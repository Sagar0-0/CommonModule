package fit.asta.health.navigation.home.categories.adapter.listeners

import android.content.Context
import fit.asta.health.ActivityLauncher
import fit.asta.health.navigation.home.viewmodel.HomeViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class OnCategoryClickListenerImpl(
    private val context: Context,
    private val viewModel: HomeViewModel
) : OnCategoryClickListener, KoinComponent {

    private val launcher: ActivityLauncher by inject()

    override fun onCategoryClick(position: Int) {
        val categoryData = viewModel.getCategory(position)
        launcher.launchCourseListingActivity(context, categoryData)
    }
}