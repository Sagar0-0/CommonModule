package fit.asta.health.old_course.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import fit.asta.health.ActivityLauncher
import fit.asta.health.R
import fit.asta.health.old_course.details.ui.CourseDetailsObserver
import fit.asta.health.old_course.details.viewmodel.CourseDetailsViewModel
import fit.asta.health.old_course.listing.data.CourseIndexData
import fit.asta.health.utils.showDrawableImage
import javax.inject.Inject


class CourseDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var courseDetailsView: CourseDetailsView
    private val viewModel: CourseDetailsViewModel by viewModels()
    @Inject
    lateinit var launcher: ActivityLauncher
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

        setContentView(courseDetailsView.setContentView(this, findViewById(R.id.courseViewPager)))
        courseDetailsView.setUpViewPager(supportFragmentManager)
        viewModel.observeCourseDetailsLiveData(this, CourseDetailsObserver(courseDetailsView))

        val courseInfo = intent.getParcelableExtra<CourseIndexData>(COURSE_INFO)
        courseInfo?.let {
            viewModel.getCourseDetailsData(courseInfo.uid)
        }

        this.showDrawableImage(R.drawable.shimmer, findViewById(R.id.courseImage))

        val tlbCourse = findViewById<MaterialToolbar>(R.id.tlbCourse)
        setSupportActionBar(tlbCourse)
        tlbCourse?.setNavigationOnClickListener {

            onBackPressed()
        }

        findViewById<MaterialButton>(R.id.btnSchedule).setOnClickListener {
            launcher.launchSchedulerActivity(it.context)
        }

        findViewById<AppCompatImageView>(R.id.exercisePlay).setOnClickListener {
            //TODO: Need fix hard coded string
            launcher.launchVideoPlayerActivity(this, courseId, "")
        }
    }
}