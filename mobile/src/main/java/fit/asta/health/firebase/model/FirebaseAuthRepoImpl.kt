package fit.asta.health.firebase.model

import android.app.Activity
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import fit.asta.health.firebase.model.domain.User
import fit.asta.health.firebase.model.domain.UserCred
import fit.asta.health.utils.ResultState
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

    override fun firebaseSignInWithGoogle(googleAuthCredential: AuthCredential): Flow<ResultState<User>> {
        TODO("Not yet implemented")
    }

    override fun createUserWithPhone(phone: String, activity: Activity): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            val onVerificationCallback =
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        trySend(ResultState.Failure(p0))
                    }

                    override fun onCodeSent(
                        verificationCode: String,
                        p1: PhoneAuthProvider.ForceResendingToken
                    ) {
                        super.onCodeSent(verificationCode, p1)
                        trySend(ResultState.Success("OTP Sent Successfully"))
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

    override fun signWithCredential(otp: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        val credential = PhoneAuthProvider.getCredential(omVerificationCode, otp)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    trySend(ResultState.Success("otp verified"))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }

    override fun createUser(auth: UserCred): Flow<ResultState<User>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseAuth.createUserWithEmailAndPassword(
            auth.email,
            auth.password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                trySend(ResultState.Success(dataMapper.mapToUser(it.result.user!!)))
                Log.d("main", "current user id: ${firebaseAuth.currentUser?.uid}")
            }
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }

        awaitClose {
            close()
        }
    }

    override fun loginUser(auth: UserCred): Flow<ResultState<User>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseAuth.signInWithEmailAndPassword(
            auth.email,
            auth.password
        ).addOnSuccessListener {
            trySend(ResultState.Success(dataMapper.mapToUser(it.user!!)))
            Log.d("main", "current user id: ${firebaseAuth.currentUser?.uid}")
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }

        awaitClose {
            close()
        }
    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}