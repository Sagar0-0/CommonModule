package fit.asta.health.data.profile.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "PROFILE_TABLE")
@Parcelize
data class ProfileEntity(
    @PrimaryKey
    val profileId: Long = 1,
    val name: String = "",
) : Parcelable
