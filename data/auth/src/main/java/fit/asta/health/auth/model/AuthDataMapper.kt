package fit.asta.health.auth.model

import fit.asta.health.auth.model.domain.User
import com.google.firebase.auth.FirebaseUser


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