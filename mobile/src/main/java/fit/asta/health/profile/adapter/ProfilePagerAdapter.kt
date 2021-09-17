package fit.asta.health.profile.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProfilePagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

     private var listPagerView = listOf<Fragment>()

    fun setProfilesList(profilePager: ArrayList<Fragment>) {
        listPagerView = profilePager
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = listPagerView.size

    override fun createFragment(position: Int): Fragment {
        return listPagerView[position]
    }
}