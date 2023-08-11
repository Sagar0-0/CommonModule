package fit.asta.health.auth.data.repo

import android.app.Activity
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthProvider
import fit.asta.health.auth.data.model.AuthDataMapper
import fit.asta.health.auth.data.model.domain.User
import fit.asta.health.common.utils.ResponseState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception
import javax.inject.Inject


class AuthRepoImpl @Inject constructor(
    private val dataMapper: AuthDataMapper,
    private val firebaseAuth: FirebaseAuth
) : AuthRepo {

    override fun isAuthenticated(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun getUserId(): String? {
        return firebaseAuth.uid
    }

    override fun getUser(): User? {
        return firebaseAuth.currentUser?.let {
            dataMapper.mapToUser(it)
        }
    }

    override fun signInWithCredential(googleAuthCredential: AuthCredential) : Flow<ResponseState<Boolean>> = callbackFlow{
        FirebaseAuth.getInstance()
            .signInWithCredential(googleAuthCredential)
            .addOnCompleteListener {
            if(it.isSuccessful){
                trySend(ResponseState.Success(true))
            }else{
                trySend(ResponseState.Error(it.exception?: Exception()))
            }
        }

        awaitClose {
            close()
        }
    }

    override fun signInWithPhone(
        phone: String,
        activity: Activity
    ): ResponseState<String> {
        //TODO
        return ResponseState.Error(Exception())
    }

    override fun verifyPhoneOtp(otp: String): Flow<ResponseState<String>> = callbackFlow  {
//        val credential = PhoneAuthProvider.getCredential(omVerificationCode, otp)
//        firebaseAuth.signInWithCredential(credential)
//            .addOnCompleteListener {
//                if (it.isSuccessful)
//                    trySend(ResponseState.Success("otp verified"))
//            }.addOnFailureListener {
//                trySend(ResponseState.Error(it))
//            } TODO

        awaitClose {
            close()
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }


    override fun deleteAccount(): Flow<ResponseState<Boolean>> = callbackFlow {
        val currentUser = firebaseAuth.currentUser!!
        val credential: AuthCredential? = when (currentUser.providerData[1].providerId) {
            "google.com" -> {
                val fireBaseContext = firebaseAuth.app.applicationContext
                val googleAccount = GoogleSignIn.getLastSignedInAccount(fireBaseContext)
                GoogleAuthProvider.getCredential(googleAccount?.idToken, null)
            }

            "phone" -> {
                // How to get the below params(verificationId, code), when we use firebase auth ui?
                PhoneAuthProvider.getCredential(currentUser.phoneNumber!!, "")
            }

            else -> null
        }
        reAuthenticateUser(credential!!)
            .addOnCompleteListener { reAuthTask ->
                if (reAuthTask.isSuccessful) {
                    currentUser.delete()
                        .addOnCompleteListener { deleteTask ->
                            if (deleteTask.isSuccessful) {
                                trySend(ResponseState.Success(true))
                            } else {
                                trySend(ResponseState.Error(deleteTask.exception?: Exception()))
                                Log.d("DeleteAccount", deleteTask.exception?.message!!)
                            }
                        }
                } else { //Handle the exception
                    trySend(ResponseState.Error(reAuthTask.exception?: Exception()))
                }
            }
        awaitClose { close() }
    }


    private fun reAuthenticateUser(credential: AuthCredential) =
        firebaseAuth.currentUser!!.reauthenticate(credential)

}