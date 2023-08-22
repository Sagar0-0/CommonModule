package fit.asta.health.network

class TokenProvider {

    private var token: String = ""

    fun get() = token

    fun load(token: String) {
        this.token = token
    }
}