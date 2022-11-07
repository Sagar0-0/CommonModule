package fit.asta.health.tools.walking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.tools.walking.model.WalkingToolRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class WalkingViewModel
@Inject constructor(
    private val walkingToolRepo: WalkingToolRepo,
) : ViewModel() {

    private val mutableState = MutableStateFlow<WalkingState>(WalkingState.Loading)
    val state = mutableState.asStateFlow()

    init {
        loadWalkingToolData()
    }

    private fun loadWalkingToolData() {
        viewModelScope.launch {
            walkingToolRepo.getWalkingTool(userId = "62fcd8c098eb9d5ed038b563").catch { exception ->
                mutableState.value = WalkingState.Error(exception)
            }.collect {
                mutableState.value = WalkingState.Success(it)
            }
        }
    }
}