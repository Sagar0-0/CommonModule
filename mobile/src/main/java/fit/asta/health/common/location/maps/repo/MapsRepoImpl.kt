package fit.asta.health.common.location.maps.repo

import android.content.Context
import fit.asta.health.R
import fit.asta.health.common.location.maps.api.RemoteApi
import fit.asta.health.common.location.maps.api.SearchApi
import fit.asta.health.common.location.maps.modal.AddressesResponse
import fit.asta.health.common.location.maps.modal.DeleteAddressResponse
import fit.asta.health.common.location.maps.modal.PutAddressResponse
import fit.asta.health.common.location.maps.modal.SearchResponse
import fit.asta.health.common.utils.ResultState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class MapsRepoImpl @Inject constructor(
    private val remoteApi: RemoteApi,
    private val searchApi: SearchApi,
    private val context: Context
) : MapsRepo {

    override suspend fun search(text: String): Flow<ResultState<SearchResponse>> = callbackFlow {
        trySend(ResultState.Loading)
        trySend(
            try {
                ResultState.Success(
                    searchApi.search(
                        text,
                        context.getString(R.string.MAPS_API_KEY)
                    )
                )
            } catch (e: Exception) {
                ResultState.Failure(
                    e
                )
            }

        )
        awaitClose {
            close()
        }
    }

    override suspend fun getAddresses(uid: String): Flow<ResultState<AddressesResponse>> =
        callbackFlow {
            trySend(ResultState.Loading)
            trySend(
                try {
                    ResultState.Success(
                        remoteApi.getAddresses(uid)
                    )
                } catch (e: Exception) {
                    if (e.message.equals("HTTP 404 ")) {
                        ResultState.Success(
                            AddressesResponse(
                                listOf(),
                                AddressesResponse.Status(200, "No Data found")
                            )
                        )
                    } else {
                        ResultState.Failure(e)
                    }
                }
            )
            awaitClose {
                close()
            }
        }

    override suspend fun putAddress(address: AddressesResponse.Address): Flow<ResultState<PutAddressResponse>> =
        callbackFlow {
            trySend(ResultState.Loading)
            trySend(
                try {
                    ResultState.Success(remoteApi.addNewAddress(address))
                } catch (e: Exception) {
                    ResultState.Failure(e)
                }
            )
            awaitClose {
                close()
            }
        }

    override suspend fun deleteAddress(
        uid: String,
        id: String
    ): Flow<ResultState<DeleteAddressResponse>> = callbackFlow {
        trySend(ResultState.Loading)
        trySend(
            try {
                ResultState.Success(remoteApi.deleteAddress(uid, id))
            } catch (e: Exception) {
                ResultState.Failure(e)
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