package fit.asta.health.common.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.storage.FirebaseStorage
import fit.asta.health.BuildConfig
import fit.asta.health.R
import fit.asta.health.settings.WEB_URL
import fit.asta.health.settings.WebViewActivity
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


fun Context.getUriFromResourceId(resId: Int): Uri {

    return Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(resources.getResourcePackageName(resId))
        .appendPath(resources.getResourceTypeName(resId))
        .appendPath(resources.getResourceEntryName(resId))
        .build()
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

    Glide.with(applicationContext)
        .load(resourceId)
        .into(imgView)
}

fun Context.showImageByUrl(uriImg: Uri, imgView: ImageView?) {

    if (imgView != null)
        Glide.with(applicationContext)
            .load(uriImg)
            .thumbnail(Glide.with(applicationContext).load(R.drawable.shimmer))
            .centerCrop()
            .transform(RoundedCorners(this.resources.getDimensionPixelSize(R.dimen.corner_radius)))
            .error(R.drawable.ic_broken_image)
            .fallback(R.drawable.ic_camera)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            //.apply(RequestOptions.noTransformation())
            .into(imgView)
}

fun Context.showCircleImageByUrl(uriImg: Uri, imgView: ImageView?) {

    if (imgView != null)
        Glide.with(applicationContext)
            .load(uriImg)
            .thumbnail(Glide.with(applicationContext).load(R.drawable.shimmer))
            .centerCrop()
            .placeholder(R.drawable.shimmer)
            .error(R.drawable.ic_broken_image)
            .fallback(R.drawable.shimmer)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions.circleCropTransform())
            .into(imgView)
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

fun Activity.hideKeyboardFrom(view: View) {

    val imm = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.mapStringKey(str: String) =
    if (str.isEmpty()) ""
    else {
        this.resources.getString(
            this.resources.getIdentifier(
                str,
                "string",
                this.applicationContext.packageName
            )
        )
    }

fun Context.shareApp() {

    //TODO: change to suit for iOS app too
    try {
        val sharingIntent = Intent()
        sharingIntent.action = Intent.ACTION_SEND
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name))
        sharingIntent.putExtra(
            Intent.EXTRA_TEXT,
            resources.getString(R.string.share_app_extra_text) + "\n" + resources.getString(R.string.google_play_store_url) + BuildConfig.APPLICATION_ID
        )
        sharingIntent.type = "text/html"
        startActivity(
            Intent.createChooser(
                sharingIntent,
                resources.getString(R.string.share_app_choose_text)
            )
        )

    } catch (e: Exception) {

        showToastMessage(e.message)
    }
}

fun Context.rateUs() {

    try {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(resources.getString(R.string.google_market_url) + BuildConfig.APPLICATION_ID)
            )
        )
    } catch (e: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(resources.getString(R.string.google_play_store_url) + BuildConfig.APPLICATION_ID)
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

fun Context.setAppTheme() {
    setAppTheme(
        PrefUtils.getTheme(
            this
        )
    )
}

fun setAppTheme(newValue: String = "system", context: Context? = null) {
    if (context != null) PrefUtils.setTheme(newValue, context)

    val mode = when (newValue) {
        "dark" -> {
            AppCompatDelegate.MODE_NIGHT_YES
        }
        "light" -> {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        "system" -> {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        "battery" -> {
            AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
        }
        else -> {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
    }

    AppCompatDelegate.setDefaultNightMode(mode)
}

fun Context.getCurrentBuildVersion(): String {

    return this.packageManager.getPackageInfo(this.packageName, 0).versionName
}

fun Context.sendEmail(to: String, subject: String) {

    var body: String? = null
    try {

        body = "\n\n------------------------------------------" +
                "\nPlease don't remove the below information:" +
                "\nDevice OS: Android \n Device OS version: " + Build.VERSION.RELEASE +
                "\n App Version: " + this.getCurrentBuildVersion() +
                "\n Device Brand: " + Build.BRAND +
                "\n Device Model: " + Build.MODEL +
                "\n Device Manufacturer: " + Build.MANUFACTURER

    } catch (e: PackageManager.NameNotFoundException) {

    }

    val intent = Intent(Intent.ACTION_SENDTO)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    intent.data = Uri.parse("mailto:")
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    intent.putExtra(Intent.EXTRA_TEXT, body)

    this.startActivity(Intent.createChooser(intent, this.getString(R.string.share_app_choose_text)))
}

fun Context.sendFeedbackMessage() {

    sendEmail("intuminds@gmail.com", "Feedback for ASTA android app")
}

fun Context.sendBugReportMessage() {

    sendEmail("intuminds@gmail.com", "Bug report from ASTA android app")
}

@Composable
fun getImageUrl(url: String) = stringResource(id = R.string.media_url) + url

fun getFirebaseStorageBucketUrl(context: Context): String {

    return context.resources.getString(R.string.fire_storage_url) +
            FirebaseStorage.getInstance().reference.bucket + "/o/"
}

fun getPublicStorageUrl(context: Context, url: String): String {

    return getFirebaseStorageBucketUrl(context) + Uri.encode(url) + "?alt=media"
}

fun Context.showDialog(title: String, desc: String, okTitle: String, notifyOK: () -> Unit) {

    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(desc)
        .setPositiveButton(okTitle) { _, _ ->

            notifyOK()
        }
        .setNegativeButton(R.string.title_cancel) { dialog, _ ->

            dialog.cancel()
        }
        .create()
        .show()
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun Context.showUrlInBrowser(url: String) {

    val webIntent = Intent(this, WebViewActivity::class.java)
    webIntent.putExtra(WEB_URL, url)
    startActivity(webIntent)
}