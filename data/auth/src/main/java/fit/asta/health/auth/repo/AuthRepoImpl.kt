package fit.asta.health.auth.repo

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import fit.asta.health.auth.fcm.remote.TokenApi
import fit.asta.health.auth.fcm.remote.TokenDTO
import fit.asta.health.auth.fcm.remote.TokenResponse
import fit.asta.health.auth.model.AuthDataMapper
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.remote.AuthApi
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.datastore.PrefManager
import fit.asta.health.datastore.UserPreferencesData
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AuthRepoImpl @Inject constructor(
    private val tokenApi: TokenApi,
    private val authApi: AuthApi,
    private val dataMapper: AuthDataMapper,
    private val googleSignInClient: GoogleSignInClient,
    private val firebaseAuth: FirebaseAuth,
    private val prefManager: PrefManager,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthRepo {
    companion object {
        const val TAG = "AuthRepoImpl"
    }

    override suspend fun resetReferralCode() {
        prefManager.setReferralCode("")
    }

    override val userData: Flow<UserPreferencesData> = prefManager.userData
    override suspend fun setLogoutDone() {
        prefManager.setScreenCode(1)//show auth screen
    }

    override suspend fun uploadFcmToken(tokenDTO: TokenDTO): ResponseState<TokenResponse> {
        return getApiResponseState(
            onSuccess = {
                setIsFcmTokenUploaded(true)
            },
            onFailure = {
                setIsFcmTokenUploaded(false)
            }
        ) {
            tokenApi.sendToken(tokenDTO)
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

    override fun signInWithCredential(authCredential: AuthCredential): Flow<ResponseState<User>> =
        callbackFlow {
            firebaseAuth
                .signInWithCredential(authCredential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(ResponseState.Success(dataMapper.mapToUser(it.result.user!!)))
                    } else {
                        trySend(ResponseState.ErrorMessage(R.string.sign_in_failed))
                    }
                }

            awaitClose {
                close()
            }
        }

    override fun linkWithCredential(authCredential: AuthCredential): Flow<ResponseState<User>> =
        callbackFlow {
            firebaseAuth.currentUser!!.linkWithCredential(authCredential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user: FirebaseUser? = it.result.user
                        Log.e(TAG, "linkWithCredential: FirebaseUser: $user")
                        trySend(ResponseState.Success(dataMapper.mapToUser(user!!)))
                    } else {
                        trySend(ResponseState.ErrorMessage(R.string.linking_the_credentials_failed))
                    }
                }
            awaitClose {
                close()
            }
        }

    override suspend fun signOut(): Flow<ResponseState<Boolean>> =
        withContext(coroutineDispatcher) {
            callbackFlow {
                try {
                    googleSignInClient.signOut().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            firebaseAuth.signOut()
                            trySend(ResponseState.Success(true))
                        } else {
                            trySend(ResponseState.ErrorMessage(R.string.sign_out_failed))
                        }
                    }
                } catch (e: Exception) {
                    trySend(ResponseState.ErrorMessage(R.string.sign_out_failed))
                }
                awaitClose { close() }
            }
        }


    override suspend fun deleteAccount(): Flow<ResponseState<Boolean>> =
        withContext(coroutineDispatcher) {
            callbackFlow {
                val currentUser = firebaseAuth.currentUser
                currentUser?.let {
                    try {
                        val res = authApi.deleteAccount(it.uid)
                        val success = res.flag && res.status.code == 200
                        if (success) {
                            if (currentUser.providerData[1].providerId == "phone") {
                                currentUser.delete()
                                    .addOnCompleteListener { deleteTask ->
                                        if (deleteTask.isSuccessful) {
                                            trySend(ResponseState.Success(true))
                                        } else {
                                            trySend(
                                                ResponseState.ErrorMessage(R.string.delete_account_failed)
                                            )
                                            Log.d("DeleteAccount", deleteTask.exception?.message!!)
                                        }
                                    }
                            } else {
                                val fireBaseContext = firebaseAuth.app.applicationContext
                                val googleAccount =
                                    GoogleSignIn.getLastSignedInAccount(fireBaseContext)
                                val credential: AuthCredential =
                                    GoogleAuthProvider.getCredential(googleAccount?.idToken, null)
                                reAuthenticateUser(credential)
                                    .addOnCompleteListener { reAuthTask ->
                                        if (reAuthTask.isSuccessful) {
                                            currentUser.delete()
                                                .addOnCompleteListener { deleteTask ->
                                                    if (deleteTask.isSuccessful) {
                                                        trySend(ResponseState.Success(true))
                                                    } else {
                                                        trySend(
                                                            ResponseState.ErrorMessage(
                                                                R.string.delete_account_failed
                                                            )
                                                        )
                                                        Log.d(
                                                            "DeleteAccount",
                                                            deleteTask.exception?.message!!
                                                        )
                                                    }
                                                }
                                        } else { //Handle the exception
                                            trySend(
                                                ResponseState.ErrorMessage(
                                                    R.string.delete_account_failed
                                                )
                                            )
                                        }
                                    }
                            }
                        } else {
                            trySend(ResponseState.ErrorMessage(R.string.delete_account_failed))
                        }
                    } catch (e: Exception) {
                        trySend(ResponseState.ErrorMessage(R.string.delete_account_failed))
                    }
                }
                awaitClose { close() }
            }
        }


    private fun reAuthenticateUser(credential: AuthCredential) =
        firebaseAuth.currentUser!!.reauthenticate(credential)

}