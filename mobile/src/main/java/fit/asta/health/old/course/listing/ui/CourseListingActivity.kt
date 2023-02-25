package fit.asta.health.old.course.listing.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import fit.asta.health.R
import fit.asta.health.old.course.listing.adapter.listeners.OnCourseClickListenerImpl
import fit.asta.health.old.course.listing.data.CategoryData
import fit.asta.health.old.course.listing.viewmodel.CourseListingObserver
import fit.asta.health.old.course.listing.viewmodel.CourseListingViewModel
import javax.inject.Inject


class CourseListingActivity : AppCompatActivity() {

    @Inject
    lateinit var courseListingView: CourseListingView
    private val viewModel: CourseListingViewModel by viewModels()

    companion object {

        const val CATEGORY_ID = "CourseCategoryId"
        const val CAT_TITLE = "CategoryTitle"

        fun launch(context: Context, data: CategoryData) {

            val intent = Intent(context, CourseListingActivity::class.java)
            intent.apply {
                putExtra(CAT_TITLE, data.title)
                putExtra(CATEGORY_ID, data.uid)
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(courseListingView.setContentView(this))
        courseListingView.setAdapterClickListener(OnCourseClickListenerImpl(this, viewModel))
        viewModel.observeCourseListingLiveData(this, CourseListingObserver(courseListingView))
        val categoryId = intent.getStringExtra(CATEGORY_ID)
        val catTitle = intent.getStringExtra(CAT_TITLE)
        val tlbCourseList = findViewById<MaterialToolbar>(R.id.tlbCourseList)
        tlbCourseList.title = catTitle

        categoryId?.let { viewModel.fetchCourses(it, 0, 10) }

        tlbCourseList.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}