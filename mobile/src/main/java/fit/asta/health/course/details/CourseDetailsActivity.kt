package fit.asta.health.course.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fit.asta.health.ActivityLauncher
import fit.asta.health.R
import fit.asta.health.course.details.ui.CourseDetailsObserver
import fit.asta.health.course.details.viewmodel.CourseDetailsViewModel
import fit.asta.health.course.listing.data.CourseIndexData
import fit.asta.health.utils.showDrawableImage
import kotlinx.android.synthetic.main.course_activity.*
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent


class CourseDetailsActivity : AppCompatActivity(), KoinComponent {

    private val courseDetailsView: CourseDetailsView by inject()
    private val viewModel: CourseDetailsViewModel by inject()
    private val launcher: ActivityLauncher by inject()
    private var courseId: String = ""

    companion object {
        private const val COURSE_INFO = "CourseInfo"

        fun launch(context: Context, data: CourseIndexData) {

            val intent = Intent(context, CourseDetailsActivity::class.java)
            intent.apply {
                putExtra(COURSE_INFO, data)
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(courseDetailsView.setContentView(this, courseViewPager))
        courseDetailsView.setUpViewPager(supportFragmentManager)
        viewModel.observeCourseDetailsLiveData(this, CourseDetailsObserver(courseDetailsView))

        val courseInfo = intent.getParcelableExtra<CourseIndexData>(COURSE_INFO)
        courseInfo?.let {
            viewModel.getCourseDetailsData(courseInfo.uid)
        }

        this.showDrawableImage(R.drawable.shimmer, courseImage)

        setSupportActionBar(tlbCourse)
        tlbCourse?.setNavigationOnClickListener {

            onBackPressed()
        }

        btnSchedule.setOnClickListener {
            launcher.launchSchedulerActivity(it.context)
        }

        exercisePlay.setOnClickListener {
            //TODO: Need fix hard coded string
            launcher.launchVideoPlayerActivity(this, courseId, "")
        }
    }
}