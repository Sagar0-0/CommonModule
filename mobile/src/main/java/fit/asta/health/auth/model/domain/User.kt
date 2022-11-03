package fit.asta.health.auth.model.domain

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid: String,
    val name: String?,
    val email: String?,
    val phoneNumber: String?,
    val photoUrl: Uri?,
    val isAuthenticated: Boolean,
    val isNew: Boolean,
    val isCreated: Boolean
) : Parcelable

data class UserCred(
    val email: String? = "",
    val password: String? = ""
)