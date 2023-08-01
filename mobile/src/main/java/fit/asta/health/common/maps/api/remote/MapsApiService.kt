package fit.asta.health.common.maps.api.remote

import fit.asta.health.common.maps.modal.AddressesResponse
import fit.asta.health.common.maps.modal.AddressesResponse.*
import fit.asta.health.common.maps.modal.DeleteAddressResponse
import fit.asta.health.common.maps.modal.PutAddressResponse
import retrofit2.http.*

interface MapsApiService {

    @GET("location/get/all/")
    suspend fun getAddresses(
        @Query("uid") uid: String
    ): AddressesResponse

    @PUT("location/put")
    suspend fun addNewAddress(@Body myAddress: MyAddress): PutAddressResponse

    @DELETE("location/delete")
    suspend fun deleteAddress(
        @Query("uid") uid: String,
        @Query("lid") id: String
    ): DeleteAddressResponse

    @PUT("location/current/put/")
    suspend fun selectCurrent(
        @Query("cid") cid: String,
        @Query("pid") pid: String?,
    )
}