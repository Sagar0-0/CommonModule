package fit.asta.health.auth.data.model

import com.google.firebase.auth.FirebaseUser
import fit.asta.health.auth.data.model.domain.User


class AuthDataMapper {

    fun mapToUser(firebaseUser: FirebaseUser): User {
        return User(
            uid = firebaseUser.uid,
            name = firebaseUser.displayName,
            email = firebaseUser.email,
            phoneNumber = firebaseUser.phoneNumber,
            photoUrl = firebaseUser.photoUrl,
            isAuthenticated = true,
            isNew = false,
            isCreated = true
        )
    }
}