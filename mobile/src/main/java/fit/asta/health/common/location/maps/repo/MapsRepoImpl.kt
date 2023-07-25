package fit.asta.health.common.location.maps.repo

import android.content.Context
import android.util.Log
import fit.asta.health.R
import fit.asta.health.common.location.maps.api.RemoteApi
import fit.asta.health.common.location.maps.api.SearchApi
import fit.asta.health.common.location.maps.modal.AddressesResponse
import fit.asta.health.common.location.maps.modal.DeleteAddressResponse
import fit.asta.health.common.location.maps.modal.PutAddressResponse
import fit.asta.health.common.location.maps.modal.SearchResponse
import fit.asta.health.common.utils.ResponseState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class MapsRepoImpl @Inject constructor(
    private val remoteApi: RemoteApi,
    private val searchApi: SearchApi,
    private val context: Context
) : MapsRepo {

    override suspend fun search(text: String): Flow<ResponseState<SearchResponse>> = callbackFlow {
        trySend(ResponseState.Loading)
        trySend(
            try {
                ResponseState.Success(
                    searchApi.search(
                        text,
                        context.getString(R.string.MAPS_API_KEY)
                    )
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

    override suspend fun getAddresses(uid: String): Flow<ResponseState<AddressesResponse>> =
        callbackFlow {
            trySend(ResponseState.Loading)
            trySend(
                try {
                    ResponseState.Success(
                        remoteApi.getAddresses(uid)
                    )
                } catch (e: Exception) {
                    if (e.message.equals("HTTP 404 ")) {
                        ResponseState.Success(
                            AddressesResponse(
                                listOf(),
                                AddressesResponse.Status(200, "No Data found")
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

    override suspend fun putAddress(myAddress: AddressesResponse.MyAddress): Flow<ResponseState<PutAddressResponse>> =
        callbackFlow {
            trySend(ResponseState.Loading)
            trySend(
                try {
                    ResponseState.Success(remoteApi.addNewAddress(myAddress))
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
                ResponseState.Success(remoteApi.deleteAddress(uid, id))
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
        remoteApi.selectCurrent(cid, pid)
    }
}