package fit.asta.health.firebase.model.service

import fit.asta.health.firebase.model.domain.User
import kotlinx.coroutines.flow.Flow

interface AuthService {

    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}
