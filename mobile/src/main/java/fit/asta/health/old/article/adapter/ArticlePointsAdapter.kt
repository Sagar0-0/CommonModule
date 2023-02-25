package fit.asta.health.old.article.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.R
import fit.asta.health.old.article.networkdata.*
import fit.asta.health.old.article.networkdata.*
import fit.asta.health.utils.GenericAdapter
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl


class ArticlePointsAdapter(
    val context: Context,
    private val metaData: MetaData?,
    private val pointType: PointType,
    points: ArrayList<Topic>
) : GenericAdapter<Topic>(points) {

    override fun setViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (pointType) {
            PointType.BULLETS_TITLE -> {

                BulletsViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.article_point_bullet_title,
                        parent,
                        false
                    )
                )
            }
            PointType.BULLETS_TEXT -> {

                BulletsViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.article_point_bullet_text,
                        parent,
                        false
                    )
                )
            }
            PointType.NUMERIC_TITLE -> {

                NumericViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.article_point_numeric_title,
                        parent,
                        false
                    )
                )
            }
            PointType.NUMERIC_TEXT -> {

                NumericViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.article_point_numeric_text,
                        parent,
                        false
                    )
                )
            }
            PointType.ALPHA_TITLE -> {

                AlphaViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.article_point_numeric_title,
                        parent,
                        false
                    )
                )
            }
            PointType.ALPHA_TEXT -> {

                AlphaViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.article_point_numeric_text,
                        parent,
                        false
                    )
                )
            }
            PointType.KEY_VALUE -> {

                KeyValueViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.article_point_key_value,
                        parent,
                        false
                    )
                )
            }
            PointType.URLS -> {

                UrlViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.article_point_url,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, item: Topic, pos: Int) {

        when (pointType) {
            PointType.BULLETS_TITLE -> {

                val itemHolder = holder as BulletsViewHolder
                itemHolder.setData(item)
            }
            PointType.BULLETS_TEXT -> {

                val itemHolder = holder as BulletsViewHolder
                itemHolder.setData(item)
            }
            PointType.NUMERIC_TITLE -> {

                val itemHolder = holder as NumericViewHolder
                itemHolder.setData(item, pos)
            }
            PointType.NUMERIC_TEXT -> {

                val itemHolder = holder as NumericViewHolder
                itemHolder.setData(item, pos)
            }
            PointType.ALPHA_TITLE -> {

                val itemHolder = holder as AlphaViewHolder
                itemHolder.setData(item, pos)
            }
            PointType.ALPHA_TEXT -> {

                val itemHolder = holder as AlphaViewHolder
                itemHolder.setData(item, pos)
            }
            PointType.KEY_VALUE -> {

                val itemHolder = holder as KeyValueViewHolder
                itemHolder.setData(item)
            }
            PointType.URLS -> {

                val itemHolder = holder as UrlViewHolder
                itemHolder.setData(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return pointType.value
    }

    fun showMedia(media: Media?, itemView: View) {

        val articleMediaFrame = itemView.findViewById<ConstraintLayout>(R.id.articleMediaFrame)
        articleMediaFrame.visibility = View.GONE
        val articleMediaVideoButton =
            itemView.findViewById<AppCompatImageButton>(R.id.articleMediaVideoButton)
        articleMediaVideoButton.visibility = View.GONE
        if (media != null) {

            articleMediaFrame.visibility = View.VISIBLE

            val imgUri = Uri.parse(
                getPublicStorageUrl(
                    context,
                    metaData?.imgLoc + media.url
                )
            )
            context.showImageByUrl(imgUri, itemView.findViewById(R.id.articleMediaImage))
            if (media.type == MediaType.VIDEO) {

                articleMediaVideoButton.visibility = View.VISIBLE
            }
        }
    }

    inner class BulletsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(item: Topic) {

            if (item.title != null) {

                itemView.findViewById<AppCompatTextView>(R.id.articlePointBulletTitle).text =
                    item.title
            }

            if (item.text != null) {

                itemView.findViewById<AppCompatTextView>(R.id.articlePointBulletText).text =
                    item.text
            }

            showMedia(item.media, itemView)
        }
    }

    inner class NumericViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun setData(item: Topic, pos: Int) {

            itemView.findViewById<AppCompatTextView>(R.id.articlePointNumericTitleNumber).text =
                "${pos + 1}. "
            if (item.title != null) {

                itemView.findViewById<AppCompatTextView>(R.id.articlePointNumericTitle).text =
                    item.title
            }
            itemView.findViewById<AppCompatTextView>(R.id.articlePointNumericText).text = item.text

            showMedia(item.media, itemView)
        }
    }

    inner class AlphaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun setData(item: Topic, pos: Int) {

            val char = (pos + 97).toChar()
            itemView.findViewById<AppCompatTextView>(R.id.articlePointNumericTitleNumber).text =
                "$char. "
            if (item.title != null) {

                itemView.findViewById<AppCompatTextView>(R.id.articlePointNumericTitle).text =
                    item.title
            }
            itemView.findViewById<AppCompatTextView>(R.id.articlePointNumericText).text = item.text

            showMedia(item.media, itemView)
        }
    }

    inner class KeyValueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(item: Topic) {

            itemView.findViewById<AppCompatTextView>(R.id.articlePointKey).text = item.title
            itemView.findViewById<AppCompatTextView>(R.id.articlePointValue).text = item.text
        }
    }

    inner class UrlViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(item: Topic) {

            val text = "<a href=\"${item.text}\">${item.title}</a>"
            val articlePointUrl = itemView.findViewById<AppCompatTextView>(R.id.articlePointUrl)
            articlePointUrl.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
            articlePointUrl.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}