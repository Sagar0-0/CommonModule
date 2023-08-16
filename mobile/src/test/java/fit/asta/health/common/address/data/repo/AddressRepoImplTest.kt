package fit.asta.health.common.address.data.repo

import app.cash.turbine.test
import fit.asta.health.common.address.data.modal.AddressesDTO
import fit.asta.health.common.address.data.modal.DeleteAddressResponse
import fit.asta.health.common.address.data.modal.MyAddress
import fit.asta.health.common.address.data.modal.PutAddressResponse
import fit.asta.health.common.address.data.modal.SearchResponse
import fit.asta.health.common.address.data.remote.AddressApi
import fit.asta.health.common.address.data.remote.SearchLocationApi
import fit.asta.health.common.address.data.utils.LocationResourceProvider
import fit.asta.health.common.utils.LocationResponse
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.ResourcesProvider
import fit.asta.health.common.utils.ResponseState
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddressRepoImplTest{
    private lateinit var repo : AddressRepoImpl

    @MockK(relaxed = true)
    lateinit var addressApi: AddressApi

    @MockK(relaxed = true)
    lateinit var searchLocationApi: SearchLocationApi

    private val prefManager : PrefManager = mockk(relaxed = true)

    private val resourcesProvider : ResourcesProvider = mockk(relaxed = true)

    private val locationResourcesProvider : LocationResourceProvider = mockk(relaxed = true)



    @BeforeEach
    fun beforeEach(){
        MockKAnnotations.init(this, relaxed = true)
        repo = spyk(
            AddressRepoImpl(
                addressApi,
                searchLocationApi,
                prefManager,
                resourcesProvider,
                locationResourcesProvider
            )
        )
    }
    @AfterEach
    fun afterEach(){
        clearAllMocks()
    }

    @Test
    fun `isPermissionGranted, true`() = runTest {
        every { locationResourcesProvider.isPermissionGranted() }returns true
        assertEquals(repo.isPermissionGranted(),true)
    }

    @Test
    fun `isPermissionGranted, false`() = runTest {
        every { locationResourcesProvider.isPermissionGranted() }returns false
        assertEquals(repo.isPermissionGranted(),false)
    }

    @Test
    fun `isLocationEnabled, true`() = runTest {
        every { locationResourcesProvider.isLocationEnabled() }returns true
        assertEquals(repo.isLocationEnabled(),true)
    }

    @Test
    fun `isLocationEnabled, false`() = runTest {
        every { locationResourcesProvider.isLocationEnabled() }returns false
        assertEquals(repo.isLocationEnabled(),false)
    }

    @Test
    fun `checkPermissionAndGetLatLng, returns PermissionDenied`() = runTest{
        every { locationResourcesProvider.isPermissionGranted() }returns false
        repo.checkPermissionAndGetLatLng().test {
            val item = awaitItem()
            assert(item is LocationResponse.PermissionDenied)
        }
    }

    @Test
    fun `checkPermissionAndGetLatLng, returns ServiceDisabled`() = runTest{
        every { locationResourcesProvider.isPermissionGranted() }returns true
        every { locationResourcesProvider.isLocationEnabled() }returns false
        repo.checkPermissionAndGetLatLng().test {
            val item = awaitItem()
            assert(item is LocationResponse.ServiceDisabled)
        }
    }

    @Test
    fun updateLocationPermissionRejectedCount() = runTest {
        coEvery { prefManager.setLocationPermissionRejectedCount(any()) }just Runs
        repo.updateLocationPermissionRejectedCount(1)
        coVerify { prefManager.setLocationPermissionRejectedCount(1) }
    }

    @Test
    fun setCurrentLocation() = runTest {
        coEvery { prefManager.setCurrentLocation(any()) }just Runs
        repo.setCurrentLocation("1")
        coVerify { prefManager.setCurrentLocation("1") }
    }

    @Test
    fun `search ,return success`() = runTest {
        coEvery { searchLocationApi.search("","") }returns SearchResponse()
        repo.search("",0.0,0.0)
        coVerify { searchLocationApi.search("","") }
    }

    @Test
    fun `search with LatLng,return success`() = runTest {
        coEvery { searchLocationApi.searchBiased(any(),any(),any(),any()) }returns SearchResponse()
        repo.search("",1.0,1.0)
        coVerify { searchLocationApi.searchBiased(any(),any(),any(),any()) }
    }

    @Test
    fun `selectAddress, returns Success`() = runTest {
        coEvery { addressApi.selectCurrent(any(),any()) }just Runs
        val res = repo.selectAddress("","")
        coVerify { addressApi.selectCurrent("","") }
        assert(res is ResponseState.Success)
    }

    @Test
    fun `selectAddress, returns Error`() = runTest {
        coEvery { addressApi.selectCurrent(any(),any()) }throws Exception()
        val res = repo.selectAddress("","")
        coVerify { addressApi.selectCurrent("","") }
        assert(res is ResponseState.Error)
    }

    @Test
    fun `putAddress, returns Success`() = runTest {
        val myAddress = MyAddress()
        coEvery { addressApi.addNewAddress(any()) }returns PutAddressResponse()
        val res = repo.putAddress(myAddress)
        coVerify { addressApi.addNewAddress(myAddress) }
        assert(res is ResponseState.Success)
    }

    @Test
    fun `putAddress, returns Error`() = runTest {
        val myAddress = MyAddress()
        coEvery { addressApi.addNewAddress(any()) }throws Exception()
        val res = repo.putAddress(myAddress)
        coVerify { addressApi.addNewAddress(myAddress) }
        assert(res is ResponseState.Error)
    }

    @Test
    fun `deleteAddress, returns Success`() = runTest {
        coEvery { addressApi.deleteAddress(any(),any()) }returns DeleteAddressResponse()
        val res = repo.deleteAddress("","")
        coVerify { addressApi.deleteAddress("","") }
        assert(res is ResponseState.Success)
    }

    @Test
    fun `deleteAddress, returns Error`() = runTest {
        coEvery { addressApi.deleteAddress(any(),any()) }throws Exception()
        val res = repo.deleteAddress("","")
        coVerify { addressApi.deleteAddress("","") }
        assert(res is ResponseState.Error)
    }

    @Test
    fun `getSavedAddress, returns Success`() = runTest {
        coEvery { addressApi.getAddresses(any()) }returns AddressesDTO()
        val res = repo.getSavedAddresses("")
        coVerify { addressApi.getAddresses("") }
        assert(res is ResponseState.Success)
    }

    @Test
    fun `getSavedAddress, returns Error`() = runTest {
        coEvery { addressApi.getAddresses(any()) }throws Exception()
        val res = repo.getSavedAddresses("")
        coVerify { addressApi.getAddresses("") }
        assert(res is ResponseState.Error)
    }
}