package fit.asta.health.feedback

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.databinding.ProfileActivityBinding
import fit.asta.health.feedback.viewmodel.FeedbackViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FeedbackActivity : AppCompatActivity() {

    lateinit var binding: ProfileActivityBinding
    private val viewModel: FeedbackViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileActivityBinding.inflate(layoutInflater)
        binding.profileComposeView.setContent {

            val feedbackState = viewModel.state.collectAsState().value
        }

        setContentView(binding.root)
    }
}