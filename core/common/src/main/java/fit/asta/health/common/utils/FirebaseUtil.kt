package fit.asta.health.common.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

object FirebaseUtil {

    fun getFireStorage(): StorageReference {
        return FirebaseStorage.getInstance().reference
    }

    fun getFireStore(): FirebaseFirestore {
        return Firebase.firestore
    }
}