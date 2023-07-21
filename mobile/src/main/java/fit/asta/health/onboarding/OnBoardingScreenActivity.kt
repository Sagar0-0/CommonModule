package fit.asta.health.onboarding

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.auth.AuthActivity
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.main.ui.MainActivity
import fit.asta.health.onboarding.ui.OnBoardingPager
import fit.asta.health.onboarding.vm.OnboardingViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class OnBoardingScreenActivity : AppCompatActivity() {

    private val onboardingViewModel: OnboardingViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    companion object {
        fun launch(context: Context) {
            Intent(context, OnBoardingScreenActivity::class.java)
                .apply {
                    context.startActivity(this)
                }
            (context as Activity).finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                OnBoardingPager(
                    state = onboardingViewModel.state.collectAsStateWithLifecycle().value,
                    onReload = onboardingViewModel::getData,
                    onFinish = {
                        PrefUtils.setOnboardingShownStatus(true, this)
                        if (!authViewModel.isAuthenticated()) {
                            AuthActivity.launch(this)
                        } else {
                            MainActivity.launch(this)
                        }
                    }
                )
            }
        }
    }

}