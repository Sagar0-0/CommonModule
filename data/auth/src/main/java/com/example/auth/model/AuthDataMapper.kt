package com.example.auth.model

import com.example.auth.model.domain.User
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