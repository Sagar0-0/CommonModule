package fit.asta.health.data.profile.local

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fit.asta.health.data.profile.local.entity.ProfileEntity

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile_table WHERE profileId = 1 LIMIT 1")
    suspend fun getProfile(): ProfileEntity?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProfile(profileEntity: ProfileEntity)

}