package fit.asta.health.auth.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth


class UserRepository {

    fun isAuthenticated(): Boolean {

        return FirebaseAuth.getInstance().currentUser != null
    }

    fun user(): User? {

        val fireBaseUser = FirebaseAuth.getInstance().currentUser
        if (fireBaseUser != null) {

            return User(
                fireBaseUser.uid,
                fireBaseUser.displayName,
                fireBaseUser.email,
                fireBaseUser.phoneNumber,
                fireBaseUser.photoUrl,
                isAuthenticated = true,
                isNew = false,
                isCreated = true
            )
        }

        return null
    }

    fun firebaseSignInWithGoogle(googleAuthCredential: AuthCredential): MutableLiveData<User>? {

        return null
    }

    fun addUser(user: User) {

    }

    fun updateUser(user: User) {

    }
}