package fit.asta.health.navigation.home.viewmodel

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.play.core.review.ReviewManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

private const val SESSION = "session"


@ExperimentalCoroutinesApi
@HiltViewModel
class RateUsViewModel
@Inject constructor(
    val reviewManager: ReviewManager,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    var state by mutableStateOf(RateUsState())
        private set

    fun onEvent(event: RateUsEvent) {
        when (event) {
            is RateUsEvent.InAppReviewRequested -> {
                onInAppReviewRequested()
            }
            is RateUsEvent.Error -> TODO()
            RateUsEvent.InAppReviewCompleted -> TODO()
            RateUsEvent.NoOp -> TODO()
            RateUsEvent.Session -> TODO()
        }
    }

    private fun onInAppReviewRequested() {
        reviewManager.requestReviewFlow().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                this.state = this.state.copy(reviewInfo = task.result)
            } else {
                this.state = this.state.copy(error = task.exception)
            }
        }
    }

    /*
    fun bind(events: Observable<RatingEvent>): Observable<ViewState> =
        bindEvents(events)
            .scanWith(initialState, reducer)
            .doOnError { println("error:" + it) }
            .onErrorReturn {
                ViewState().copy(error = it)
            }
            .distinctUntilChanged()

    private fun bindEvents(events: Observable<RatingEvent>): Observable<RatingEvent> {

        val obtainReviewInfo = Single.create<ReviewInfo> { emitter ->
            val session = sharedPreferences.getInt(SESSION, 1)
            if (session == 5 || session == 10 || session == 20) {
                val request = reviewManager.requestReviewFlow()
                request.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(task.result)
                    } else {
                        emitter.onError(Throwable(task.exception))
                    }

                }
            } else {
                emitter.onError(Throwable("no time to request in-app review"))
            }
        }.map<RatingEvent> {
            RatingEvent.InAppReviewRequested(it)
        }.onErrorReturn { error ->
            //If this throws an error, do you need to display it to a user? Not really.
            // We can just log an error.
            RatingEvent.NoOp
        }.toObservable()

        val viewIntents = events.publish {
            val inAppReviewCompleted = it.ofType(RatingEvent.InAppReviewCompleted::class.java)
                .flatMapCompletable {
                    //after receiving a signal that review flow ended, we proceed with regular flow
                    navigator.navigateTo(NavigatorPath.Feed)
                }.toObservable<RatingEvent>()
                .onErrorReturn {
                    RatingEvent.Error(it)
                }

            val saveSessionNumber = it.ofType(RatingEvent.Session::class.java)
                .flatMapCompletable {
                    Completable.fromAction {
                        val session = sharedPreferences.getInt(SESSION, 1)
                        sharedPreferences.edit().putInt(SESSION, (session + 1)).apply()
                    }
                }.toObservable<RatingEvent>()
                .onErrorReturn {
                    RatingEvent.Error(it)
                }

            Observable.merge(inAppReviewCompleted, saveSessionNumber)
        }

        return Observable.merge(obtainReviewInfo, viewIntents)
    }

    private val reducer =
        BiFunction<ViewState, RatingEvent, ViewState> { previous, intent ->
            when (intent) {
                is RatingEvent.InAppReviewRequested -> {
                    previous.copy(reviewInfo = intent.reviewInfo)
                }
                is RatingEvent.Error -> {
                    previous.copy(error = intent.throwable, reviewInfo = null)
                }
                else -> previous.copy(reviewInfo = null)
            }
        }*/
}