package fit.asta.health.feature.spotify.utils

object SpotifyUIHelper {

    fun <T> extractTextFromListOfStrings(input: List<T>, comparator: (T) -> String): String {
        return input
            .map { comparator(it) }
            .toString()
            .filter { it != '[' }
            .filter { it != ']' }
    }
}