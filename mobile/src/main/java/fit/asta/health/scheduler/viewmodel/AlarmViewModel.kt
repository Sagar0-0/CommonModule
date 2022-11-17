package fit.asta.health.scheduler.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.scheduler.model.db.AlarmRepository
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.db.entity.TagEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val repository: AlarmRepository,
    application: Application
) : AndroidViewModel(application) {

    var allAlarms: LiveData<List<AlarmEntity>> = repository.local.getAllAlarm().asLiveData()

    fun insertAlarm(alarmEntity: AlarmEntity) = viewModelScope.launch {
        repository.local.insertAlarm(alarmEntity)
    }

    fun updateAlarm(alarmEntity: AlarmEntity) = viewModelScope.launch {
        repository.local.updateAlarm(alarmEntity)
    }

    fun deleteAlarm(alarmEntity: AlarmEntity) = viewModelScope.launch {
        repository.local.deleteAlarm(alarmEntity)
    }

    fun deleteAllAlarms() = viewModelScope.launch {
        repository.local.deleteAllAlarm()
    }

    var allTags: LiveData<List<TagEntity>> = repository.local.getAllTags().asLiveData()

    fun insertTag(tagEntity: TagEntity) = viewModelScope.launch {
        repository.local.insertTag(tagEntity)
    }

    fun updateTag(tagEntity: TagEntity) = viewModelScope.launch {
        repository.local.updateTag(tagEntity)
    }

    fun deleteTag(tagEntity: TagEntity) = viewModelScope.launch {
        repository.local.deleteTag(tagEntity)
    }

    fun deleteAllTags() = viewModelScope.launch {
        repository.local.deleteAllTag()
    }
}