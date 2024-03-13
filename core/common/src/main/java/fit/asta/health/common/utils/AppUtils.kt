package fit.asta.health.common.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.location.Address
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.core.content.ContextCompat.getSystemService
import androidx.documentfile.provider.DocumentFile
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.google.android.play.core.review.ReviewManagerFactory
import fit.asta.health.core.common.BuildConfig
import fit.asta.health.resources.drawables.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import fit.asta.health.resources.strings.R as StringR


fun Context.getUriFromResourceId(resId: Int): Uri {

    return Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(resources.getResourcePackageName(resId))
        .appendPath(resources.getResourceTypeName(resId))
        .appendPath(resources.getResourceEntryName(resId)).build()
}

fun Context.showToastMessage(msg: String?) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Context.shareApp(appId: String) {

    //TODO: change to suit for iOS app too
    try {
        val sharingIntent = Intent()
        sharingIntent.action = Intent.ACTION_SEND
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(StringR.string.app_name))
        sharingIntent.putExtra(
            Intent.EXTRA_TEXT,
            resources.getString(StringR.string.share_app_extra_text) + "\n" + resources.getString(
                StringR.string.google_play_store_url
            ) + appId
        )
        sharingIntent.type = "text/html"
        startActivity(
            Intent.createChooser(
                sharingIntent, resources.getString(StringR.string.share_app_choose_text)
            )
        )

    } catch (e: Exception) {

        showToastMessage(e.message)
    }
}

fun Context.rateUs(appId: String) {
    try {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(resources.getString(StringR.string.google_market_url) + appId)
            )
        )
    } catch (e: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(resources.getString(StringR.string.google_play_store_url) + appId)
            )
        )
    }
}

fun Activity.showInAppReview() {

    val reviewManager = ReviewManagerFactory.create(this)
    //val reviewManager = FakeReviewManager(activity)
    reviewManager.requestReviewFlow().addOnCompleteListener {
        if (it.isSuccessful) {
            val flow = reviewManager.launchReviewFlow(this, it.result)
            flow.addOnCompleteListener { _ ->
                // Continue your application process
            }
        } else {
            this.showToastMessage(it.exception?.message)
        }
    }
}

sealed class AppThemeType(val value: String) {
    data object Dark : AppThemeType("dark")
    data object System : AppThemeType("system")
    data object Light : AppThemeType("light")
    data object Battery : AppThemeType("battery")
}

fun Context.isSystemDarkMode(): Boolean {
    val nightModeFlags = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
}

fun Context.getCurrentBuildVersion(): String {
    return this.packageManager.getPackageInfo(this.packageName, 0).versionName
}

fun Context.sendEmail(to: String, subject: String) {

    val body =
        "\n\n------------------------------------------" + "\nPlease don't remove the below information:" + "\nDevice OS: Android \n Device OS version: " + Build.VERSION.RELEASE + "\n App Version: " + this.getCurrentBuildVersion() + "\n Device Brand: " + Build.BRAND + "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER

    val intent = Intent(Intent.ACTION_SENDTO)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    intent.data = Uri.parse("mailto:")
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    intent.putExtra(Intent.EXTRA_TEXT, body)

    this.startActivity(
        Intent.createChooser(
            intent,
            this.getString(StringR.string.share_app_choose_text)
        )
    )
}

@Composable
fun getImageModel(uri: Uri?, remoteUrl: String?): String? {
    return uri?.toString() ?: remoteUrl?.let { getImageUrl(remoteUrl) }
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.graph.id) {
        inclusive = true
    }
}

fun <T : Any> SnapshotStateListSaver(): Saver<SnapshotStateList<T>, *> = listSaver(
    save = { stateList ->
        if (stateList.isNotEmpty()) {
            val first = stateList.first()
            if (!canBeSaved(first)) {
                throw IllegalStateException("${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.")
            }
        }
        stateList.toList()
    },
    restore = { it.toMutableStateList() }
)

fun Long.getDateFormat(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}

fun String.toDraw() = when (this) {
    "msc" -> R.drawable.baseline_music_note_24
    "ins" -> R.drawable.baseline_merge_type_24
    "lvl" -> R.drawable.round_filter_vintage_24
    "ln" -> R.drawable.baseline_language_24
    "tgt" -> R.drawable.baseline_merge_type_24
    else -> R.drawable.baseline_merge_type_24
}

fun Context.sendBugReportMessage() {

    sendEmail("intuminds@gmail.com", "Bug report from ASTA android app")
}

fun Context.copyTextToClipboard(text: String) {
    val clipboard = getSystemService(this, ClipboardManager::class.java)
    val clip = ClipData.newPlainText("code", text)
    clipboard?.setPrimaryClip(clip)
}

fun Context.shareReferralCode(code: String, appId: String) {
    try {
        val sharingIntent = Intent()
        sharingIntent.action = Intent.ACTION_SEND
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(StringR.string.app_name))
        sharingIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Click on the link below to download ASTA from my referral" + "\n" + resources.getString(
                StringR.string.google_play_store_url
            ) + appId + "&referrer=utm_source%3Drefer%26utm_content%3D${code}"
        )
        sharingIntent.type = "text/html"
        startActivity(
            Intent.createChooser(
                sharingIntent, resources.getString(StringR.string.share_app_choose_text)
            )
        )
    } catch (e: Exception) {
        showToastMessage(e.message)
    }
}

fun getBaseUrl(url: String) = BuildConfig.BASE_URL + url

fun getImageUrl(url: String) = if (url.startsWith("http")) url else BuildConfig.BASE_IMAGE_URL + url

fun getProfileImageUrl(uid: String) = BuildConfig.BASE_IMAGE_URL + "/images/$uid/userProfile/$uid"
suspend fun isImagePresent(url: String): Boolean {
    val connection = withContext(Dispatchers.IO) {
        URL(url).openConnection()
    }
    val contentType = connection.getHeaderField("Content-Type")
    return contentType.startsWith("image/")
}

fun getVideoUrl(url: String) = BuildConfig.BASE_VIDEO_URL + url

fun getVideoUrlTools(url: String) = BuildConfig.BASE_IMAGE_URL + url

fun Uri.getFileName(context: Context) = DocumentFile.fromSingleUri(context, this)?.name ?: ""

fun Context.showDialog(title: String, desc: String, okTitle: String, notifyOK: () -> Unit) {

    AlertDialog.Builder(this).setTitle(title).setMessage(desc).setPositiveButton(okTitle) { _, _ ->

        notifyOK()
    }.setNegativeButton(StringR.string.title_cancel) { dialog, _ ->

        dialog.cancel()
    }.create().show()
}

fun Address?.getShortAddressName(): String {
    if (this == null) return ""
    return if (!this.subLocality.isNullOrEmpty()) {
        "${this.subLocality}, ${this.locality}"
    } else if (!this.locality.isNullOrEmpty()) {
        "${this.locality}, ${this.adminArea}"
    } else {
        this.adminArea
    }
}