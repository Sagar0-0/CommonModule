package fit.asta.health.fcm.repo

interface TokenRepo {
    fun sendToken(token: String)
}
