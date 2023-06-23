package fit.asta.health.common.location.maps.api

import fit.asta.health.common.location.maps.modal.AddressesResponse
import fit.asta.health.common.location.maps.modal.AddressesResponse.*
import fit.asta.health.common.location.maps.modal.DeleteAddressResponse
import fit.asta.health.common.location.maps.modal.PutAddressResponse
import retrofit2.http.*

interface RemoteApi {

    @GET("get/all/")
    suspend fun getAddresses(
        @Query("uid") uid: String
    ): AddressesResponse

    @PUT("put")
    suspend fun addNewAddress(@Body address: Address): PutAddressResponse

    @DELETE("delete")
    suspend fun deleteAddress(
        @Query("uid") uid: String,
        @Query("lid") id: String
    ): DeleteAddressResponse

    @PUT("current/put/")
    suspend fun selectCurrent(
        @Query("cid") cid: String,
        @Query("pid") pid: String?,
    )
}