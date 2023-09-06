package fit.asta.health.feature.spotify.vm

import app.cash.turbine.test
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.core.test.BaseTest
import fit.asta.health.data.spotify.model.common.Album
import fit.asta.health.data.spotify.model.common.Track
import fit.asta.health.data.spotify.model.library.albums.SpotifyLibraryAlbumModel
import fit.asta.health.data.spotify.model.library.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.data.spotify.model.library.following.SpotifyUserFollowingArtist
import fit.asta.health.data.spotify.model.library.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.data.spotify.model.library.shows.SpotifyLibraryShowsModel
import fit.asta.health.data.spotify.model.library.tracks.SpotifyLibraryTracksModel
import fit.asta.health.data.spotify.model.me.SpotifyMeModel
import fit.asta.health.data.spotify.model.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.data.spotify.model.search.ArtistList
import fit.asta.health.data.spotify.model.search.TrackList
import fit.asta.health.data.spotify.repo.MusicRepositoryImpl
import fit.asta.health.data.spotify.repo.SpotifyRepoImpl
import fit.asta.health.feature.spotify.events.SpotifyUiEvent
import fit.asta.health.feature.spotify.viewmodel.SpotifyViewModelX
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

class SpotifyViewModelTest : BaseTest() {

    private lateinit var viewModel: SpotifyViewModelX
    private val remoteRepo: SpotifyRepoImpl = mockk(relaxed = true)
    private val localRepo: MusicRepositoryImpl = mockk(relaxed = true)


    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        viewModel = spyk(
            SpotifyViewModelX(remoteRepo, localRepo)
        )
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }

    @Nested
    @DisplayName("Get Current User Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUser {

        @Test
        fun `getCurrentUserDetails, returns Success`() = runTest {

            val expected = ResponseState.Success<SpotifyMeModel>(mockk())
            coEvery { remoteRepo.getCurrentUserDetails("") } returns expected

            viewModel.getCurrentUserDetails("")

            coVerify { remoteRepo.getCurrentUserDetails("") }
            viewModel.currentUserData.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getCurrentUserDetails, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { remoteRepo.getCurrentUserDetails("") } returns expected

            viewModel.getCurrentUserDetails("")

            coVerify { remoteRepo.getCurrentUserDetails("") }
            viewModel.currentUserData.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get All Local Tracks ")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAllTracks {

        @Test
        fun `getAllTracks, returns Success`() = runTest {

            val expected = ResponseState.Success<List<Track>>(listOf())
            coEvery { localRepo.getAllTracks() } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.LocalIO.LoadAllTracks)

            coVerify { localRepo.getAllTracks() }
            viewModel.allTracks.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getAllTracks, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { localRepo.getAllTracks() } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.LocalIO.LoadAllTracks)

            coVerify { localRepo.getAllTracks() }
            viewModel.allTracks.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get All Local Albums ")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAllAlbums {

        @Test
        fun `getAllAlbums, returns Success`() = runTest {

            val expected = ResponseState.Success<List<Album>>(mockk())
            coEvery { localRepo.getAllAlbums() } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.LocalIO.LoadAllAlbums)

            coVerify { localRepo.getAllAlbums() }
            viewModel.allAlbums.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getAllAlbums, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { localRepo.getAllAlbums() } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.LocalIO.LoadAllAlbums)

            coVerify { localRepo.getAllAlbums() }
            viewModel.allAlbums.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Insert Track")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class InsertTrack {

        @Test
        fun `insertTrack, returns Success`() = runTest {

            val expected = ResponseState.Success<Unit>(mockk())
            coEvery { localRepo.insertTrack(any()) } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.LocalIO.InsertTrack(mockk()))

            coVerify { localRepo.insertTrack(any()) }
        }

        @Test
        fun `insertTrack, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { localRepo.insertTrack(any()) } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.LocalIO.InsertTrack(mockk()))

            coVerify { localRepo.insertTrack(any()) }
        }
    }


    @Nested
    @DisplayName("Delete Track")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteTrack {

        @Test
        fun `deleteTrack, returns Success`() = runTest {

            val expected = ResponseState.Success<Unit>(mockk())
            coEvery { localRepo.deleteTrack(any()) } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.LocalIO.DeleteTrack(mockk()))

            coVerify { localRepo.deleteTrack(any()) }
        }

        @Test
        fun `deleteTrack, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { localRepo.deleteTrack(any()) } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.LocalIO.DeleteTrack(mockk()))

            coVerify { localRepo.deleteTrack(any()) }
        }
    }


    @Nested
    @DisplayName("Insert Album")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class InsertAlbum {

        @Test
        fun `insertAlbum, returns Success`() = runTest {

            val expected = ResponseState.Success<Unit>(mockk())
            coEvery { localRepo.insertAlbum(any()) } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.LocalIO.InsertAlbum(mockk()))

            coVerify { localRepo.insertAlbum(any()) }
        }

        @Test
        fun `insertAlbum, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { localRepo.insertAlbum(any()) } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.LocalIO.InsertAlbum(mockk()))

            coVerify { localRepo.insertAlbum(any()) }
        }
    }


    @Nested
    @DisplayName("Delete Album")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteAlbum {

        @Test
        fun `deleteAlbum, returns Success`() = runTest {

            val expected = ResponseState.Success<Unit>(mockk())
            coEvery { localRepo.deleteAlbum(any()) } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.LocalIO.DeleteAlbum(mockk()))

            coVerify { localRepo.deleteAlbum(any()) }
        }

        @Test
        fun `deleteAlbum, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { localRepo.deleteAlbum(any()) } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.LocalIO.DeleteAlbum(mockk()))

            coVerify { localRepo.deleteAlbum(any()) }
        }
    }


    @Nested
    @DisplayName("Get Current User Recently Played Tracks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserRecentlyPlayedTracks {

        @Test
        fun `getCurrentUserRecentlyPlayedTracks, returns Success`() = runTest {

            val expected = ResponseState.Success<SpotifyUserRecentlyPlayedModel>(mockk())
            coEvery { remoteRepo.getCurrentUserRecentlyPlayedTracks("") } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadCurrentUserRecentlyPlayedTracks)

            coVerify { remoteRepo.getCurrentUserRecentlyPlayedTracks("") }
            viewModel.userRecentlyPlayedTracks.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getCurrentUserRecentlyPlayedTracks, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { remoteRepo.getCurrentUserRecentlyPlayedTracks("") } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadCurrentUserRecentlyPlayedTracks)

            coVerify { remoteRepo.getCurrentUserRecentlyPlayedTracks("") }
            viewModel.userRecentlyPlayedTracks.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get Track Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetTrackDetails {

        @Test
        fun `getTrackDetails, returns Success`() = runTest {

            val expected = ResponseState.Success<Track>(mockk())
            coEvery { remoteRepo.getTrackDetails(any(), any()) } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadTrackDetails)

            coVerify { remoteRepo.getTrackDetails(any(), any()) }
            viewModel.trackDetailsResponse.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getTrackDetails, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { remoteRepo.getTrackDetails(any(), any()) } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadTrackDetails)

            coVerify { remoteRepo.getTrackDetails(any(), any()) }
            viewModel.trackDetailsResponse.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get User Top Tracks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetUserTopTracks {

        @Test
        fun `getUserTopTracks, returns Success`() = runTest {

            val expected = ResponseState.Success<TrackList>(mockk())
            coEvery { remoteRepo.getCurrentUserTopTracks(any()) } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadUserTopTracks)

            coVerify { remoteRepo.getCurrentUserTopTracks(any()) }
            viewModel.userTopTracks.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getUserTopTracks, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { remoteRepo.getCurrentUserTopTracks(any()) } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadUserTopTracks)

            coVerify { remoteRepo.getCurrentUserTopTracks(any()) }
            viewModel.userTopTracks.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get User Top Artists")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetUserTopArtists {

        @Test
        fun `getUserTopArtists, returns Success`() = runTest {

            val expected = ResponseState.Success<ArtistList>(mockk())
            coEvery { remoteRepo.getCurrentUserTopArtists("") } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadUserTopArtists)

            coVerify { remoteRepo.getCurrentUserTopArtists("") }
            viewModel.userTopArtists.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getUserTopArtists, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { remoteRepo.getCurrentUserTopArtists(any()) } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadUserTopArtists)

            coVerify { remoteRepo.getCurrentUserTopArtists(any()) }
            viewModel.userTopArtists.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get Current User Tracks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserTracks {

        @Test
        fun `getCurrentUserTracks, returns Success`() = runTest {

            val expected = ResponseState.Success<SpotifyLibraryTracksModel>(mockk())
            coEvery { remoteRepo.getCurrentUserTracks("") } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadCurrentUserTracks)

            coVerify { remoteRepo.getCurrentUserTracks("") }
            viewModel.currentUserTracks.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getCurrentUserTracks, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { remoteRepo.getCurrentUserTracks("") } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadCurrentUserTracks)

            coVerify { remoteRepo.getCurrentUserTracks("") }
            viewModel.currentUserTracks.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get Current User Playlist")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserPlaylist {

        @Test
        fun `getCurrentUserTracks, returns Success`() = runTest {

            val expected = ResponseState.Success<SpotifyUserPlaylistsModel>(mockk())
            coEvery { remoteRepo.getCurrentUserPlaylists("") } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadCurrentUserPlaylist)

            coVerify { remoteRepo.getCurrentUserPlaylists("") }
            viewModel.currentUserPlaylist.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getCurrentUserTracks, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { remoteRepo.getCurrentUserPlaylists("") } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadCurrentUserPlaylist)

            coVerify { remoteRepo.getCurrentUserPlaylists("") }
            viewModel.currentUserPlaylist.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get Current User Artists")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserArtists {

        @Test
        fun `getCurrentUserArtists, returns Success`() = runTest {

            val expected = ResponseState.Success<SpotifyUserFollowingArtist>(mockk())
            coEvery { remoteRepo.getCurrentUserFollowedArtists("") } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadCurrentUserArtists)

            coVerify { remoteRepo.getCurrentUserFollowedArtists("") }
            viewModel.currentUserFollowingArtist.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getCurrentUserArtists, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { remoteRepo.getCurrentUserFollowedArtists("") } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadCurrentUserArtists)

            coVerify { remoteRepo.getCurrentUserFollowedArtists("") }
            viewModel.currentUserFollowingArtist.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get Current User Album")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserAlbum {

        @Test
        fun `getCurrentUserAlbum, returns Success`() = runTest {

            val expected = ResponseState.Success<SpotifyLibraryAlbumModel>(mockk())
            coEvery { remoteRepo.getCurrentUserAlbums("") } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadCurrentUserAlbum)

            coVerify { remoteRepo.getCurrentUserAlbums("") }
            viewModel.currentUserAlbum.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getCurrentUserAlbum, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { remoteRepo.getCurrentUserAlbums("") } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadCurrentUserAlbum)

            coVerify { remoteRepo.getCurrentUserAlbums("") }
            viewModel.currentUserAlbum.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get Current User Shows")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserShows {

        @Test
        fun `getCurrentUserShows, returns Success`() = runTest {

            val expected = ResponseState.Success<SpotifyLibraryShowsModel>(mockk())
            coEvery { remoteRepo.getCurrentUserShows("") } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadCurrentUserShows)

            coVerify { remoteRepo.getCurrentUserShows("") }
            viewModel.currentUserShow.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getCurrentUserShows, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { remoteRepo.getCurrentUserShows("") } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadCurrentUserShows)

            coVerify { remoteRepo.getCurrentUserShows("") }
            viewModel.currentUserShow.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get Current User Episode")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserEpisode {

        @Test
        fun `getCurrentUserEpisode, returns Success`() = runTest {

            val expected = ResponseState.Success<SpotifyLibraryEpisodesModel>(mockk())
            coEvery { remoteRepo.getCurrentUserEpisodes("") } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadCurrentUserEpisode)

            coVerify { remoteRepo.getCurrentUserEpisodes("") }
            viewModel.currentUserEpisode.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getCurrentUserEpisode, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { remoteRepo.getCurrentUserEpisodes("") } returns expected

            viewModel.uiEventListener(SpotifyUiEvent.NetworkIO.LoadCurrentUserEpisode)

            coVerify { remoteRepo.getCurrentUserEpisodes("") }
            viewModel.currentUserEpisode.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }
}