package fit.asta.health.navigation.today

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.navigation.today.viewmodel.TodayPlanViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
@Suppress("DEPRECATION")
class TodayFragment : Fragment() {

    private val todayPlanViewModel: TodayPlanViewModel by viewModels()

    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

            }
        }
    }
}