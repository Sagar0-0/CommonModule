package fit.asta.health.auth.fcm.repo

interface TokenRepo {
    fun setNewTokenAvailable(token: String)
}
