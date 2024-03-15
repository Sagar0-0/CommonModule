package fit.asta.health.data.spotify.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import fit.asta.health.data.spotify.remote.model.common.Track
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException
import kotlin.test.assertEquals

class MusicDaoTest {

    private lateinit var musicDao: MusicDao
    private lateinit var database: MusicDatabase

    @BeforeEach
    fun beforeEach() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MusicDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        musicDao = database.musicDao()
    }

    @AfterEach
    @Throws(IOException::class)
    fun afterEach() {
        database.close()
    }

    @Test
    fun `getAllTracks, returns success`() = runTest {

        val track1 = mockk<Track>()
        val track2 = mockk<Track>()
        val mockTrack = listOf(track1, track2)

        musicDao.insertTrack(track1)
        musicDao.insertTrack(track2)

        val response = musicDao.getAllTracks()

        assertEquals(response.size, mockTrack.size)
    }

}