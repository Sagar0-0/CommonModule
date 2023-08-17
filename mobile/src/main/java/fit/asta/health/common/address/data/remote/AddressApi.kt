package fit.asta.health.common.address.data.remote

import fit.asta.health.common.address.data.modal.AddressesDTO
import fit.asta.health.common.address.data.modal.AddressesDTO.*
import fit.asta.health.common.address.data.modal.DeleteAddressResponse
import fit.asta.health.common.address.data.modal.MyAddress
import fit.asta.health.common.address.data.modal.PutAddressResponse
import retrofit2.http.*

interface AddressApi {

    @GET("location/get/all/")
    suspend fun getAddresses(
        @Query("uid") uid: String
    ): AddressesDTO

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