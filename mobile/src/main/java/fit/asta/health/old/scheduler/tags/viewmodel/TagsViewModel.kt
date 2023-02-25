package fit.asta.health.old.scheduler.tags.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.old.scheduler.tags.TagsRepo
import fit.asta.health.old.scheduler.tags.data.ScheduleTagData
import fit.asta.health.old.scheduler.tags.ui.TagsObserver
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TagsViewModel(
    private val tagsRepo: TagsRepo,
    private val userRepo: AuthRepo
) : ViewModel() {

    private val liveDataTags = MutableLiveData<TagsAction>()
    private val dataStore = TagsDataStoreImpl()

    fun submitTag() {
        viewModelScope.launch {
            userRepo.getUser()?.let { user ->
                val tag = dataStore.getMyTag(user.uid)
                if (tag.uid.isEmpty()) {
                    tagsRepo.createTag(tag)
                        .catch {
                            Log.i("POST ERROR", "Something wrong")
                        }.collect {

                        }
                } else {
                    tagsRepo.updateTag(tag)
                        .catch {
                            Log.i("PUT ERROR", "Something wrong")
                        }.collect {

                        }
                }

                liveDataTags.value = TagsAction.Empty
            }
        }
    }

    fun fetchTag(tagId: String) {
        viewModelScope.launch {
            userRepo.getUser()?.let { user ->
                tagsRepo.fetchTag(user.uid, tagId).collect {
                    dataStore.setMyTag(it)
                    liveDataTags.value = TagsAction.LoadTag(it)
                }
            }
        }
    }

    fun fetchTagList(selectedTagId: String?) {
        viewModelScope.launch {
            tagsRepo.fetchTagList()
                .collect {
                    updateTagList(selectedTagId, it)
                }
        }
    }

    private fun updateTagList(selectedTagId: String?, list: List<ScheduleTagData>) {

        list.find { it.uid == selectedTagId }?.isSelected = true
        dataStore.updateTagList(list)
        liveDataTags.value = TagsAction.LoadTagList(list)
    }

    fun observeTagLiveData(lifecycleOwner: LifecycleOwner, observer: TagsObserver) {
        liveDataTags.observe(lifecycleOwner, observer)
    }

    /*fun  getSelectedData(chipCardItem: ChipCardItem?): ScheduleTagData {
        val finalData = FinalSelectedData()
        val arrayList = ArrayList<MultiSelectData>()
        chipCardItem?.let {
            finalData.cardUid = it.id
            finalData.tabType = it.profileTabType
        }
        arrayList.addAll(multiSelectData.filter { it .isSelected })
        finalData.data = arrayList
        return finalData
    }*/

    fun updateData(tagId: String, updatedValue: Boolean) {

        dataStore.updateData(tagId, updatedValue)
    }

    @Suppress("UNUSED_PARAMETER")
    fun getSelectedData(tagId: String): ScheduleTagData {
        return ScheduleTagData()
    }
}