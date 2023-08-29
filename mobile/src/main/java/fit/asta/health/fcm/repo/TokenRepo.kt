package fit.asta.health.fcm.repo

interface TokenRepo {
    fun setNewTokenAvailable(token: String)
}
