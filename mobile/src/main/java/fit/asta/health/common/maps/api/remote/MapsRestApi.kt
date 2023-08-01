package fit.asta.health.common.maps.api.remote

import fit.asta.health.common.maps.modal.AddressesResponse
import fit.asta.health.common.utils.NetworkUtil
import okhttp3.OkHttpClient

class MapsRestApi(baseUrl: String, client: OkHttpClient) : MapsApi {

    private val apiService: MapsApiService =
        NetworkUtil.getRetrofit(baseUrl = baseUrl, client = client)
            .create(MapsApiService::class.java)

    override suspend fun getAddresses(uid: String) = apiService.getAddresses(uid)

    override suspend fun addNewAddress(myAddress: AddressesResponse.MyAddress) =
        apiService.addNewAddress(myAddress)

    override suspend fun deleteAddress(uid: String, id: String) = apiService.deleteAddress(uid, id)

    override suspend fun selectCurrent(cid: String, pid: String?) =
        apiService.selectCurrent(cid, pid)
}