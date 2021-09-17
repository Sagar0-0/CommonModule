package fit.asta.health.navigation.home.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import fit.asta.health.navigation.home.banners.data.BannerData
import fit.asta.health.navigation.home.categories.adapter.listeners.OnCategoryClickListener
import fit.asta.health.navigation.home.categories.data.CategoryData
import fit.asta.health.testimonials.data.TestimonialData

interface HomeView {

    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?

    fun setAdapterClickListener(listener: OnCategoryClickListener)
    fun stopScrolling(isPrimaryNavigationFragment: Boolean)
    fun changeState(state: State)

    fun setUpViewPager(fragment: Fragment)
    fun registerAutoScroll(lifecycleScope: LifecycleCoroutineScope)
    fun setupInAppReview(activity: Activity)

    sealed class State {
        class ShowBanners(val listBanners: List<BannerData>) : State()
        class ShowCategories(val listCategories: List<CategoryData>) : State()
        class ShowTestimonials(val listTestimonials: List<TestimonialData>) : State()
        object Empty : State()
        class Error(val error: Throwable) : State()
    }
}