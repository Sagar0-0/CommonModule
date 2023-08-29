package fit.asta.health.auth.repo

import android.app.Activity
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthProvider
import fit.asta.health.auth.model.AuthDataMapper
import fit.asta.health.auth.model.domain.User
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.datastore.PrefManager
import fit.asta.health.datastore.UserPreferencesData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class AuthRepoImpl @Inject constructor(
    private val dataMapper: AuthDataMapper,
    private val firebaseAuth: FirebaseAuth,
    private val prefManager: PrefManager
) : AuthRepo {
    companion object {
        const val TAG = "AuthRepoImpl"
    }

    override val userData: Flow<UserPreferencesData> = prefManager.userData
    override suspend fun setLogoutDone() {
        prefManager.setScreenCode(1)//show auth screen
    }

    override suspend fun uploadFcmToken(token: String, timestamp: String, uid: String) {
        try {
            //TODO: CALL API
            setIsFcmTokenUploaded(true)
            Log.d(TAG, "uploadFcmToken: Success")
        } catch (e: Exception) {
            setIsFcmTokenUploaded(false)
            Log.e(TAG, "uploadFcmToken: Error $e")
        }
    }

    override suspend fun setIsFcmTokenUploaded(value: Boolean) {
        prefManager.setIsFcmTokenUploaded(value)
    }

    override suspend fun setLoginDone() {
        prefManager.setScreenCode(2)//Show basic profile
    }

    override suspend fun setBasicProfileDone() {
        prefManager.setScreenCode(3)//Show home
    }

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

    override fun signInWithCredential(googleAuthCredential: AuthCredential): Flow<ResponseState<User>> =
        callbackFlow {
            firebaseAuth
                .signInWithCredential(googleAuthCredential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(ResponseState.Success(dataMapper.mapToUser(it.result.user!!)))
                    } else {
                        trySend(ResponseState.Error(it.exception ?: Exception()))
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

    override fun verifyPhoneOtp(otp: String): Flow<ResponseState<String>> = callbackFlow {
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

    override fun signOut(): ResponseState<Boolean> {
        return try {
            firebaseAuth.signOut()
            ResponseState.Success(true)
        } catch (e: Exception) {
            ResponseState.Error(e)
        }
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
                                trySend(ResponseState.Error(deleteTask.exception ?: Exception()))
                                Log.d("DeleteAccount", deleteTask.exception?.message!!)
                            }
                        }
                } else { //Handle the exception
                    trySend(ResponseState.Error(reAuthTask.exception ?: Exception()))
                }
            }
        awaitClose { close() }
    }


    private fun reAuthenticateUser(credential: AuthCredential) =
        firebaseAuth.currentUser!!.reauthenticate(credential)

}