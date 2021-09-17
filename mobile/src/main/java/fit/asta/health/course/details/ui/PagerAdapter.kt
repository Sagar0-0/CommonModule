package fit.asta.health.course.details.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class PagerAdapter internal constructor(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm) {

    private val mFragmentList: MutableList<Fragment> = ArrayList()
    private val mFragmentTitleList: MutableList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {

        return mFragmentList[position]
    }

    override fun getCount(): Int {

        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return mFragmentTitleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {

        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
}