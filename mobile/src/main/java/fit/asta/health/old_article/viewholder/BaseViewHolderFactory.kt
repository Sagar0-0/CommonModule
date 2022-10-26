package fit.asta.health.old_article.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old_article.data.ArticleContent
import fit.asta.health.old_article.networkdata.ContentType

class BaseViewHolderFactory {

    fun create(parent: ViewGroup, viewType: Int): BaseViewHolder<ArticleContent> {

        return when (ContentType.valueOf(viewType)) {
            ContentType.HEADER -> {

                HeaderViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.article_header, parent, false)
                )
            }
            ContentType.SUB_TOPIC -> {

                SubTopicViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.article_sub_topic, parent, false)
                )
            }
            ContentType.TEXT -> {

                TextViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.article_text, parent, false)
                )
            }
            ContentType.QUOTE -> {

                QuoteViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.article_quote, parent, false)
                )
            }
            ContentType.NOTE -> {

                NoteViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.article_note, parent, false)
                )
            }
            ContentType.IMAGE -> {

                ImageViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.article_image, parent, false)
                )
            }
            ContentType.VIDEO -> {

                VideoViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.article_video, parent, false)
                )
            }
            ContentType.POINTS -> {

                PointsViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.article_points, parent, false)
                )
            }
            ContentType.SOURCE -> {

                SourceViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.article_source, parent, false)
                )
            }
        }
    }
}