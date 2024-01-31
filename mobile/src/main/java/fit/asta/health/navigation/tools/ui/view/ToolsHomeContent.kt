package fit.asta.health.navigation.tools.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fit.asta.health.BuildConfig
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.shareReferralCode
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppVerticalGrid
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.pager.AppExpandingDotIndicator
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.feedback.FEEDBACK_GRAPH_ROUTE
import fit.asta.health.feature.sleep.view.navigation.SLEEP_GRAPH_ROUTE
import fit.asta.health.feature.testimonials.components.UserTestimonialUI
import fit.asta.health.feature.walking.nav.STEPS_GRAPH_ROUTE
import fit.asta.health.home.remote.model.ToolsHome
import fit.asta.health.main.Graph
import fit.asta.health.navigation.tools.ui.view.component.FeedbackCard
import fit.asta.health.navigation.tools.ui.view.component.RateAppCard
import fit.asta.health.navigation.tools.ui.view.component.ToolsCardLayout
import fit.asta.health.navigation.tools.ui.view.component.ToolsHmScreenTopBanner
import fit.asta.health.navigation.tools.ui.view.component.ViewAllLayout
import fit.asta.health.offers.remote.model.OffersData
import fit.asta.health.referral.view.NewReferralDialogContent
import fit.asta.health.subscription.OffersLoadingCard
import fit.asta.health.subscription.SubscriptionLoadingCard
import fit.asta.health.subscription.remote.model.SubscriptionPlansResponse
import fit.asta.health.subscription.view.OffersBanner
import fit.asta.health.subscription.view.OffersUiData
import fit.asta.health.subscription.view.SubscriptionTypesPager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.Locale


@OptIn(ExperimentalFoundationApi::class)
@ExperimentalCoroutinesApi
@Composable
fun ToolsHomeContent(
    toolsHome: ToolsHome,
    subscriptionCategoryState: UiState<SubscriptionPlansResponse>,
    offersDataState: UiState<List<OffersData>>,
    refCode: String,
    onEvent: (ToolsHomeUiEvent) -> Unit,
    onNav: (String) -> Unit,
) {
    val context = LocalContext.current

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
    ) {
        item {
            when (offersDataState) {
                is UiState.Loading -> {
                    OffersLoadingCard(true)
                }

                is UiState.Success -> {
                    OffersBanner(
                        offersList = offersDataState.data.map {
                            OffersUiData(
                                categoryId = it.areas[0].productCategoryId.toString(),
                                productId = it.areas[0].productId.toString(),
                                type = it.type,
                                url = it.imageUrl
                            )
                        }
                    ) { categoryId, productId ->
                        onEvent(
                            ToolsHomeUiEvent.NavigateToFinalPayment(
                                categoryId = categoryId,
                                productId = productId
                            )
                        )
                    }
                }

                else -> {
                    OffersLoadingCard(isLoading = false) {
                        onEvent(ToolsHomeUiEvent.LoadOffersData)
                    }
                }
            }
        }

        // Top Sliding Images
        toolsHome.banners?.let { banners ->
            item {
                Column {
                    AppHorizontalPager(
                        pagerState = rememberPagerState { banners.size },
                        modifier = Modifier
                            .aspectRatio(ratio = AppTheme.aspectRatio.fullScreen)
                            .fillMaxWidth(),
                        enableAutoAnimation = true
                    ) { page ->
                        ToolsHmScreenTopBanner(bannerDataPages = banners[page])
                    }
                }
            }
        }

        when (subscriptionCategoryState) {
            is UiState.Loading -> {
                item {
                    SubscriptionLoadingCard(true)
                }
            }

            is UiState.Success -> {
                if (subscriptionCategoryState.data.userPlan == null) {
                    subscriptionCategoryState.data.plans?.let { plans ->
                        item {
                            SubscriptionTypesPager(
                                subscriptionPlans = plans
                            ) {
                                onEvent(ToolsHomeUiEvent.NavigateToSubscriptionDurations(it))
                            }
                        }
                    }
                }
//                    subscriptionCategoryState.data.userPlan?.let { plan ->
//                        UserSubscribedPlanSection(userSubscribedPlan = plan) { catId, prodId ->
//                            onEvent(ToolsHomeUiEvent.NavigateToFinalPayment(catId, prodId))
//                        }
//                    }

            }

            else -> {
                item {
                    SubscriptionLoadingCard(isLoading = false) {
                        onEvent(ToolsHomeUiEvent.LoadSubscriptionCategoryData)
                    }
                }
            }
        }

        toolsHome.tools?.let { tools ->
            // My Tools text and View All button
            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.spacing.level2)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TitleTexts.Level2(text = "My Tools")
                    AppIcon(
                        imageVector =
                        if (subscriptionCategoryState is UiState.Success) {
                            if (subscriptionCategoryState.data.userPlan != null) {
                                Icons.Default.LockOpen
                            } else {
                                Icons.Default.Lock
                            }
                        } else {
                            Icons.Default.Error
                        },
                        contentDescription = "Plans Locked/Unlocked"
                    )
                }
            }

            // All The Tools Composable cards
            item {
                val toolGridItem: @Composable (ToolsHome.HealthTool) -> Unit = { tool ->
                    Column {
                        ToolsCardLayout(
                            cardTitle = tool.title,
                            type = tool.name,
                            imgUrl = tool.url
                        ) { type ->
                            when (type.lowercase(Locale.getDefault())) {
                                "water" -> {
                                    onNav(Graph.WaterTool.route)
                                }

                                "steps" -> {
                                    onNav(STEPS_GRAPH_ROUTE)
                                }

                                "workout" -> {
                                    onNav(Graph.ExerciseTool.route + "?activity=workout")
                                }

                                "yoga" -> {
                                    onNav(Graph.ExerciseTool.route + "?activity=yoga")
                                }

                                "hiit" -> {
                                    onNav(Graph.ExerciseTool.route + "?activity=HIIT")
                                }

                                "dance" -> {
                                    onNav(Graph.ExerciseTool.route + "?activity=dance")
                                }

                                "meditation" -> {
                                    onNav(Graph.MeditationTool.route)
                                }

                                "sleep" -> {
                                    onNav(SLEEP_GRAPH_ROUTE)
                                }

                                "breathing" -> {
                                    onNav(Graph.BreathingTool.route)
                                }

                                "sunlight" -> {
                                    onNav(Graph.SunlightTool.route)
                                }
                            }
                        }
                    }
                }
                AppVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.spacing.level2)
                        .wrapContentHeight(),
                    count = 3,
                    items = tools.map {
                        {
                            toolGridItem(it)
                        }
                    }
                )
            }
        }

        toolsHome.testimonials?.let { testimonials ->
            // Testimonials Text and View All Button
            item {
                ViewAllLayout(
                    modifier = Modifier.padding(horizontal = AppTheme.spacing.level2),
                    title = "Testimonials",
                    clickString = "View All"
                ) { onNav(Graph.Testimonials.route) }
            }

            // Why our Customers text
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.spacing.level2),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TitleTexts.Level3(text = "Why our customers love ASTA?")
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                    AppDivider(modifier = Modifier.width(AppTheme.customSize.level9))
                }
            }

            // Testimonials Banners in a Horizontal Pager
            item {

                val pagerState = rememberPagerState { testimonials.size }

                Box {
                    AppHorizontalPager(
                        modifier = Modifier
                            .fillMaxWidth(),
                        pagerState = pagerState,
                    ) { page ->
                        AppCard(
                            modifier = Modifier
                                .padding(
                                    start = AppTheme.spacing.level2,
                                    end = AppTheme.spacing.level2,
                                    bottom = AppTheme.spacing.level2
                                )
                                .fillMaxSize()
                        ) {
                            UserTestimonialUI(
                                modifier = Modifier.padding(AppTheme.spacing.level2),
                                userTestimonial = testimonials[page].testimonial
                            )
                        }
                    }

                    // This function draws the Dot Indicator for the Pager
                    AppExpandingDotIndicator(
                        modifier = Modifier
                            .padding(bottom = AppTheme.spacing.level2)
                            .align(Alignment.BottomCenter),
                        pagerState = pagerState
                    )
                }
            }
        }

        // rate my app Card
        item {
            Column(modifier = Modifier.padding(horizontal = AppTheme.spacing.level2)) {
                RateAppCard()
            }
        }

        // Feedback Card
        item {
            Column(modifier = Modifier.padding(horizontal = AppTheme.spacing.level2)) {
                FeedbackCard {
                    onNav("$FEEDBACK_GRAPH_ROUTE/application")
                }
            }
        }

        // Refer and Earn Card
        item {
            NewReferralDialogContent(
                modifier = Modifier.padding(horizontal = AppTheme.spacing.level2),
                refCode = refCode,
                shareRefLink = {
                    context.shareReferralCode(it, BuildConfig.APPLICATION_ID)
                }
            )
        }

        item { Spacer(Modifier) }//To provide end padding
    }
}

sealed interface ToolsHomeUiEvent {
    data object LoadSubscriptionCategoryData : ToolsHomeUiEvent
    data object LoadOffersData : ToolsHomeUiEvent
    data object LoadToolsData : ToolsHomeUiEvent

    data class NavigateToSubscriptionDurations(val categoryId: String) : ToolsHomeUiEvent
    data class NavigateToFinalPayment(val categoryId: String, val productId: String) :
        ToolsHomeUiEvent
}
