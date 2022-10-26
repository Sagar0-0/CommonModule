package fit.asta.health.old_course.listing.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fit.asta.health.navigation.home_old.categories.data.CategoryData
import fit.asta.health.old_course.listing.adapter.listeners.OnCourseClickListenerImpl
import fit.asta.health.old_course.listing.viewmodel.CourseListingObserver
import fit.asta.health.old_course.listing.viewmodel.CourseListingViewModel
import kotlinx.android.synthetic.main.course_list_activity.*
import org.koin.android.ext.android.inject


class CourseListingActivity : AppCompatActivity() {

    private val courseListingView: CourseListingView by inject()
    private val viewModel: CourseListingViewModel by inject()

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
        tlbCourseList.title = catTitle

        categoryId?.let { viewModel.fetchCourses(it, 10, 0) }

        tlbCourseList.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}