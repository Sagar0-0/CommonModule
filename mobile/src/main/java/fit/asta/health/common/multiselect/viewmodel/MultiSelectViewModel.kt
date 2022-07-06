package fit.asta.health.common.multiselect.viewmodel

import androidx.lifecycle.*
import fit.asta.health.common.multiselect.MultiSelectRepo
import fit.asta.health.common.multiselect.MultiSelectionAction
import fit.asta.health.common.multiselect.data.FinalSelectedData
import fit.asta.health.common.multiselect.data.MultiSelectData
import fit.asta.health.profile.adapter.ChipCardItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MultiSelectViewModel(private val selectRepo: MultiSelectRepo): ViewModel() {

    private val liveData = MutableLiveData<MultiSelectionAction>()
    private var multiSelectData: List<MultiSelectData> = listOf()

    fun getMultiSelectData(chipCardItem: ChipCardItem?) {
        chipCardItem?.let {

            viewModelScope.launch {
                selectRepo.fetchSelectionData(chipCardItem.id).collect {
                    multiSelectData = it
                    liveData.value = MultiSelectionAction.LoadSelections(getList(chipCardItem))
                }
            }
        }
    }

    fun getList(chipCardItem: ChipCardItem): List<MultiSelectData>{

        if (chipCardItem.value.size == 0) {
            multiSelectData
        } else {
            multiSelectData.forEach { data ->
               chipCardItem.value.forEach {
                   if(it.uid == data.id){
                     data.isSelected = true
                   }
               }
           }
        }
        return multiSelectData
    }

    fun setLiveDataObservable(owner: LifecycleOwner, observer: Observer<MultiSelectionAction> ){
        liveData.observe(owner, observer)
    }

    fun  getSelectedData(chipCardItem: ChipCardItem?): FinalSelectedData {
        val finalData = FinalSelectedData()
        val arrayList = ArrayList<MultiSelectData>()
        chipCardItem?.let {
            finalData.cardUid = it.id
            finalData.tabType = it.profileTabType
        }
        arrayList.addAll(multiSelectData.filter { it .isSelected })
        finalData.data = arrayList
        return finalData
    }

    fun updateData(uid: String, updateValue: Boolean) {
        multiSelectData.find { it.id == uid }?.isSelected = updateValue
    }
}