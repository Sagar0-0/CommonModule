package fit.asta.health.old_course.details.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class CourseDetailsPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var mFragmentList = listOf<Fragment>()

    fun setCourseDetailsList(fragmentList: List<Fragment>) {

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