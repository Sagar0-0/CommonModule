package fit.asta.health.auth.repo

interface TokenRepo {
    //TODO: Make functions suspended
    fun setNewTokenAvailable(token: String)
}
