package fit.asta.health.auth.model

import android.app.Activity
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.model.domain.UserCred
import fit.asta.health.common.utils.ResponseState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class FirebaseAuthRepoImpl @Inject constructor(
    private val dataMapper: AuthDataMapper,
    private val firebaseAuth: FirebaseAuth
) : AuthRepo {

    private lateinit var omVerificationCode: String

    override fun isAuthenticated(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }

    override fun getUserId(): String? {
        return FirebaseAuth.getInstance().uid
    }

    override fun getUser(): User? {
        return FirebaseAuth.getInstance().currentUser?.let {
            dataMapper.mapToUser(it)
        }
    }

    override fun firebaseSignInWithGoogle(googleAuthCredential: AuthCredential): Flow<ResponseState<User>> {
        TODO("Not yet implemented")
    }

    override fun createUserWithPhone(
        phone: String,
        activity: Activity
    ): Flow<ResponseState<String>> =
        callbackFlow {
            trySend(ResponseState.Loading)

            val onVerificationCallback =
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        trySend(ResponseState.Error(p0))
                    }

                    override fun onCodeSent(
                        verificationCode: String,
                        p1: PhoneAuthProvider.ForceResendingToken
                    ) {
                        super.onCodeSent(verificationCode, p1)
                        trySend(ResponseState.Success("OTP Sent Successfully"))
                        omVerificationCode = verificationCode
                    }
                }

            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber("+91$phone")
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(onVerificationCallback)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
            awaitClose {
                close()
            }
        }

    override fun signWithCredential(otp: String): Flow<ResponseState<String>> = callbackFlow {
        trySend(ResponseState.Loading)
        val credential = PhoneAuthProvider.getCredential(omVerificationCode, otp)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    trySend(ResponseState.Success("otp verified"))
            }.addOnFailureListener {
                trySend(ResponseState.Error(it))
            }
        awaitClose {
            close()
        }
    }

    override fun createUser(auth: UserCred): Flow<ResponseState<User>> = callbackFlow {
        trySend(ResponseState.Loading)

        firebaseAuth.createUserWithEmailAndPassword(
            auth.email,
            auth.password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                trySend(ResponseState.Success(dataMapper.mapToUser(it.result.user!!)))
                Log.d("main", "current user id: ${firebaseAuth.currentUser?.uid}")
            }
        }.addOnFailureListener {
            trySend(ResponseState.Error(it))
        }

        awaitClose {
            close()
        }
    }

    override fun loginUser(auth: UserCred): Flow<ResponseState<User>> = callbackFlow {
        trySend(ResponseState.Loading)

        firebaseAuth.signInWithEmailAndPassword(
            auth.email,
            auth.password
        ).addOnSuccessListener {
            trySend(ResponseState.Success(dataMapper.mapToUser(it.user!!)))
            Log.d("main", "current user id: ${firebaseAuth.currentUser?.uid}")
        }.addOnFailureListener {
            trySend(ResponseState.Error(it))
        }

        awaitClose {
            close()
        }
    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}