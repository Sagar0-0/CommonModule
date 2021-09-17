package fit.asta.health.common.carousel

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CarouselViewPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    private var carouselList = listOf<Fragment>()

    companion object {
        const val REFRESH_RATE = 4000L
    }

    fun setCarouselList(listCarousel: List<Fragment>) {

        carouselList = listCarousel
        notifyDataSetChanged()
    }

    override fun getItemCount() = carouselList.size

    override fun createFragment(position: Int): Fragment {
        //val carouselData = carouselList[position % carouselList.size]
        return carouselList[position]
    }
}