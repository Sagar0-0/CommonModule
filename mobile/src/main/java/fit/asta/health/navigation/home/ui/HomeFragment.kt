package fit.asta.health.navigation.home.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import fit.asta.health.ActivityLauncher
import fit.asta.health.navigation.home.HomeViewObserver
import fit.asta.health.navigation.home.categories.adapter.listeners.OnCategoryClickListenerImpl
import fit.asta.health.navigation.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.ext.android.inject


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by inject()
    private val homeView: HomeView by inject()
    private val launcher: ActivityLauncher by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return homeView.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnViewMore.setOnClickListener {

            launcher.launchTestimonialsActivity(it.context)
        }

        homeView.setAdapterClickListener(
            OnCategoryClickListenerImpl(
                requireContext(),
                homeViewModel
            )
        )
        homeViewModel.observeHomeViewLiveData(
            viewLifecycleOwner,
            HomeViewObserver(homeView)
        )
        homeView.setUpViewPager(this)
        homeView.registerAutoScroll(viewLifecycleOwner.lifecycleScope)

        homeViewModel.loadBanners()
        homeViewModel.loadCategories()
        homeViewModel.loadTestimonials()

        homeView.setupInAppReview(activity as Activity)
    }

    override fun onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment: Boolean) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment)
        homeView.stopScrolling(isPrimaryNavigationFragment)
    }
}