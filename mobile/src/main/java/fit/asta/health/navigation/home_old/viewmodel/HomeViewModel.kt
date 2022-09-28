package fit.asta.health.navigation.home_old.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.navigation.home_old.HomeAction
import fit.asta.health.navigation.home_old.HomeViewObserver
import fit.asta.health.navigation.home_old.banners.BannersRepo
import fit.asta.health.navigation.home_old.categories.CategoriesDataStoreImpl
import fit.asta.health.navigation.home_old.categories.CategoriesRepo
import fit.asta.health.navigation.home_old.categories.data.CategoryData
import fit.asta.health.testimonials.TestimonialsRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val bannersRepo: BannersRepo,
    private val categoriesRepo: CategoriesRepo,
    private val testimonialsRepo: TestimonialsRepo
) : ViewModel() {

    private val liveDataCategories = MutableLiveData<HomeAction>()
    private val dataStore = CategoriesDataStoreImpl()

    fun loadBanners() {
        viewModelScope.launch {
            bannersRepo.fetchBanners("course").catch { exception ->
                liveDataCategories.value = HomeAction.Error(exception)
            }
                .collect {
                    liveDataCategories.value = HomeAction.LoadBanners(it)
                }
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            categoriesRepo.fetchCategories("courses").catch { exception ->
                liveDataCategories.value = HomeAction.Error(exception)
            }
                .collect {
                    dataStore.updateList(it)
                    liveDataCategories.value = HomeAction.LoadCategories(it)
                }
        }
    }

    fun loadTestimonials() {
        viewModelScope.launch {
            testimonialsRepo.fetchTestimonialsList(4, 0)
                .catch { exception ->
                liveDataCategories.value = HomeAction.Error(exception) }
                .collect {
                    liveDataCategories.value = HomeAction.LoadTestimonials(it)
                }
        }
    }

    fun observeHomeViewLiveData(lifecycleOwner: LifecycleOwner, observer: HomeViewObserver) {
        liveDataCategories.observe(lifecycleOwner, observer)
    }

    fun getCategory(position: Int): CategoryData {
        return dataStore.getCategory(position)
    }
}