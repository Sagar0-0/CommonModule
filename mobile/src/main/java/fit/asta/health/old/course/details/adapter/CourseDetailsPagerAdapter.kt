package fit.asta.health.old.course.details.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CourseDetailsPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var listPagerView = listOf<Fragment>()

    fun setFragmentList(profilePager: ArrayList<Fragment>) {
        listPagerView = profilePager
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listPagerView.size

    override fun createFragment(position: Int): Fragment {
        return listPagerView[position]
    }
}