package fit.asta.health.subscription.listner

import androidx.viewpager2.widget.ViewPager2
import fit.asta.health.subscription.viewmodel.SubscriptionViewModel

class SubscriptionViewPagerListener(val viewModel: SubscriptionViewModel) :
    ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        viewModel.updateTab(position)
    }
}