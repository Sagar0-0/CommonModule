package fit.asta.health.old.article.viewholder

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.widget.ContentLoadingProgressBar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import fit.asta.health.ActivityLauncher
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old.article.data.ArticleContent
import fit.asta.health.old.course.session.data.Exercise
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl
import javax.inject.Inject


class VideoViewHolder(itemView: View) : BaseViewHolder<ArticleContent>(itemView) {
    private val storageRef: StorageReference?
        get() = FirebaseStorage.getInstance().reference

    @Inject
    lateinit var launcher: ActivityLauncher
    private var currentItem: ArticleContent? = null

    init {

        itemView.setOnClickListener {
            playVideo(itemView.context, currentItem)
        }

        itemView.findViewById<AppCompatImageButton>(R.id.articleVideoButton)?.setOnClickListener {
            playVideo(itemView.context, currentItem)
        }
    }


    override fun bindData(content: ArticleContent) {

        currentItem = content
        val imgUri = Uri.parse(
            getPublicStorageUrl(
                itemView.context,
                content.metaData.imgLoc + getThumbnail(content.text)
            )
        )
        itemView.apply {
            context.showImageByUrl(imgUri, itemView.findViewById(R.id.articleVideo))
            findViewById<ContentLoadingProgressBar>(R.id.progressArticleVideo).hide()
            findViewById<AppCompatImageButton>(R.id.articleVideoButton).visibility = View.VISIBLE
        }
    }

    private fun playVideo(
        context: Context,
        currentItem: ArticleContent?
    ) {

        val videoRef = storageRef!!.child(currentItem?.metaData?.vdoLoc + this.currentItem?.text!!)
        videoRef.downloadUrl.addOnSuccessListener { uri ->

            val videoList = arrayListOf(Exercise("1", this.currentItem?.title ?: "", "", 0, uri.toString()))
            launcher.launchVideoPlayerActivity(context, videoList[0])
        }
    }

    private fun getThumbnail(url: String): String {
        val parts = url.split(".")
        return parts[0] + ".jpg"
    }
}