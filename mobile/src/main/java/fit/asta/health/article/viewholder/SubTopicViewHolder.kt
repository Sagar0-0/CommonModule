package fit.asta.health.article.viewholder

import android.view.View
import fit.asta.health.article.data.ArticleContent
import fit.asta.health.common.BaseViewHolder
import kotlinx.android.synthetic.main.article_sub_topic.view.*

class SubTopicViewHolder(itemView: View) : BaseViewHolder<ArticleContent>(itemView) {
    override fun bindData(content: ArticleContent) {
        itemView.apply {
            articleSubTitle.text = content.title
            articleSubText.text = content.text
        }
    }
}