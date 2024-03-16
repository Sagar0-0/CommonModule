package fit.asta.health.data.address.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.data.address.remote.modal.DeleteAddressResponse
import fit.asta.health.data.address.remote.modal.MyAddress
import fit.asta.health.data.address.remote.modal.PutAddressResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface AddressApi {

    @GET("location/get/all/")
    suspend fun getAddresses(
        @Query("uid") uid: String
    ): Response<List<MyAddress>>

    @PUT("location/put")
    suspend fun putAddress(@Body myAddress: MyAddress): Response<PutAddressResponse>

    @DELETE("location/delete")
    suspend fun deleteAddress(
        @Query("uid") uid: String,
        @Query("lid") id: String
    ): Response<DeleteAddressResponse>

    @PUT("location/current/put/")
    suspend fun selectCurrent(
        @Query("cid") cid: String,
        @Query("pid") pid: String?,
    )
}