package fit.asta.health.data.spotify.repo

import android.util.Log
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.spotify.local.MusicDao
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

class MusicRepositoryImplTest {

    @RelaxedMockK
    lateinit var musicDao: MusicDao

    private lateinit var musicRepositoryImpl: MusicRepositoryImpl

    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this, relaxed = true)
        musicRepositoryImpl = spyk(MusicRepositoryImpl(musicDao))
    }


    @Nested
    @DisplayName("Get All Tracks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAllTracks {


        @Test
        fun `getAllTracks, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { musicDao.getAllTracks() } returns listOf()
            val response = musicRepositoryImpl.getAllTracks()
            coVerify { musicDao.getAllTracks() }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getAllTracks, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { musicDao.getAllTracks() } throws Exception()
            val response = musicRepositoryImpl.getAllTracks()
            coVerify { musicDao.getAllTracks() }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Insert Track")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class InsertTrack {


        @Test
        fun `insertTrack, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { musicDao.insertTrack(any()) } returns Unit
            val response = musicRepositoryImpl.insertTrack(mockk())
            coVerify { musicDao.insertTrack(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `insertTrack, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { musicDao.insertTrack(any()) } throws Exception()
            val response = musicRepositoryImpl.insertTrack(mockk())
            coVerify { musicDao.insertTrack(any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Delete Track")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteTrack {


        @Test
        fun `deleteTrack, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { musicDao.deleteTrack(any()) } returns Unit
            val response = musicRepositoryImpl.deleteTrack(mockk())
            coVerify { musicDao.deleteTrack(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `deleteTrack, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { musicDao.deleteTrack(any()) } throws Exception()
            val response = musicRepositoryImpl.deleteTrack(mockk())
            coVerify { musicDao.deleteTrack(any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get All Albums")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAllAlbums {


        @Test
        fun `getAllAlbums, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { musicDao.getAllAlbums() } returns listOf()
            val response = musicRepositoryImpl.getAllAlbums()
            coVerify { musicDao.getAllAlbums() }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getAllAlbums, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { musicDao.getAllAlbums() } throws Exception()
            val response = musicRepositoryImpl.getAllAlbums()
            coVerify { musicDao.getAllAlbums() }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Insert Album")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class InsertAlbum {


        @Test
        fun `insertAlbum, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { musicDao.insertAlbum(any()) } returns Unit
            val response = musicRepositoryImpl.insertAlbum(mockk())
            coVerify { musicDao.insertAlbum(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `insertAlbum, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { musicDao.insertAlbum(any()) } throws Exception()
            val response = musicRepositoryImpl.insertAlbum(mockk())
            coVerify { musicDao.insertAlbum(any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Delete Album")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteAlbum {


        @Test
        fun `deleteAlbum, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { musicDao.deleteAlbum(any()) } returns Unit
            val response = musicRepositoryImpl.deleteAlbum(mockk())
            coVerify { musicDao.deleteAlbum(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `deleteAlbum, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { musicDao.deleteAlbum(any()) } throws Exception()
            val response = musicRepositoryImpl.deleteAlbum(mockk())
            coVerify { musicDao.deleteAlbum(any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }
}