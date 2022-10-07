package fit.asta.health.navigation.home.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.play.core.review.ReviewManager
import fit.asta.health.R
import fit.asta.health.common.carousel.CarouselViewPagerAdapter
import fit.asta.health.navigation.home.model.domain.ToolsHome
import fit.asta.health.navigation.home_old.banners.ui.BannerFragment
import fit.asta.health.navigation.home_old.categories.adapter.CategoriesAdapter
import fit.asta.health.navigation.home_old.categories.data.CategoryData
import fit.asta.health.navigation.home_old.testimonials.TestimonialFragment
import fit.asta.health.testimonials.data.TestimonialData
import fit.asta.health.utils.showToastMessage
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.home_rcv_library.view.*
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class HomeViewImpl : HomeView {

    private var rootView: View? = null
    private var reviewManager: ReviewManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.home_fragment, container, false)
        initializeView()
        return rootView
    }

    private fun initializeView() {
        setupCategoriesView()
    }

    override fun changeState(state: HomeView.State) {
        when (state) {
            is HomeView.State.ShowHomeData -> setBannerAdapter(state.toolsHome)
            is HomeView.State.Empty -> showEmpty()
            is HomeView.State.Error -> showError(state.error)
        }
    }

    private fun setBannerAdapter(toolsHome: ToolsHome) {

    }

    private fun setupCategoriesView() {
        rootView?.let {
            val adapter = CategoriesAdapter()
            it.homeGalleryRcView.layoutManager =
                GridLayoutManager(it.context, 2, GridLayoutManager.VERTICAL, false)
            it.homeGalleryRcView.adapter = adapter
        }
    }

    private fun setCategoriesAdapter(list: List<CategoryData>) {
        rootView?.let {
            (it.homeGalleryRcView.adapter as CategoriesAdapter).updateList(list)
        }
    }

    private fun setTestimonialsAdapter(list: List<TestimonialData>) {
        rootView?.let {
            (it.sliderTestimonials.adapter as CarouselViewPagerAdapter).setCarouselList(
                list.map { testimonial ->
                    TestimonialFragment.newInstance(testimonial)
                })

            TabLayoutMediator(it.tabTestimonials, it.sliderTestimonials) { _, _ -> }.attach()
        }
    }

    private fun showEmpty() {

    }

    // we can handle here or just create base view impl class handle errors in one place apply for all classes
    private fun showError(error: Throwable) {
        when (error) {
            is IOException -> {
            }
            is HttpException -> {

                when (error.code()) {
                    // not found
                    HttpURLConnection.HTTP_NOT_FOUND -> {
                    }

                    // access denied
                    HttpURLConnection.HTTP_FORBIDDEN -> {
                    }

                    // unavailable service
                    HttpURLConnection.HTTP_UNAVAILABLE -> {
                    }

                    // all the others will be treated as unknown error
                    else -> {

                    }
                }
            }

        }
    }

    private fun showRateApp(activity: Activity) {
        val request = reviewManager?.requestReviewFlow()
        request?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val flow = reviewManager?.launchReviewFlow(activity, task.result)
                flow?.addOnCompleteListener { _ ->
                    // Continue your application process
                }
            } else {
                activity.showToastMessage(task.exception?.message)
            }
        }
    }
}