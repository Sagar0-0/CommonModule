package com.example.subscription.repo

import com.example.common.utils.ResponseState
import com.example.subscription.model.SubscriptionResponse

interface SubscriptionRepo {
    suspend fun getData(
        uid: String,
        country: String,
        date: String
    ): ResponseState<SubscriptionResponse>
}
