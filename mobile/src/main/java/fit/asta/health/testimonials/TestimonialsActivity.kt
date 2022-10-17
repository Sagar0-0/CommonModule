package fit.asta.health.testimonials

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.databinding.ActivityProfileNewBinding
import fit.asta.health.testimonials.viewmodel.TestimonialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TestimonialsActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileNewBinding
    private val viewModel: TestimonialViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileNewBinding.inflate(layoutInflater)
        binding.profileComposeView.setContent {

            val testimonialState = viewModel.state.collectAsState().value
        }

        setContentView(binding.root)
    }
}