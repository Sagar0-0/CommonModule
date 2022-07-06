package fit.asta.health.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.auth.data.UserRepository
import fit.asta.health.common.multiselect.data.FinalSelectedData
import fit.asta.health.profile.ProfileData
import fit.asta.health.profile.ProfileRepo
import fit.asta.health.profile.adapter.ChipCardItem
import fit.asta.health.profile.adapter.PlainCardItem
import fit.asta.health.profile.adapter.viewholders.ProfileTabType
import fit.asta.health.profile.adapter.viewholders.ProfileTabType.*
import fit.asta.health.profile.data.userprofile.Value
import fit.asta.health.profile.ui.ProfileAction
import fit.asta.health.profile.ui.ProfileObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ProfileViewModel(private val repo: ProfileRepo,
                       private val userRepo: UserRepository) : ViewModel() {

    private val mapProfileLiveData: MutableMap<String, MutableLiveData<ProfileAction>> =  mutableMapOf()
    private var profileData: ProfileData? = null

    fun getProfileData() {
        viewModelScope.launch {
            val userId = userRepo.user()?.uid ?: "1234"
            repo.fetchProfile(userId)
                .collect {
                    profileData = it
                }
          }
    }

    private fun updatePhysique() {
        profileData?.physique?.let {
            mapProfileLiveData[PhysiqueTab.name]?.value = ProfileAction.LoadProfileAction(it)
        }
    }

    private fun updateLiftStyle() {
        profileData?.lifestyle?.let {
            mapProfileLiveData[LifeStyleTab.name]?.value = ProfileAction.LoadProfileAction(it)
        }
    }

    private fun updateHealthTargets() {
        profileData?.health?.let {
            mapProfileLiveData[HealthTargetsTab.name]?.value = ProfileAction.LoadProfileAction(it)
        }
    }

    fun updateTabData(profileTabType: ProfileTabType) {
        when (profileTabType) {
            PhysiqueTab -> {
                updatePhysique()
            }
            LifeStyleTab -> {
                updateLiftStyle()
            }
            HealthTargetsTab -> {
                updateHealthTargets()
            }
            else -> {
                updatePhysique()
                updateLiftStyle()
                updateHealthTargets()
            }
        }
    }

    fun observeProfileLiveData(
        lifecycleOwner: LifecycleOwner, profileTabType: ProfileTabType,
        observer: ProfileObserver
    ) {
        mapProfileLiveData[profileTabType.name] = MutableLiveData()
        mapProfileLiveData[profileTabType.name]?.observe(lifecycleOwner, observer)
    }

    fun updateEditData(profileItem: PlainCardItem) {
        when(profileItem.profileTabType){
            LifeStyleTab -> {
                profileData?.lifestyle?.forEach {
                    if (it is PlainCardItem) {
                        if(profileItem.id == it.id) {
                            it.updatedValue = profileItem.updatedValue
                        }
                    }
                }
            }

            HealthTargetsTab -> {
                profileData?.health?.forEach {
                    if (it is PlainCardItem) {
                        if(profileItem.id == it.id) {
                            it.updatedValue = profileItem.updatedValue
                        }
                    }
                }
            }

            PhysiqueTab -> {
                profileData?.physique?.forEach {
                    if (it is PlainCardItem) {
                        if(profileItem.id == it.id) {
                            it.updatedValue = profileItem.updatedValue
                        }
                    }
                }
            }
            else -> {}
        }
    }

    @ExperimentalCoroutinesApi
    fun updateProfile(){
        viewModelScope.launch {
            val userId = userRepo.user()?.uid ?: "1234"
            repo.updateProfile(profileData, userId)
                .catch {
                Log.i("POST ERROR", "Something wrong")
            }.collect {
               Log.i("POST DATA", it)
            }
        }
    }

    fun updateChipsCount(selectData: FinalSelectedData?) {
        val list = arrayListOf<Value>()
        selectData?.data?.forEach {
            list.add(
                Value(
                    it.id,
                    it.displayName
                )
            )
        }
        when (selectData?.tabType) {
            HealthTargetsTab -> {
                profileData?.health?.forEach {
                    if (it is ChipCardItem) {
                        if (it.id == selectData.cardUid) {
                            it.value = list
                        }
                    }
                }
                updateHealthTargets()
            }
            LifeStyleTab -> {
                profileData?.lifestyle?.forEach {
                    if (it is ChipCardItem) {
                        if (it.id == selectData.cardUid) {
                            it.value = list
                        }
                    }
                }
                updateLiftStyle()
            }
            else -> {
            }
        }
    }

    fun deleteChipData(chipCardItem: ChipCardItem) {
        when (chipCardItem.profileTabType) {
            LifeStyleTab -> {
                profileData?.lifestyle?.forEach {
                    if(it is ChipCardItem){
                        if(it.id == chipCardItem.id){
                            it.value.removeAt(it.value.indexOfFirst {true})
                        }
                    }
                }
            }
            HealthTargetsTab -> {
                profileData?.health?.forEach {
                    if(it is ChipCardItem){
                        if(it.id == chipCardItem.id){
                            it.value.removeAt(it.value.indexOfFirst {true})
                        }
                    }
                }
            }
            else -> {}
        }
    }
}