package fit.asta.health.common.location.maps.repo

import fit.asta.health.common.location.maps.modal.AddressesResponse
import fit.asta.health.common.location.maps.modal.AddressesResponse.Address
import fit.asta.health.common.location.maps.modal.DeleteAddressResponse
import fit.asta.health.common.location.maps.modal.PutAddressResponse
import fit.asta.health.common.location.maps.modal.SearchResponse
import fit.asta.health.common.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface MapsRepo {

    suspend fun search(text: String): Flow<ResultState<SearchResponse>>

    suspend fun getAddresses(uid: String): Flow<ResultState<AddressesResponse>>

    suspend fun putAddress(address: Address): Flow<ResultState<PutAddressResponse>>

    suspend fun deleteAddress(uid: String, id: String): Flow<ResultState<DeleteAddressResponse>>

    suspend fun selectCurrent(cid: String, pid: String?)
}