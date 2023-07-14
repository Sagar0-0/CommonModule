package fit.asta.health.feedback

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.common.ui.components.EndScreenPopup
import fit.asta.health.feedback.view.SessionFeedback
import fit.asta.health.feedback.viewmodel.FeedbackPostState
import fit.asta.health.feedback.viewmodel.FeedbackViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FeedbackActivity : AppCompatActivity() {

    private val viewModel: FeedbackViewModel by viewModels()

    companion object {
        private var fid: String = ""
        fun launch(context: Context, fid: String) {
            this.fid = fid
            Intent(context, FeedbackActivity::class.java)
                .apply {
                    context.startActivity(this)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadFeedbackQuestions(fid)
        setContent {
            AppTheme {
                val quesState = viewModel.feedbackQuestions.collectAsStateWithLifecycle()
                val postResultState = viewModel.feedbackPostState.collectAsStateWithLifecycle()
                when (postResultState.value) {
                    FeedbackPostState.Idle -> {
                        SessionFeedback(
                            feedbackQuesState = quesState.value,
                            onSubmit = viewModel::postUserFeedback
                        )
                    }
                    FeedbackPostState.Loading -> {
                        LinearProgressIndicator(Modifier.fillMaxWidth())
                    }

                    is FeedbackPostState.Success -> {
                        EndScreenPopup(
                            title = "Thank you!",
                            desc = "Your feedback has been submitted"
                        ) {
                            finish()
                        }
                    }

                    is FeedbackPostState.Error -> {
                        Toast.makeText(
                            this,
                            "ERROR MSG: " + (postResultState.value as FeedbackPostState.Error).error.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}