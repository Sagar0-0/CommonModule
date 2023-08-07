package fit.asta.health.common.maps.repo

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import fit.asta.health.R
import fit.asta.health.common.maps.api.remote.MapsApi
import fit.asta.health.common.maps.api.search.SearchApi
import fit.asta.health.common.maps.modal.AddressesResponse
import fit.asta.health.common.maps.modal.DeleteAddressResponse
import fit.asta.health.common.maps.modal.PutAddressResponse
import fit.asta.health.common.maps.modal.SearchResponse
import fit.asta.health.common.utils.ResourcesProvider
import fit.asta.health.common.utils.ResponseState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.HttpException
import javax.inject.Inject

class MapsRepoImpl @Inject constructor(
    private val mapsApi: MapsApi,
    private val searchApi: SearchApi,
    private val resourcesProvider: ResourcesProvider
) : MapsRepo {

    override suspend fun search(text: String, latLng: LatLng): Flow<ResponseState<SearchResponse>> =
        callbackFlow {
            trySend(ResponseState.Loading)
            trySend(
                try {
                    ResponseState.Success(
                        if (latLng.latitude == 0.00 && latLng.longitude == 0.00) {
                            searchApi.search(
                                text,
                                resourcesProvider.getString(R.string.MAPS_API_KEY)
                            )
                        } else {
                            searchApi.searchBiased(
                                "${latLng.latitude},${latLng.longitude}",
                                "distance",
                                text,
                                resourcesProvider.getString(R.string.MAPS_API_KEY)
                            )
                        }
                    )
            } catch (e: Exception) {
                ResponseState.Error(
                    e
                )
            }

        )
        awaitClose {
            close()
        }
    }

    override suspend fun getAddresses(uid: String): ResponseState<AddressesResponse> {
        return try {
            ResponseState.Success(
                mapsApi.getAddresses(uid)
            )
        } catch (e: HttpException) {
            ResponseState.Success(
                AddressesResponse(
                    listOf(),
                    AddressesResponse.Status(200, "No Data found")
                )
            )
        } catch (e: Exception) {
            ResponseState.Error(e)
        }
    }

    override suspend fun putAddress(myAddress: AddressesResponse.MyAddress): Flow<ResponseState<PutAddressResponse>> =
        callbackFlow {
            trySend(ResponseState.Loading)
            trySend(
                try {
                    ResponseState.Success(mapsApi.addNewAddress(myAddress))
                } catch (e: Exception) {
                    ResponseState.Error(e)
                }
            )
            awaitClose {
                close()
            }
        }

    override suspend fun deleteAddress(
        uid: String,
        id: String
    ): Flow<ResponseState<DeleteAddressResponse>> = callbackFlow {
        trySend(ResponseState.Loading)
        trySend(
            try {
                ResponseState.Success(mapsApi.deleteAddress(uid, id))
            } catch (e: Exception) {
                Log.e("MAPS", "REPO deleteAddress: ${e.message}")
                if (e.message.equals("HTTP 404 ")) {
                    ResponseState.Success(
                        DeleteAddressResponse(
                            true,
                            DeleteAddressResponse.Status(200, "No Data found")
                        )
                    )
                } else {
                    ResponseState.Error(e)
                }
            }
        )
        awaitClose {
            close()
        }
    }

    override suspend fun selectCurrent(cid: String, pid: String?) {
        mapsApi.selectCurrent(cid, pid)
    }
}