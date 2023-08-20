package com.example.subscription.data.repo

import com.example.common.utils.ResponseState
import com.example.subscription.data.model.SubscriptionResponse

interface SubscriptionRepo {
    suspend fun getData(
        uid: String,
        country: String,
        date: String
    ): ResponseState<SubscriptionResponse>
}
