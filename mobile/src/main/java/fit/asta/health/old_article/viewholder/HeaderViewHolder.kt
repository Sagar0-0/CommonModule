package fit.asta.health.old_article.viewholder

import android.view.View
import android.widget.TextView
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old_article.data.ArticleContent


class HeaderViewHolder(itemView: View) : BaseViewHolder<ArticleContent>(itemView) {

    override fun bindData(content: ArticleContent) {
        itemView.apply {
            findViewById<TextView>(R.id.articleHeaderTitle).text = content.title
            findViewById<TextView>(R.id.articleHeaderDate).text = content.text
            findViewById<TextView>(R.id.articleHeaderAuthor).text = content.authorName
        }
    }
}