package fit.asta.health.network

class LanguageProvider {

    private var language: String = "en"

    fun get() = language

    fun load(language: String) {
        this.language = language
    }
}