package fit.asta.health.common.address.ui.vm

import android.location.Address
import app.cash.turbine.test
import com.google.android.gms.maps.model.LatLng
import fit.asta.health.common.address.data.modal.MyAddress
import fit.asta.health.common.address.data.modal.SearchResponse
import fit.asta.health.common.address.data.repo.AddressRepoImpl
import fit.asta.health.common.utils.LocationResponse
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import health.BaseTest
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Locale

class AddressViewModelTest : BaseTest() {

    private lateinit var viewmodel: AddressViewModel

    private val repo: AddressRepoImpl = mockk(relaxed = true)

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        viewmodel = spyk(
            AddressViewModel(
                repo,
                ""
            )
        )
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }

    @Test
    fun `getSavedAddress Success,returns UiState Success`() = runTest {
        coEvery { repo.getSavedAddresses(any()) } returns ResponseState.Success(listOf())
        viewmodel.getSavedAddresses()
        coVerify { repo.getSavedAddresses("") }
        viewmodel.savedAddressListState.test {
            assert(awaitItem() is UiState.Success)
        }
    }

    @Test
    fun `getSavedAddress Error,returns UiState Error`() = runTest {
        coEvery { repo.getSavedAddresses(any()) } returns ResponseState.Error(Exception())
        viewmodel.getSavedAddresses()
        coVerify { repo.getSavedAddresses("") }
        viewmodel.savedAddressListState.test {
            assert(awaitItem() is UiState.Error)
        }
    }

    @Test
    fun `getSavedAddress Success,returns Same List`() = runTest {
        val list = listOf(MyAddress(), MyAddress())
        coEvery { repo.getSavedAddresses(any()) } returns ResponseState.Success(list)
        viewmodel.getSavedAddresses()
        coVerify { repo.getSavedAddresses("") }
        viewmodel.savedAddressListState.test {
            val item = awaitItem()
            assert(item is UiState.Success)
            assertEquals((item as UiState.Success<List<MyAddress>>).data, list)
        }
    }

    @Test
    fun `putAddress Success,returns Success`() = runTest {
        coEvery { repo.putAddress(any()) } returns ResponseState.Success(true)
        viewmodel.putAddress(MyAddress())
        coVerify { repo.putAddress(any()) }
        viewmodel.putAddressState.test {
            val item = awaitItem()
            assert(item is UiState.Success)
        }
    }

    @Test
    fun `putAddress Error,returns Error`() = runTest {
        coEvery { repo.putAddress(any()) } returns ResponseState.Error(Exception())
        viewmodel.putAddress(MyAddress())
        coVerify { repo.putAddress(any()) }
        viewmodel.putAddressState.test {
            val item = awaitItem()
            assert(item is UiState.Error)
        }
    }


    @Test
    fun `deleteAddress Success,returns Success`() = runTest {
        coEvery { repo.deleteAddress(any(), any()) } returns ResponseState.Success(true)
        viewmodel.deleteAddress("")
        coVerify { repo.deleteAddress(any(), any()) }
        viewmodel.deleteAddressState.test {
            val item = awaitItem()
            assert(item is UiState.Success)
        }
    }

    @Test
    fun `deleteAddress Error,returns Error`() = runTest {
        coEvery { repo.deleteAddress(any(), any()) } returns ResponseState.Error(Exception())
        viewmodel.deleteAddress("")
        coVerify { repo.deleteAddress(any(), any()) }
        viewmodel.deleteAddressState.test {
            val item = awaitItem()
            assert(item is UiState.Error)
        }
    }

    @Test
    fun `selectAddress Success,returns Success`() = runTest {
        coEvery { repo.selectAddress(any(), any()) } returns ResponseState.Success(Unit)
        viewmodel.selectAddress(MyAddress())
        coVerify { repo.selectAddress(any(), any()) }
        viewmodel.selectAddressState.test {
            val item = awaitItem()
            assert(item is UiState.Success)
        }
    }

    @Test
    fun `selectAddress Error,returns Error`() = runTest {
        coEvery { repo.selectAddress(any(), any()) } returns ResponseState.Error(Exception())
        viewmodel.selectAddress(MyAddress())
        coVerify { repo.selectAddress(any(), any()) }
        viewmodel.selectAddressState.test {
            val item = awaitItem()
            assert(item is UiState.Error)
        }
    }

    @Test
    fun `search Success,returns Success`() = runTest {
        coEvery { repo.search(any(), any()) } returns ResponseState.Success(SearchResponse())
        viewmodel.search("")
        coVerify { repo.search(any(), any()) }
        viewmodel.searchResultState.test {
            val item = awaitItem()
            assert(item is UiState.Success)
        }
    }

    @Test
    fun `search Error,returns Error`() = runTest {
        coEvery { repo.search(any(), any()) } returns ResponseState.Error(Exception())
        viewmodel.search("")
        coVerify { repo.search(any(), any()) }
        viewmodel.searchResultState.test {
            val item = awaitItem()
            assert(item is UiState.Error)
        }
    }

    @Test
    fun `getMarkerAddressDetails Success,returns Success`() = runTest {
        coEvery { repo.getAddressDetails(any()) } returns flow {
            emit(
                ResponseState.Success(
                    Address(
                        Locale("")
                    )
                )
            )
        }
        viewmodel.getMarkerAddressDetails(LatLng(0.0, 0.0))
        advanceUntilIdle()
        coVerify { repo.getAddressDetails(any()) }
        viewmodel.markerAddressState.test {
            val item = awaitItem()
            assert(item is UiState.Success)
        }
    }

    @Test
    fun `getMarkerAddressDetails Error,returns Error`() = runTest {
        coEvery { repo.getAddressDetails(any()) } returns flow {
            emit(ResponseState.Error(Exception()))
        }
        viewmodel.getMarkerAddressDetails(LatLng(0.0, 0.0))
        advanceUntilIdle()
        coVerify { repo.getAddressDetails(any()) }
        viewmodel.markerAddressState.test {
            val item = awaitItem()
            assert(item is UiState.Error)
        }
    }

    @Test
    fun `getMarkerAddressDetails Success,returns Same Address`() = runTest {
        val address = Address(
            Locale("")
        )
        coEvery { repo.getAddressDetails(any()) } returns flow {
            emit(
                ResponseState.Success(
                    address
                )
            )
        }
        viewmodel.getMarkerAddressDetails(LatLng(0.0, 0.0))
        advanceUntilIdle()
        coVerify { repo.getAddressDetails(any()) }
        viewmodel.markerAddressState.test {
            val item = awaitItem()
            assert(item is UiState.Success)
            assertEquals((item as UiState.Success).data, address)
        }
    }

    @Test
    fun `checkPermissionAndGetLatLng Success, getAddressDetails Success, return Address Success`() =
        runTest {
            coEvery { repo.checkPermissionAndGetLatLng() } returns flow {
                emit(LocationResponse.Success(LatLng(0.0, 0.0)))
            }
            coEvery { repo.getAddressDetails(any()) } returns flow {
                emit(ResponseState.Success(Address(Locale(""))))
            }
            viewmodel.checkPermissionAndUpdateCurrentAddress()
            coVerify { repo.checkPermissionAndGetLatLng() }
            coVerify { repo.getAddressDetails(any()) }
            viewmodel.currentAddressState.test {
                val item = awaitItem()
                assert(item is UiState.Success)
            }
        }

    @Test
    fun `checkPermissionAndGetLatLng Success, getAddressDetails Error, return Address Error`() =
        runTest {
            coEvery { repo.checkPermissionAndGetLatLng() } returns flow {
                emit(LocationResponse.Success(LatLng(0.0, 0.0)))
            }
            coEvery { repo.getAddressDetails(any()) } returns flow {
                emit(ResponseState.Error(Exception()))
            }
            viewmodel.checkPermissionAndUpdateCurrentAddress()
            coVerify { repo.checkPermissionAndGetLatLng() }
            coVerify { repo.getAddressDetails(any()) }
            viewmodel.currentAddressState.test {
                val item = awaitItem()
                assert(item is UiState.Error)
            }
        }

    @Test
    fun `checkPermissionAndGetLatLng Error, return Address Error`() = runTest {
        coEvery { repo.checkPermissionAndGetLatLng() } returns flow {
            emit(LocationResponse.Error(0))
        }
        viewmodel.checkPermissionAndUpdateCurrentAddress()
        coVerify { repo.checkPermissionAndGetLatLng() }
        viewmodel.currentAddressState.test {
            val item = awaitItem()
            assert(item is UiState.Error)
        }
    }

    @Test
    fun `checkPermissionAndGetLatLng PermissionDenied, updates isPermissionGranted`() = runTest {
        coEvery { repo.checkPermissionAndGetLatLng() } returns flow {
            emit(LocationResponse.PermissionDenied)
        }
        viewmodel.checkPermissionAndUpdateCurrentAddress()
        coVerify { repo.checkPermissionAndGetLatLng() }
        viewmodel.isPermissionGranted.test {
            val item = awaitItem()
            assert(!item)
        }
    }

    @Test
    fun `checkPermissionAndGetLatLng ServiceDisabled, updates isLocationEnabled`() = runTest {
        coEvery { repo.checkPermissionAndGetLatLng() } returns flow {
            emit(LocationResponse.ServiceDisabled)
        }
        viewmodel.checkPermissionAndUpdateCurrentAddress()
        coVerify { repo.checkPermissionAndGetLatLng() }
        viewmodel.isLocationEnabled.test {
            val item = awaitItem()
            assert(!item)
        }
    }

    @Test
    fun `location enabled, updates isLocationEnabled`() = runTest {
        coEvery { repo.isLocationEnabled() } returns true
        viewmodel.setIsLocationEnabled()
        coVerify { repo.isLocationEnabled() }
        viewmodel.isLocationEnabled.test {
            val item = awaitItem()
            assert(item)
        }
    }

    @Test
    fun `location disabled, updates isLocationEnabled`() = runTest {
        coEvery { repo.isLocationEnabled() } returns false
        viewmodel.setIsLocationEnabled()
        coVerify { repo.isLocationEnabled() }
        viewmodel.isLocationEnabled.test {
            val item = awaitItem()
            assert(!item)
        }
    }

    @Test
    fun `permission enabled, updates isPermissionGranted`() = runTest {
        coEvery { repo.isPermissionGranted() } returns true
        viewmodel.setIsPermissionGranted()
        coVerify { repo.isPermissionGranted() }
        viewmodel.isPermissionGranted.test {
            val item = awaitItem()
            assert(item)
        }
    }

    @Test
    fun `permission disabled, updates isPermissionGranted`() = runTest {
        coEvery { repo.isPermissionGranted() } returns false
        viewmodel.setIsPermissionGranted()
        coVerify { repo.isPermissionGranted() }
        viewmodel.isPermissionGranted.test {
            val item = awaitItem()
            assert(!item)
        }
    }

    @Test
    fun `updateLocationPermissionRejectedCount, calls repo`() = runTest {
        coEvery { repo.updateLocationPermissionRejectedCount(any()) } just Runs
        viewmodel.updateLocationPermissionRejectedCount(1)
        coVerify { repo.updateLocationPermissionRejectedCount(1) }
    }

//    @Test
//    fun `updateLocationPermissionRejectedCount, follows repo`() = runTest {
//        val user = UserPreferencesData(locationPermissionRejectedCount = 1)
//        coEvery { repo.userPreferences }returns flow {
//            emit(user)
//        }
//        viewmodel.locationPermissionRejectedCount
//        advanceUntilIdle()
//        coVerify { repo.userPreferences }
//        viewmodel.locationPermissionRejectedCount.test {
//            val item = awaitItem()
//            assert(item==user.locationPermissionRejectedCount)
//        }
//    }

}