package fit.asta.health.network

interface TokenProvider {

    fun get(): String
    fun load(token: String)
    fun getToken(callback: (String) -> Unit)
}