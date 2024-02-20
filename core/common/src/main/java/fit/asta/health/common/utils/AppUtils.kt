package fit.asta.health.common.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.documentfile.provider.DocumentFile
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.review.ReviewManagerFactory
import fit.asta.health.core.common.BuildConfig
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import fit.asta.health.resources.drawables.R as DrawR
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

fun View.showSnackbar(@StringRes messageRes: Int, duration: Int = Snackbar.LENGTH_LONG) {

    Snackbar.make(this, messageRes, duration).show()
}

fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_LONG) {

    Snackbar.make(this, message, duration).show()
}

fun Context.showDrawableImage(resourceId: Int, imgView: ImageView) {

    Glide.with(applicationContext).load(resourceId).into(imgView)
}

fun Context.showImageByUrl(uriImg: Uri, imgView: ImageView?) {

    if (imgView != null) Glide.with(applicationContext).load(uriImg)
        .thumbnail(Glide.with(applicationContext).load(DrawR.drawable.shimmer)).centerCrop()
        .transform(RoundedCorners(4)).error(DrawR.drawable.ic_broken_image)
        .fallback(DrawR.drawable.ic_camera).diskCacheStrategy(DiskCacheStrategy.ALL)
        //.apply(RequestOptions.noTransformation())
        .into(imgView)
}

fun Context.showCircleImageByUrl(uriImg: Uri, imgView: ImageView?) {

    if (imgView != null) Glide.with(applicationContext).load(uriImg)
        .thumbnail(Glide.with(applicationContext).load(DrawR.drawable.shimmer)).centerCrop()
        .placeholder(DrawR.drawable.shimmer).error(DrawR.drawable.ic_broken_image)
        .fallback(DrawR.drawable.shimmer).diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply(RequestOptions.circleCropTransform()).into(imgView)
}

fun getBitmapFromURL(strURL: String?): Bitmap? {

    return try {

        val url = URL(strURL)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()

        val input: InputStream = connection.inputStream
        BitmapFactory.decodeStream(input)

    } catch (e: IOException) {

        e.printStackTrace()
        null //or a default bitmap
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

fun Activity.hideKeyboardFrom(view: View) {

    val imm = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.mapStringKey(str: String) = if (str.isEmpty()) ""
else {
    this.resources.getString(
        this.resources.getIdentifier(
            str, "string", this.applicationContext.packageName
        )
    )
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

fun showInAppReview(activity: Activity) {

    val reviewManager = ReviewManagerFactory.create(activity)
    //val reviewManager = FakeReviewManager(activity)
    reviewManager.requestReviewFlow().addOnCompleteListener {
        if (it.isSuccessful) {
            val flow = reviewManager.launchReviewFlow(activity, it.result)
            flow.addOnCompleteListener { _ ->
                // Continue your application process
            }
        } else {
            activity.showToastMessage(it.exception?.message)
        }
    }
}

sealed class AppThemeType(val value: String) {
    data object Dark : AppThemeType("dark")
    data object System : AppThemeType("system")
    data object Light : AppThemeType("light")
    data object Battery : AppThemeType("battery")
}

//fun setAppTheme(newValue: String, context: Context) {
//    PrefManager.setTheme(newValue, context)
//    when (PrefManager.getTheme(context)) {
//        AppThemeType.Dark.value -> {
//            usingDarkMode.value = true
//        }
//
//        AppThemeType.Light.value -> {
//            usingDarkMode.value = false
//        }
//
//        else -> {
//            usingDarkMode.value = isSystemDarkMode(context)
//        }
//    }
//
//    val mode = when (newValue) {
//        AppThemeType.Dark.value -> {
//            AppCompatDelegate.MODE_NIGHT_YES
//        }
//
//        AppThemeType.Light.value -> {
//            AppCompatDelegate.MODE_NIGHT_NO
//        }
//
//        AppThemeType.System.value -> {
//            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
//        }
//
//        AppThemeType.Battery.value -> {
//            AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
//        }
//
//        else -> {
//            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
//        }
//    }
//
//    AppCompatDelegate.setDefaultNightMode(mode)
//} TODO: use proto

fun isSystemDarkMode(context: Context): Boolean {
    val nightModeFlags = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
}

fun Context.getCurrentBuildVersion(): String {

    return this.packageManager.getPackageInfo(this.packageName, 0).versionName
}

fun Context.sendEmail(to: String, subject: String) {

    var body: String? = null
    try {

        body =
            "\n\n------------------------------------------" + "\nPlease don't remove the below information:" + "\nDevice OS: Android \n Device OS version: " + Build.VERSION.RELEASE + "\n App Version: " + this.getCurrentBuildVersion() + "\n Device Brand: " + Build.BRAND + "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER

    } catch (e: PackageManager.NameNotFoundException) {

    }

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

fun getVideoUrl(url: String) = BuildConfig.BASE_VIDEO_URL + url

fun getVideoUrlTools(url: String) = BuildConfig.BASE_IMAGE_URL + url

fun getFileName(context: Context, uri: Uri) = DocumentFile.fromSingleUri(context, uri)?.name ?: ""


fun Context.showDialog(title: String, desc: String, okTitle: String, notifyOK: () -> Unit) {

    AlertDialog.Builder(this).setTitle(title).setMessage(desc).setPositiveButton(okTitle) { _, _ ->

        notifyOK()
    }.setNegativeButton(StringR.string.title_cancel) { dialog, _ ->

        dialog.cancel()
    }.create().show()
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


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