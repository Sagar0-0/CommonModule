package fit.asta.health.old_scheduler.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.old_scheduler.ScheduleRepo
import fit.asta.health.old_scheduler.data.ScheduleData
import fit.asta.health.old_scheduler.data.ScheduleSessionData
import fit.asta.health.old_scheduler.data.ScheduleTimeData
import fit.asta.health.old_scheduler.tags.data.ScheduleTagData
import fit.asta.health.old_scheduler.ui.ScheduleAction
import fit.asta.health.old_scheduler.ui.ScheduleObserver
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ScheduleViewModel(private val scheduleRepo: ScheduleRepo) : ViewModel() {

    var lst = MutableLiveData<ArrayList<ScheduleSessionData>>()
    var newlist = arrayListOf<ScheduleSessionData>()

    private val scheduleLiveData = MutableLiveData<ScheduleAction>()
    private var scheduleData: ScheduleData? = null

    fun schedulePlan() {
        viewModelScope.launch {
            scheduleRepo.schedulePlan("", scheduleData!!)
                .catch {
                    Log.i("POST ERROR", "Something wrong")
                }.collect {
                    Log.i("POST DATA", it)
                }
        }
    }

    fun getScheduleData() {
        viewModelScope.launch {
            scheduleRepo.fetchScheduledData("", "")
                .collect {
                    scheduleData = it
                }
        }
    }

    fun reSchedulePlan() {
        viewModelScope.launch {
            scheduleRepo.reschedulePlan("", scheduleData!!)
                .catch {
                    Log.i("POST ERROR", "Something wrong")
                }.collect {
                    Log.i("POST DATA", it)
                }
        }
    }

    private fun updateCourseDetails(scheduleData: ScheduleData) {
        scheduleLiveData.value = ScheduleAction.LoadSchedule(scheduleData)
    }

    fun observerScheduleLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: ScheduleObserver
    ) {
        scheduleLiveData.observe(lifecycleOwner, observer)
    }

    @Suppress("UNUSED_PARAMETER")
    fun addTime(time: ScheduleTimeData) {

    }

    fun add(blog: ScheduleSessionData) {
        if (newlist.contains(blog)) {
        } else {
            newlist.add(blog)
            lst.value = newlist
        }
    }

    fun remove(blog: ScheduleSessionData) {
        newlist.remove(blog)
        lst.value = newlist
    }

    fun submitScheduledPlan() {
        schedulePlan()
    }

    fun getTagId(): String? {
        return scheduleData?.tagId
    }

    fun updateTag(selectedData: ScheduleTagData?) {
        scheduleLiveData.value = ScheduleAction.LoadTag(selectedData)
    }
}
