package fit.asta.health.old_article.viewholder

import android.net.Uri
import android.view.View
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old_article.data.ArticleContent
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl


class ImageViewHolder(itemView: View) : BaseViewHolder<ArticleContent>(itemView) {
    override fun bindData(content: ArticleContent) {
        val imageUri = Uri.parse(
            getPublicStorageUrl(
                itemView.context,
                content.metaData.imgLoc + content.text
            )
        )
        itemView.apply {
            context.showImageByUrl(imageUri, itemView.findViewById(R.id.articleImage))
        }
    }
}