package fit.asta.health.auth.model.domain

import android.net.Uri
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid: String = "",
    val name: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val photoUrl: Uri? = null
) : Parcelable {
    override fun toString(): String {
        val gson: Gson = GsonBuilder().create()
        return gson.toJson(this).replace("/", "|")
    }

}

fun String.toUser(): User {
    val gson: Gson = GsonBuilder().create()
    val json = this.replace("|", "/")
    return gson.fromJson(json, User::class.java)
}