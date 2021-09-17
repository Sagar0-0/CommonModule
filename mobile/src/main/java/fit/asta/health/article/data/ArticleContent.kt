package fit.asta.health.article.data

import fit.asta.health.article.networkdata.ContentType
import fit.asta.health.article.networkdata.MetaData
import fit.asta.health.article.networkdata.Points

data class ArticleContent(
    val header: HeaderContent,
    val metaData: MetaData,
    val points: Points,
    val type: ContentType,
    val title: String = "",
    val text: String = "",
    val authorName: String = "")