package fit.asta.health.di

import fit.asta.health.ActivityLauncher
import fit.asta.health.ActivityLauncherImpl
import fit.asta.health.BuildConfig
import fit.asta.health.auth.data.UserRepository
import fit.asta.health.common.multiselect.MultiSelectDataMapper
import fit.asta.health.common.multiselect.MultiSelectRepo
import fit.asta.health.common.multiselect.MultiSelectRepoImpl
import fit.asta.health.common.multiselect.ui.MultiSelectView
import fit.asta.health.common.multiselect.ui.MultiSelectViewImpl
import fit.asta.health.common.multiselect.viewmodel.MultiSelectViewModel
import fit.asta.health.course.details.CourseDetailsRepo
import fit.asta.health.course.details.CourseDetailsRepoImpl
import fit.asta.health.course.details.CourseDetailsView
import fit.asta.health.course.details.CourseDetailsViewImpl
import fit.asta.health.course.details.data.CourseDetailsDataMapper
import fit.asta.health.course.details.ui.CourseDetailsPagerView
import fit.asta.health.course.details.ui.CourseDetailsPagerViewImpl
import fit.asta.health.course.details.viewmodel.CourseDetailsViewModel
import fit.asta.health.course.listing.CourseListingRepo
import fit.asta.health.course.listing.CourseListingRepoImpl
import fit.asta.health.course.listing.data.CourseListingDataMapper
import fit.asta.health.course.listing.ui.CourseListingView
import fit.asta.health.course.listing.ui.CourseListingViewImpl
import fit.asta.health.course.listing.viewmodel.CourseListingViewModel
import fit.asta.health.course.session.SessionRepo
import fit.asta.health.course.session.SessionRepoImpl
import fit.asta.health.course.session.data.SessionDataMapper
import fit.asta.health.course.session.ui.SessionView
import fit.asta.health.course.session.ui.SessionViewImpl
import fit.asta.health.course.session.viewmodel.SessionViewModel
import fit.asta.health.navigation.home_old.banners.BannersRepo
import fit.asta.health.navigation.home_old.banners.BannersRepoImpl
import fit.asta.health.navigation.home_old.banners.data.BannersDataMapper
import fit.asta.health.navigation.home_old.categories.CategoriesRepo
import fit.asta.health.navigation.home_old.categories.CategoriesRepoImpl
import fit.asta.health.navigation.home_old.categories.data.CategoryDataMapper
import fit.asta.health.navigation.home_old.ui.HomeView
import fit.asta.health.navigation.home_old.ui.HomeViewImpl
import fit.asta.health.navigation.home_old.viewmodel.HomeViewModel
import fit.asta.health.navigation.today.TodayPlanRepo
import fit.asta.health.navigation.today.TodayPlanRepoImpl
import fit.asta.health.navigation.today.adapter.TodayBaseViewHolderFactory
import fit.asta.health.navigation.today.data.TodayDataMapper
import fit.asta.health.navigation.today.ui.TodayPlanView
import fit.asta.health.navigation.today.ui.TodayPlanViewImpl
import fit.asta.health.navigation.today.viewmodel.TodayPlanViewModel
import fit.asta.health.network.AstaNetwork
import fit.asta.health.network.Certificate
import fit.asta.health.network.TokenProvider
import fit.asta.health.network.interceptor.OfflineInterceptor
import fit.asta.health.network.interceptor.OnlineInterceptor
import fit.asta.health.profile_old.ProfileDataMapper
import fit.asta.health.profile_old.ProfileRepo
import fit.asta.health.profile_old.ProfileRepoImpl
import fit.asta.health.profile_old.adapter.ProfileViewHolderFactory
import fit.asta.health.profile_old.ui.ProfilePagerView
import fit.asta.health.profile_old.ui.ProfilePagerViewImpl
import fit.asta.health.profile_old.ui.ProfileView
import fit.asta.health.profile_old.ui.ProfileViewImpl
import fit.asta.health.profile_old.ui.contacts.ContactsFragmentView
import fit.asta.health.profile_old.ui.contacts.ContactsFragmentViewImpl
import fit.asta.health.profile_old.viewmodel.ProfileViewModel
import fit.asta.health.schedule.ScheduleRepo
import fit.asta.health.schedule.ScheduleRepoImpl
import fit.asta.health.schedule.data.ScheduleDataMapper
import fit.asta.health.schedule.tags.TagsRepo
import fit.asta.health.schedule.tags.TagsRepoImpl
import fit.asta.health.schedule.tags.data.TagDataMapper
import fit.asta.health.schedule.tags.ui.TagsView
import fit.asta.health.schedule.tags.ui.TagsViewImpl
import fit.asta.health.schedule.tags.viewmodel.TagsViewModel
import fit.asta.health.schedule.ui.ScheduleView
import fit.asta.health.schedule.ui.ScheduleViewImpl
import fit.asta.health.schedule.viewmodel.ScheduleViewModel
import fit.asta.health.subscription.SubscriptionPagerView
import fit.asta.health.subscription.SubscriptionPagerViewImpl
import fit.asta.health.subscription.SubscriptionRepo
import fit.asta.health.subscription.SubscriptionRepoImpl
import fit.asta.health.subscription.adapter.SubscriptionViewHolderFactory
import fit.asta.health.subscription.data.SubscriptionDataMapper
import fit.asta.health.subscription.ui.SubscriptionView
import fit.asta.health.subscription.ui.SubscriptionViewImpl
import fit.asta.health.subscription.viewmodel.SubscriptionViewModel
import fit.asta.health.testimonials.TestimonialsRepo
import fit.asta.health.testimonials.TestimonialsRepoImpl
import fit.asta.health.testimonials.data.TestimonialDataMapper
import fit.asta.health.testimonials.ui.TestimonialsView
import fit.asta.health.testimonials.ui.TestimonialsViewImpl
import fit.asta.health.testimonials.viewmodel.TestimonialsViewModel
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val appModule = module {
    single(named("cache")) {
        Cache(androidApplication().filesDir, AstaNetwork.CACHE_SIZE)
    }

    single { TokenProvider() }

    single(named("local")) {

        val builder = AstaNetwork.Builder()
            .setCache(cache = get(named("cache")))
            .addInterceptor(OfflineInterceptor(androidApplication()))
            .addCertificatePinner(Certificate())

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.HEADERS)
            )
        }

        builder.build()
    }

    single(named("remote")) {

        val builder = AstaNetwork.Builder()
            .setApiKey(get())
            .setCache(cache = get(named("cache")))
            .addInterceptor(OnlineInterceptor(androidApplication()))

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)

            )
        }
        builder.build()
    }
}

val homeModule = module {

    factory { BannersDataMapper() }
    factory<BannersRepo> { BannersRepoImpl(get(named("remote")), get()) }
    factory<HomeView> { HomeViewImpl() }
    viewModel {
        HomeViewModel(
            get(),
            get(),
            get()
        )
    }
}

val categoryModule = module {
    factory { CategoryDataMapper() }
    factory<CategoriesRepo> {
        CategoriesRepoImpl(
            get(named("remote")),
            get()
        )
    }
}

val profileModule = module {
    factory<ProfilePagerView> { ProfilePagerViewImpl() }
    factory { ProfileDataMapper() }
    factory { UserRepository() }
    factory<ProfileRepo> { ProfileRepoImpl(get(named("remote")), get()) }
    viewModel { ProfileViewModel(get(), get()) }
    factory<ProfileView> { ProfileViewImpl() }
    factory<ContactsFragmentView> { ContactsFragmentViewImpl() }
    factory { ProfileViewHolderFactory() }
}

val courseModule = module {
    factory<CourseListingView> { CourseListingViewImpl() }
    factory { CourseListingDataMapper() }
    factory<CourseListingRepo> { CourseListingRepoImpl(get(named("remote")), get()) }
    viewModel { CourseListingViewModel(get()) }
    factory<ActivityLauncher> { ActivityLauncherImpl() }
}

val courseDetailsModule = module {
    factory<CourseDetailsPagerView> { CourseDetailsPagerViewImpl() }
    factory { CourseDetailsDataMapper() }
    factory<CourseDetailsRepo> { CourseDetailsRepoImpl(get(named("remote")), get()) }
    viewModel { CourseDetailsViewModel(get()) }
    factory<CourseDetailsView> { CourseDetailsViewImpl() }
}

val todayModule = module {
    factory<TodayPlanView> { TodayPlanViewImpl() }
    factory { TodayDataMapper() }
    factory<TodayPlanRepo> { TodayPlanRepoImpl(get(named("remote")), get()) }
    viewModel { TodayPlanViewModel(get()) }
    factory { TodayBaseViewHolderFactory() }
}

val exerciseModule = module {
    factory<SessionView> { SessionViewImpl() }
    factory { SessionDataMapper() }
    factory<SessionRepo> { SessionRepoImpl(get(named("remote")), get()) }
    viewModel { SessionViewModel(get()) }
}

val tagsModule = module {
    factory<TagsView> { TagsViewImpl() }
    factory { TagDataMapper() }
    factory<TagsRepo> { TagsRepoImpl(get(named("remote")), get()) }
    viewModel { TagsViewModel(get(), get()) }
}

val testimonialsModule = module {
    factory<TestimonialsView> { TestimonialsViewImpl() }
    factory { TestimonialDataMapper() }
    factory<TestimonialsRepo> { TestimonialsRepoImpl(get(named("remote")), get()) }
    viewModel { TestimonialsViewModel(get(), get()) }
}

val subscriptionModule = module {
    factory<SubscriptionPagerView> { SubscriptionPagerViewImpl() }
    factory { SubscriptionDataMapper() }
    factory<SubscriptionRepo> { SubscriptionRepoImpl(get(named("remote")), get()) }
    viewModel { SubscriptionViewModel(get()) }
    factory<SubscriptionView> { SubscriptionViewImpl() }
    factory { SubscriptionViewHolderFactory() }
}

val multiSelectionModule = module {
    factory<MultiSelectView> { MultiSelectViewImpl() }
    factory { MultiSelectDataMapper() }
    factory<MultiSelectRepo> { MultiSelectRepoImpl(get(named("remote")), get()) }
    viewModel { MultiSelectViewModel(get()) }
}

val scheduleModule = module {
    factory<ScheduleRepo> { ScheduleRepoImpl(get(named("remote")), get()) }
    factory { ScheduleDataMapper() }
    viewModel { ScheduleViewModel(get()) }
    factory<ScheduleView> { ScheduleViewImpl() }
}
