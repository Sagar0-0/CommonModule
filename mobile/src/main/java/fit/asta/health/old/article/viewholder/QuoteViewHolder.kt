package fit.asta.health.old.article.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old.article.data.ArticleContent


class QuoteViewHolder(itemView: View) : BaseViewHolder<ArticleContent>(itemView) {

    override fun bindData(content: ArticleContent) {
        itemView.findViewById<AppCompatTextView>(R.id.articleQuoteText).text = content.text
    }
}