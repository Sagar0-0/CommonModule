package fit.asta.health.data.scheduler.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = "TAG_TABLE")
@Parcelize
data class TagEntity(
    @SerializedName("uid")
    var uid: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("url")
    var url: String = "",
    @SerializedName("dsc")
    var dsc: String = "",
    @SerializedName("ttl")
    var ttl: String = "",
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    var id: String = "",
) : Serializable, Parcelable
