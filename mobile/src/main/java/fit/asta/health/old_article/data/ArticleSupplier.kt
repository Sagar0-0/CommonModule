package fit.asta.health.old_article.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import fit.asta.health.old_article.networkdata.*
import fit.asta.health.utils.ONE_MEGABYTE
import fit.asta.health.utils.loadJSONFromAsset
import org.json.JSONException
import org.json.JSONObject


class ArticleSupplier {

    private val mStorageRef: StorageReference? = FirebaseStorage.getInstance().reference

    fun fetchArticle(url: String): LiveData<Article> {

        val mArticle = MutableLiveData<Article>()
        val asanSeqRef: StorageReference = mStorageRef!!.child(url)
        asanSeqRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->

            val jsonArticle = JSONObject(bytes.toString(Charsets.UTF_8))
            parseArticle(jsonArticle, mArticle)
        }

        return mArticle
    }

    fun fetchLocalArticle(context: Context, url: String): LiveData<Article> {

        val mArticle = MutableLiveData<Article>()
        val jsonArticle = JSONObject(context.loadJSONFromAsset(url)!!)
        parseArticle(jsonArticle, mArticle)

        return mArticle
    }

    private fun parseArticle(jsonArticle: JSONObject, mArticle: MutableLiveData<Article>) {

        try {

            val metaData = if (jsonArticle.has("meta_data")) {

                val jsonMetaData = jsonArticle.getJSONObject("meta_data")
                val adoLoc =
                    if (jsonMetaData.has("audio_loc")) jsonMetaData.getString("audio_loc") else ""
                val imgLoc =
                    if (jsonMetaData.has("image_loc")) jsonMetaData.getString("image_loc") else ""
                val vdoLoc =
                    if (jsonMetaData.has("video_loc")) jsonMetaData.getString("video_loc") else ""

                MetaData(
                    verifyUrlLoc(adoLoc),
                    verifyUrlLoc(imgLoc),
                    verifyUrlLoc(vdoLoc)
                )

            } else null

            val pubDate =
                if (jsonArticle.has("pub_date")) jsonArticle.getString("pub_date") else null
            val author = if (jsonArticle.has("author")) {

                val jsonAuthor = jsonArticle.getJSONObject("author")
                val name = if (jsonAuthor.has("name")) jsonAuthor.getString("name") else null
                val imgUrl =
                    if (jsonAuthor.has("imgUrl")) jsonAuthor.getString("imgUrl") else null
                val about = if (jsonAuthor.has("about")) jsonAuthor.getString("about") else null
                Author(name!!, imgUrl, about)
            } else null

            val jsonArrContent = jsonArticle.getJSONArray("content")
            val arrContent = ArrayList<Content>()
            for (inxContent in 0 until jsonArrContent.length()) {

                val jsonContent = jsonArrContent[inxContent] as JSONObject

                val contentType = jsonContent.getInt("type")
                val title = if (jsonContent.has("title")) jsonContent.getString("title") else null
                val text = if (jsonContent.has("text")) jsonContent.getString("text") else null

                val points = if (jsonContent.has("points")) {

                    val jsonPoints = jsonContent.getJSONObject("points")
                    val pointType = jsonPoints.getInt("type")
                    val jsonArrTopics = jsonPoints.getJSONArray("topics")

                    val arrPoints = ArrayList<Topic>()
                    for (inxTopic in 0 until jsonArrTopics.length()) {

                        val jsonPointTopic = jsonArrTopics[inxTopic] as JSONObject
                        val pointTopic = parseTopic(jsonPointTopic)
                        arrPoints.add(inxTopic, pointTopic)
                    }

                    Points(
                        PointType.valueOf(
                            pointType
                        ), arrPoints
                    )
                } else null

                val content = Content(
                    ContentType.valueOf(
                        contentType
                    ), title, text, points
                )
                arrContent.add(inxContent, content)
            }

            mArticle.value = Article(
                metaData,
                pubDate,
                author,
                arrContent
            )

        } catch (e: JSONException) {

            e.printStackTrace()
        }
    }

    private fun verifyUrlLoc(url: String): String {

        if (url.isNotEmpty() && url[url.length - 1] != '/')
            return "$url/"

        return url
    }

    private fun parseTopic(jsonTopic: JSONObject): Topic {

        val title = if (jsonTopic.has("title")) jsonTopic.getString("title") else null
        val text = if (jsonTopic.has("text")) jsonTopic.getString("text") else null

        val media = if (jsonTopic.has("media")) {

            val jsonMedia = jsonTopic.getJSONObject("media")
            val mediaType = jsonMedia.getInt("type")
            val mediaUrl = jsonMedia.getString("url")
            val mediaTitle = if (jsonMedia.has("title")) jsonMedia.getString("title") else null
            Media(
                MediaType.valueOf(
                    mediaType
                ), mediaUrl, mediaTitle
            )

        } else null

        return Topic(title, text, media)
    }
}