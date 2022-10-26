package fit.asta.health.old_subscription.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SubscriptionPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var mFragmentList = listOf<Fragment>()

    fun setSubscriptionPlansList(fragmentList: List<Fragment>) {

        mFragmentList = fragmentList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }
}