package fit.asta.health.navigation.tools.ui.viewmodel

import com.google.android.play.core.review.ReviewInfo


data class RateUsState(
    val error: Throwable? = null,
    val reviewInfo: ReviewInfo? = null
)