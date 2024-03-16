package fit.asta.health.auth.repo

import fit.asta.health.datastore.PrefManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TokenRepoImpl
@Inject constructor(
    private val prefManager: PrefManager,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TokenRepo {
    override fun setNewTokenAvailable(token: String) {
        CoroutineScope(coroutineDispatcher).launch {
            prefManager.setIsFcmTokenUploaded(false)
        }
    }
}