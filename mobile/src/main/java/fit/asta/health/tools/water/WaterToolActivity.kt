package fit.asta.health.tools.water

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.databinding.ActivityProfileNewBinding
import fit.asta.health.tools.water.view.waterToolForm
import fit.asta.health.tools.water.viewmodel.WaterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class WaterToolActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileNewBinding
    private val viewModel: WaterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileNewBinding.inflate(layoutInflater)
        binding.profileComposeView.setContent {
            waterToolForm(viewModel = viewModel)
        }

        setContentView(binding.root)
    }

}