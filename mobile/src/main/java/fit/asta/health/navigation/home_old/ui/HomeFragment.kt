package fit.asta.health.navigation.home_old.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import fit.asta.health.ActivityLauncher
import fit.asta.health.R
import fit.asta.health.navigation.home_old.HomeViewObserver
import fit.asta.health.navigation.home_old.categories.adapter.listeners.OnCategoryClickListenerImpl
import fit.asta.health.navigation.home_old.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.ext.android.inject


@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by inject()
    private val homeView: HomeView by inject()
    private val launcher: ActivityLauncher by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.home_header_layout, container, false)
        view.findViewById<ComposeView>(R.id.compose_view).setContent {

        }
        return view
    }

    @Deprecated("Deprecated in Java")
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