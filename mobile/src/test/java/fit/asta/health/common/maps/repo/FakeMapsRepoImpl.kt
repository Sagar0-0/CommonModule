package fit.asta.health.common.maps.repo

import com.google.android.gms.maps.model.LatLng
import fit.asta.health.common.maps.modal.AddressesResponse
import fit.asta.health.common.maps.modal.DeleteAddressResponse
import fit.asta.health.common.maps.modal.PutAddressResponse
import fit.asta.health.common.maps.modal.SearchResponse
import fit.asta.health.common.utils.ResponseState
import kotlinx.coroutines.flow.Flow

class FakeMapsRepoImpl : MapsRepo {

    override suspend fun search(text: String, latLng: LatLng): Flow<ResponseState<SearchResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAddresses(uid: String): ResponseState<AddressesResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun putAddress(myAddress: AddressesResponse.MyAddress): Flow<ResponseState<PutAddressResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAddress(
        uid: String,
        id: String
    ): Flow<ResponseState<DeleteAddressResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun selectCurrent(cid: String, pid: String?) {
        TODO("Not yet implemented")
    }
}