package fit.asta.health.feature.testimonialsx.utils

import android.net.Uri
import androidx.compose.runtime.Composable
import fit.asta.health.common.utils.getImgUrl

/**[getOneUrlX] function is a utility function used in Jetpack Compose for Android development. It is
 *  designed to retrieve a single URL as a String based on the provided localUrl and remoteUrl parameters.
 *  If the localUrl is not null, the function will return the String representation of the localUrl.
 *  If the localUrl is null, the function will call getImgUrl(remoteUrl) to obtain the URL as a String from the remoteUrl.
 * @param localUrl (nullable Uri): The local URL represented as a Uri object. It may be null if there is no local URL available or if the local URL is not valid.
 * @param remoteUrl (String): The remote URL represented as a String. This is the URL used when localUrl is null or invalid. It should be a valid URL string.
 * @return [String]: The URL as a String, either obtained from the localUrl or retrieved from the remoteUrl using the getImgUrl function.
 */

@Composable
fun getOneUrlX(localUrl: Uri?, remoteUrl: String): String {
    return localUrl?.toString() ?: getImgUrl(remoteUrl)
}