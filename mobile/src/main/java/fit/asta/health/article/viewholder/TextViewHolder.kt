package fit.asta.health.article.viewholder

import android.view.View
import fit.asta.health.article.data.ArticleContent
import fit.asta.health.common.BaseViewHolder
import kotlinx.android.synthetic.main.article_text.view.*

class TextViewHolder(itemView: View) : BaseViewHolder<ArticleContent>(itemView) {
    override fun bindData(content: ArticleContent) {
        itemView.articleText.text = content.text
    }
}