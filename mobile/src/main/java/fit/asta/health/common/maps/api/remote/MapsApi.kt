package fit.asta.health.common.maps.api.remote

import fit.asta.health.common.maps.modal.AddressesResponse
import fit.asta.health.common.maps.modal.DeleteAddressResponse
import fit.asta.health.common.maps.modal.PutAddressResponse

interface MapsApi {
    suspend fun getAddresses(
        uid: String
    ): AddressesResponse

    suspend fun addNewAddress(myAddress: AddressesResponse.MyAddress): PutAddressResponse

    suspend fun deleteAddress(
        uid: String,
        id: String
    ): DeleteAddressResponse

    suspend fun selectCurrent(
        cid: String,
        pid: String?,
    )
}