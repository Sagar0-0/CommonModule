package fit.asta.health.article.viewholder

import android.net.Uri
import android.view.View
import fit.asta.health.article.data.ArticleContent
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl
import kotlinx.android.synthetic.main.article_image.view.articleImage

class ImageViewHolder(itemView: View) : BaseViewHolder<ArticleContent>(itemView) {
    override fun bindData(content: ArticleContent) {
        val imageUri = Uri.parse(
            getPublicStorageUrl(
                itemView.context,
                content.metaData.imgLoc + content.text
            )
        )
        itemView.apply {
            context.showImageByUrl(imageUri, itemView.articleImage)
        }
    }
}