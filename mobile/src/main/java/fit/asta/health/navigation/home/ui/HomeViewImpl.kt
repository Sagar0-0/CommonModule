package fit.asta.health.navigation.home.ui

import android.app.Activity
import android.app.AuthenticationRequiredException
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import fit.asta.health.R
import fit.asta.health.common.carousel.CarouselViewPagerAdapter
import fit.asta.health.common.carousel.autoScroll
import fit.asta.health.navigation.home.banners.data.BannerData
import fit.asta.health.navigation.home.banners.ui.BannerFragment
import fit.asta.health.navigation.home.categories.adapter.CategoriesAdapter
import fit.asta.health.navigation.home.categories.adapter.listeners.OnCategoryClickListener
import fit.asta.health.navigation.home.categories.data.CategoryData
import fit.asta.health.navigation.home.testimonials.TestimonialFragment
import fit.asta.health.testimonials.data.TestimonialData
import fit.asta.health.utils.showToastMessage
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.home_rcv_library.view.*
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
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

    override fun stopScrolling(isPrimaryNavigationFragment: Boolean) {
        if (!isPrimaryNavigationFragment) {
            rootView?.let {
                it.homeGalleryRcView.stopScroll()
            }
        }
    }

    override fun changeState(state: HomeView.State) {
        when (state) {
            is HomeView.State.ShowBanners -> setBannerAdapter(state.listBanners)
            is HomeView.State.ShowCategories -> setCategoriesAdapter(state.listCategories)
            is HomeView.State.ShowTestimonials -> setTestimonialsAdapter(state.listTestimonials)
            is HomeView.State.Empty -> showEmpty()
            is HomeView.State.Error -> showError(state.error)
        }
    }

    private fun setBannerAdapter(list: List<BannerData>) {
        rootView?.let {
            (it.sliderBanners.adapter as CarouselViewPagerAdapter).setCarouselList(
                list.map { banner -> BannerFragment.newInstance(banner) }
            )
            TabLayoutMediator(it.tabCarousel, it.sliderBanners) { _, _ -> }.attach()
        }
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

    override fun setAdapterClickListener(listener: OnCategoryClickListener) {
        rootView?.let {
            (it.homeGalleryRcView.adapter as CategoriesAdapter).setAdapterClickListener(listener)
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


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun setUpViewPager(fragment: Fragment) {
        rootView?.let {

            it.sliderTestimonials.adapter = CarouselViewPagerAdapter(fragment)
            it.sliderBanners.adapter = CarouselViewPagerAdapter(fragment)
        }
    }

    override fun registerAutoScroll(lifecycleScope: LifecycleCoroutineScope) {

        rootView?.let {
            it.sliderTestimonials.autoScroll(
                lifecycleScope = lifecycleScope,
                interval = CarouselViewPagerAdapter.REFRESH_RATE
            )
            it.sliderBanners.autoScroll(
                lifecycleScope = lifecycleScope,
                interval = CarouselViewPagerAdapter.REFRESH_RATE
            )
        }
    }

    override fun setupInAppReview(activity: Activity) {
        rootView?.let {
            reviewManager = ReviewManagerFactory.create(it.context)
            it.includeRateUs.setOnClickListener {
                showRateApp(activity)
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