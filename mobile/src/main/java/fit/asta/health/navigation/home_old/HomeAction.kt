package fit.asta.health.navigation.home_old

import fit.asta.health.navigation.home_old.banners.data.BannerData
import fit.asta.health.navigation.home_old.categories.data.CategoryData
import fit.asta.health.old_testimonials.data.TestimonialData

sealed class HomeAction {

    class LoadBanners(val listBanners: List<BannerData>) : HomeAction()
    class LoadCategories(val listCategories: List<CategoryData>) : HomeAction()
    class LoadTestimonials(val listTestimonials: List<TestimonialData>) : HomeAction()
    object Empty : HomeAction()
    class Error(val error: Throwable) : HomeAction()
}