package com.example.subscription.repo

import com.example.common.utils.ResponseState
import com.example.common.utils.getResponseState
import com.example.subscription.model.SubscriptionResponse
import com.example.subscription.remote.SubscriptionApi
import javax.inject.Inject

class SubscriptionRepoImpl
@Inject constructor(
    private val remoteApi: SubscriptionApi
) : SubscriptionRepo {

    override suspend fun getData(
        uid: String,
        country: String,
        date: String
    ): ResponseState<SubscriptionResponse> {
        return getResponseState { remoteApi.getData(uid, country, date) }
    }

}