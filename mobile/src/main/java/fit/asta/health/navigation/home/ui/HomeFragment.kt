package fit.asta.health.navigation.home.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.pager.ExperimentalPagerApi
import fit.asta.health.ActivityLauncher
import fit.asta.health.R
import fit.asta.health.home.homeFragments.*
import fit.asta.health.home.view.AutoSliding
import fit.asta.health.navigation.home.HomeViewObserver
import fit.asta.health.navigation.home.categories.adapter.listeners.OnCategoryClickListenerImpl
import fit.asta.health.navigation.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.ext.android.inject


@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by inject()
    private val homeView: HomeView by inject()
    private val launcher: ActivityLauncher by inject()

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.home_header_layout,container,false)
        view.findViewById<ComposeView>(R.id.compose_view).setContent {
            Box(modifier = Modifier
                .background(color = MaterialTheme.colors.background)
                .clip(RoundedCornerShape(16.dp))) {
                Column(Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .background(color = MaterialTheme.colors.background)
                    .verticalScroll(rememberScrollState())) {
                    NameAndMoodHomeScreenHeader()
                    Spacer(modifier = Modifier.height(24.dp))
                    WeatherCardImage()
                    Spacer(modifier = Modifier.height(24.dp))
                    AutoSliding()
                    MyToolsAndViewAll()
                    VerticalImageCards()
                    Testimonials()
                    Spacer(modifier = Modifier.height(24.dp))
                    RateUsCard()
                    Spacer(modifier = Modifier.height(24.dp))
                    ReferAndEarn()
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
        return view
    }

//    @Deprecated("Deprecated in Java")
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//        btnViewMore.setOnClickListener {
//
//            launcher.launchTestimonialsActivity(it.context)
//        }
//
//        homeView.setAdapterClickListener(
//            OnCategoryClickListenerImpl(
//                requireContext(),
//                homeViewModel
//            )
//        )
//
//        homeViewModel.observeHomeViewLiveData(
//            viewLifecycleOwner,
//            HomeViewObserver(homeView)
//        )
//
//        homeView.setUpViewPager(this)
//        homeView.registerAutoScroll(viewLifecycleOwner.lifecycleScope)
//
//        homeViewModel.loadBanners()
//        homeViewModel.loadCategories()
//        homeViewModel.loadTestimonials()
//
//        homeView.setupInAppReview(activity as Activity)
//    }
//
//    override fun onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment: Boolean) {
//        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment)
//        homeView.stopScrolling(isPrimaryNavigationFragment)
//    }
}