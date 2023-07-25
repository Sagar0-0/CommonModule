package fit.asta.health.common.location.maps.repo

import fit.asta.health.common.location.maps.modal.AddressesResponse
import fit.asta.health.common.location.maps.modal.AddressesResponse.MyAddress
import fit.asta.health.common.location.maps.modal.DeleteAddressResponse
import fit.asta.health.common.location.maps.modal.PutAddressResponse
import fit.asta.health.common.location.maps.modal.SearchResponse
import fit.asta.health.common.utils.ResponseState
import kotlinx.coroutines.flow.Flow

interface MapsRepo {

    suspend fun search(text: String): Flow<ResponseState<SearchResponse>>

    suspend fun getAddresses(uid: String): Flow<ResponseState<AddressesResponse>>

    suspend fun putAddress(myAddress: MyAddress): Flow<ResponseState<PutAddressResponse>>

    suspend fun deleteAddress(uid: String, id: String): Flow<ResponseState<DeleteAddressResponse>>

    suspend fun selectCurrent(cid: String, pid: String?)
}