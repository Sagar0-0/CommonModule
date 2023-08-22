package fit.asta.health.auth

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import fit.asta.health.resources.strings.R

object FirebaseUtil {

    fun getFireStorage(): StorageReference {
        return FirebaseStorage.getInstance().reference
    }

    fun getFireStore(): FirebaseFirestore {
        return Firebase.firestore
    }
}

fun getFirebaseStorageBucketUrl(context: Context): String {
    val url =
        context.resources.getString(R.string.fire_storage_url) + FirebaseStorage.getInstance().reference.bucket + "/o/"
    Log.d("URL", "getFirebaseStorageBucketUrl: $url")
    return url
}

fun getPublicStorageUrl(context: Context, url: String): String {
    return getFirebaseStorageBucketUrl(context) + Uri.encode(url) + "?alt=media"
}