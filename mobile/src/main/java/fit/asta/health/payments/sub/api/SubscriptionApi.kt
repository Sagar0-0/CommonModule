package fit.asta.health.payments.sub.api

import fit.asta.health.payments.sub.model.SubscriptionResponse

interface SubscriptionApi {

    suspend fun getData(uid: String, country: String, date: String): SubscriptionResponse
}