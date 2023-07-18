package fit.asta.health.profile.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.model.AuthRepo
import fit.asta.health.network.NetworkHelper
import fit.asta.health.profile.model.ProfileRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class CreateProfileViewModel
@Inject constructor(
    private val profileRepo: ProfileRepo,
    private val authRepo: AuthRepo,
    private val networkHelper: NetworkHelper,
    private val savedState: SavedStateHandle,
) : ViewModel()