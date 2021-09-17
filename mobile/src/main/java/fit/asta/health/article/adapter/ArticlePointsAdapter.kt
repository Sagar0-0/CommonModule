package fit.asta.health.article.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.R
import fit.asta.health.article.networkdata.*
import fit.asta.health.utils.GenericAdapter
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl
import kotlinx.android.synthetic.main.article_media.view.*
import kotlinx.android.synthetic.main.article_point_bullet_title.view.*
import kotlinx.android.synthetic.main.article_point_key_value.view.*
import kotlinx.android.synthetic.main.article_point_numeric_title.view.*
import kotlinx.android.synthetic.main.article_point_url.view.*


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

        itemView.articleMediaFrame.visibility = View.GONE
        itemView.articleMediaVideoButton.visibility = View.GONE
        if (media != null) {

            itemView.articleMediaFrame.visibility = View.VISIBLE

            val imgUri = Uri.parse(
                getPublicStorageUrl(
                    context,
                    metaData?.imgLoc + media.url
                )
            )
            context.showImageByUrl(imgUri, itemView.articleMediaImage)
            if (media.type == MediaType.VIDEO) {

                itemView.articleMediaVideoButton.visibility = View.VISIBLE
            }
        }
    }

    inner class BulletsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(item: Topic) {

            if (item.title != null) {

                itemView.articlePointBulletTitle.text = item.title
            }

            if (item.text != null) {

                itemView.articlePointBulletText.text = item.text
            }

            showMedia(item.media, itemView)
        }
    }

    inner class NumericViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun setData(item: Topic, pos: Int) {

            itemView.articlePointNumericTitleNumber.text = "${pos + 1}. "
            if (item.title != null) {

                itemView.articlePointNumericTitle.text = item.title
            }
            itemView.articlePointNumericText.text = item.text

            showMedia(item.media, itemView)
        }
    }

    inner class AlphaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun setData(item: Topic, pos: Int) {

            val char = (pos + 97).toChar()
            itemView.articlePointNumericTitleNumber.text = "$char. "
            if (item.title != null) {

                itemView.articlePointNumericTitle.text = item.title
            }
            itemView.articlePointNumericText.text = item.text

            showMedia(item.media, itemView)
        }
    }

    inner class KeyValueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(item: Topic) {

            itemView.articlePointKey.text = item.title
            itemView.articlePointValue.text = item.text
        }
    }

    inner class UrlViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(item: Topic) {

            val text = "<a href=\"${item.text}\">${item.title}</a>"
            itemView.articlePointUrl.text =
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                    Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
                else
                    Html.fromHtml(text)

            itemView.articlePointUrl.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}