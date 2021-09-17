package fit.asta.health.article.viewholder

import android.view.View
import fit.asta.health.article.data.ArticleContent
import fit.asta.health.common.BaseViewHolder
import kotlinx.android.synthetic.main.article_header.view.articleHeaderTitle
import kotlinx.android.synthetic.main.article_header.view.articleHeaderDate
import kotlinx.android.synthetic.main.article_header.view.articleHeaderAuthor

class HeaderViewHolder(itemView: View) : BaseViewHolder<ArticleContent>(itemView) {

    override fun bindData(content: ArticleContent) {
        itemView.apply {
            articleHeaderTitle.text = content.title
            articleHeaderDate.text = content.text
            articleHeaderAuthor.text = content.authorName
        }
    }
}