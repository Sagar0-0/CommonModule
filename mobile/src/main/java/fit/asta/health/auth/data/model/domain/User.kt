package fit.asta.health.auth.data.model.domain

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid: String = "",
    val name: String? = "",
    val email: String? = "",
    val phoneNumber: String? = "",
    val photoUrl: Uri? = null,
    val isAuthenticated: Boolean = false,
    val isNew: Boolean = true,
    val isCreated: Boolean = false,
    val isAnonymous: Boolean = true
) : Parcelable

data class UserCred(
    val email: String = "",
    val password: String = ""
)