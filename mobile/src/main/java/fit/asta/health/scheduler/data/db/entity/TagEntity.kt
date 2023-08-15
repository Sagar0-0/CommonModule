package fit.asta.health.scheduler.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fit.asta.health.scheduler.data.api.net.tag.Data
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = "TAG_TABLE")
@Parcelize
data class TagEntity(
    @ColumnInfo(name = "selected")
    var selected: Boolean,
    @ColumnInfo(name = "meta")
    val meta: Data,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) : Serializable, Parcelable