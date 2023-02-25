package fit.asta.health.old.course.session.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import fit.asta.health.R
import fit.asta.health.old.course.session.adapter.listeners.OnExerciseClickListenerImpl
import fit.asta.health.old.course.session.listners.OnSessionClickListenerImpl
import fit.asta.health.old.course.session.viewmodel.SessionObserver
import fit.asta.health.old.course.session.viewmodel.SessionViewModel
import javax.inject.Inject


class SessionActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionView: SessionView
    private val viewModel: SessionViewModel by viewModels()

    companion object {

        private const val ARG_COURSE_ID = "courseId"
        private const val ARG_SESSION_ID = "sessionId"

        fun launch(context: Context, courseId: String, sessionId: String) {

            val intent = Intent(context, SessionActivity::class.java)
            intent.putExtra(ARG_COURSE_ID, courseId)
            intent.putExtra(ARG_SESSION_ID, sessionId)
            intent.apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(sessionView.setContentView(this))
        sessionView.setAdapterClickListener(OnExerciseClickListenerImpl(this, viewModel))
        sessionView.setSessionPlayClickListener(OnSessionClickListenerImpl(this, viewModel))
        viewModel.observeSessionLiveData(this, SessionObserver(sessionView))
        viewModel.fetchSession(
            "",
            intent.getStringExtra(ARG_COURSE_ID)!!,
            intent.getStringExtra(ARG_SESSION_ID)!!
        )

        findViewById<AppCompatImageView>(R.id.imgBack).setOnClickListener {
            onBackPressed()
        }
    }
}