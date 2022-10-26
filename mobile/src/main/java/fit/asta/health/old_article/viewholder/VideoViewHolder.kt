package fit.asta.health.old_article.viewholder

import android.content.Context
import android.net.Uri
import android.view.View
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import fit.asta.health.ActivityLauncher
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old_article.data.ArticleContent
import fit.asta.health.old_course.session.data.Exercise
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl
import kotlinx.android.synthetic.main.article_video.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class VideoViewHolder(itemView: View) : BaseViewHolder<ArticleContent>(itemView), KoinComponent {
    private val storageRef: StorageReference?
        get() = FirebaseStorage.getInstance().reference

    private val launcher: ActivityLauncher by inject()
    private var currentItem: ArticleContent? = null

    init {

        itemView.setOnClickListener {
            playVideo(itemView.context, currentItem)
        }

        itemView.articleVideoButton?.setOnClickListener {
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
            context.showImageByUrl(imgUri, itemView.articleVideo)
            progressArticleVideo.hide()
            articleVideoButton.visibility = View.VISIBLE
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