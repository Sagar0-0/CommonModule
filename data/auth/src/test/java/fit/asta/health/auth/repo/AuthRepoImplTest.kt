package fit.asta.health.auth.repo

import com.google.firebase.auth.FirebaseAuth
import fit.asta.health.auth.model.AuthDataMapper
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.remote.AuthApi
import fit.asta.health.auth.remote.TokenApi
import fit.asta.health.auth.remote.TokenResponse
import fit.asta.health.common.utils.Response
import fit.asta.health.datastore.PrefManager
import fit.asta.health.datastore.ScreenCode
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AuthRepoImplTest {

    private lateinit var repo: AuthRepoImpl

    @RelaxedMockK
    lateinit var authApi: AuthApi

    @RelaxedMockK
    lateinit var tokenApi: TokenApi

    @RelaxedMockK
    lateinit var prefManager: PrefManager

    @RelaxedMockK
    lateinit var firebaseAuth: FirebaseAuth

    @RelaxedMockK
    lateinit var authDataMapper: AuthDataMapper

    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this, relaxed = true)
        every { prefManager.userData } returns mockk()
        repo = AuthRepoImpl(
            tokenApi,
            authApi,
            authDataMapper,
            mockk(),
            firebaseAuth,
            prefManager,
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `resetReferralCode, calls PrefManager`() = runTest {
        coEvery { prefManager.setReferralCode(any()) } returns Unit
        repo.resetReferralCode()
        coVerify { prefManager.setReferralCode("") }
    }

    @Test
    fun `setLogoutDone, sets PrefManager`() = runTest {
        coEvery { prefManager.setScreenCode(ScreenCode.Auth.code) } returns Unit
        repo.setLogoutDone()
        coVerify { prefManager.setScreenCode(ScreenCode.Auth.code) }
    }

    @Test
    fun `uploadFCMToken, returns Success`() = runTest {
        coEvery { tokenApi.sendToken(any()) } returns Response(data = TokenResponse())
        coEvery { prefManager.setIsFcmTokenUploaded(any()) } returns Unit
        repo.uploadFcmToken(mockk())
        coVerify { prefManager.setIsFcmTokenUploaded(true) }
        coVerify { tokenApi.sendToken(any()) }
    }

    @Test
    fun `uploadFCMToken, returns Error`() = runTest {
        coEvery { tokenApi.sendToken(any()) } returns Response(
            status = Response.Status(10, ""),
            TokenResponse()
        )
        coEvery { prefManager.setIsFcmTokenUploaded(any()) } returns Unit
        repo.uploadFcmToken(mockk())
        coVerify { prefManager.setIsFcmTokenUploaded(false) }
        coVerify { tokenApi.sendToken(any()) }
    }

    @Test
    fun `setIsFcmTokenUploaded, sets PrefManager`() = runTest {
        coEvery { prefManager.setIsFcmTokenUploaded(any()) } returns Unit
        repo.setIsFcmTokenUploaded(true)
        coVerify { prefManager.setIsFcmTokenUploaded(true) }
    }

    @Test
    fun `setLoginDone, sets PrefManager`() = runTest {
        coEvery { prefManager.setScreenCode(any()) } returns Unit
        repo.setLoginDone()
        coVerify { prefManager.setScreenCode(ScreenCode.BasicProfile.code) }
    }

    @Test
    fun `setBasicProfileDone, sets PrefManager`() = runTest {
        coEvery { prefManager.setScreenCode(any()) } returns Unit
        repo.setBasicProfileDone()
        coVerify { prefManager.setScreenCode(ScreenCode.Home.code) }
    }

    @Test
    fun `isAuthenticated, returns FirebaseUser`() = runTest {
        coEvery { firebaseAuth.currentUser } returns mockk()
        assertEquals(repo.isAuthenticated(), true)
    }

    @Test
    fun `isAuthenticated, returns null`() = runTest {
        coEvery { firebaseAuth.currentUser } returns null
        assertEquals(repo.isAuthenticated(), false)
    }

    @Test
    fun `getUserId, returns Uid`() = runTest {
        val uid = "abc"
        coEvery { firebaseAuth.uid } returns uid
        assertEquals(repo.getUserId(), uid)
    }

    @Test
    fun `getUserId, returns null`() = runTest {
        coEvery { firebaseAuth.uid } returns null
        assertEquals(repo.getUserId(), null)
    }

    @Test
    fun `getUser, returns User`() = runTest {
        val user = User()
        coEvery { authDataMapper.mapToUser(any()) } returns user
        coEvery { firebaseAuth.currentUser } returns mockk()
        assertEquals(repo.getUser(), user)
    }

    @Test
    fun `getUser, returns null`() = runTest {
        coEvery { firebaseAuth.currentUser } returns null
        assertEquals(repo.getUser(), null)
    }

}