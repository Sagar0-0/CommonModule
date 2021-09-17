package fit.asta.health.network

import com.google.firebase.auth.FirebaseAuth

class TokenProvider {

    private var token: String = ""

    fun get() = token

    fun load(token: String) {
        this.token = token
    }

    private fun getToken(callback: (String) -> Unit) {
        FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.addOnCompleteListener {
            if (it.isSuccessful){
                callback(it.result?.token!!)
            }
        }
    }
}