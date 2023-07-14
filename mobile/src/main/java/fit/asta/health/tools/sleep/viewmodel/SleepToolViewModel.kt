package fit.asta.health.tools.sleep.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.tools.sleep.model.LocalRepo
import fit.asta.health.tools.sleep.model.SleepRepository
import javax.inject.Inject

@HiltViewModel
class SleepToolViewModel @Inject constructor(
    private val remoteRepository: SleepRepository,
    private val localRepo: LocalRepo
) : ViewModel()