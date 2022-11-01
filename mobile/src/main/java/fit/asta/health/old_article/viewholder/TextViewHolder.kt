package fit.asta.health.old_article.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old_article.data.ArticleContent


class TextViewHolder(itemView: View) : BaseViewHolder<ArticleContent>(itemView) {
    override fun bindData(content: ArticleContent) {
        itemView.findViewById<AppCompatTextView>(R.id.articleText).text = content.text
    }
}