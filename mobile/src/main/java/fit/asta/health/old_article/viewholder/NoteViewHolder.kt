package fit.asta.health.old_article.viewholder

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.core.content.ContextCompat
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old_article.data.ArticleContent
import kotlinx.android.synthetic.main.article_note.view.*

class NoteViewHolder(itemView: View) : BaseViewHolder<ArticleContent>(itemView) {

    override fun bindData(content: ArticleContent) {
        val title = content.title
        val text = content.text
        val color = ContextCompat.getColor(itemView.context, R.color.colorPrimary)
            val spannable = SpannableString("$title: $text")
            spannable.setSpan(
                ForegroundColorSpan(color),
                0,
                title.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                title.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            itemView.article_note_text.text = spannable
        }
    }