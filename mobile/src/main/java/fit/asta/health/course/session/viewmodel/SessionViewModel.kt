package fit.asta.health.course.session.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.course.session.SessionRepo
import fit.asta.health.course.session.data.Exercise
import fit.asta.health.course.session.data.SessionData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SessionViewModel(private val sessionRepo: SessionRepo) : ViewModel() {

    private val sessionLiveData = MutableLiveData<SessionAction>()
    private val dataStore = SessionDataStoreImpl()

    fun fetchSession(userId: String, courseId: String, sessionId: String) {
        viewModelScope.launch {
            sessionRepo.fetchSession(userId, courseId, sessionId).collect {
                loadSession(it)
            }
        }
    }

    private fun loadSession(list: SessionData) {
        dataStore.updateSession(list)
        sessionLiveData.value = SessionAction.LoadSession(list)
    }

    fun observeSessionLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: SessionObserver
    ) {
        sessionLiveData.observe(lifecycleOwner, observer)
    }

    fun getExercise(position: Int): Exercise {
        return dataStore.getExercise(position)
    }

    fun getExercises(): List<Exercise> {
        return dataStore.getExercises()
    }
}