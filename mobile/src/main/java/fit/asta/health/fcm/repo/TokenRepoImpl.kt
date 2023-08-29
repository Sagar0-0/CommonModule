package fit.asta.health.fcm.repo

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TokenRepoImpl : TokenRepo {
    override fun sendToken(token: String) {
        Log.d("TAG", "sendToken: $token")
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            print("hi")
        }
    }

    suspend fun sme() {

    }
}