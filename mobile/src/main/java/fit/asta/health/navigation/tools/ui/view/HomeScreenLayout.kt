package fit.asta.health.navigation.tools.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fit.asta.health.BuildConfig
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.shareReferralCode
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.pager.AppExpandingDotIndicator
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.designsystem.molecular.scrollables.AppVerticalGrid
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
import fit.asta.health.subscription.remote.model.SubscriptionCategoryData
import fit.asta.health.subscription.view.OffersBanner
import fit.asta.health.subscription.view.OffersUiData
import fit.asta.health.subscription.view.SubscriptionTypesPager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalCoroutinesApi
@Composable
fun HomeScreenLayout(
    toolsHome: ToolsHome,
    subscriptionCategoryState: UiState<List<SubscriptionCategoryData>>,
    offersDataState: UiState<List<OffersData>>,
    refCode: String,
    onEvent: (HomeScreenUiEvent) -> Unit,
    onNav: (String) -> Unit,
) {
    val context = LocalContext.current
    val columns = 3
    val showSubscriptionPlans = remember {
        true//TODO: HIDE SUBSCRIPTION CARDS WHEN PURCHASED
    }

    AppVerticalGrid(
        count = columns,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {
        item(span = { GridItemSpan(columns) }) {
            when (offersDataState) {
                is UiState.Idle -> {
                    LaunchedEffect(
                        key1 = Unit,
                        block = {
                            onEvent(HomeScreenUiEvent.LoadOffersData)
                        }
                    )
                }

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
                            HomeScreenUiEvent.NavigateToFinalPayment(
                                categoryId = categoryId,
                                productId = productId
                            )
                        )
                    }
                }

                else -> {
                    OffersLoadingCard(isLoading = false) {
                        onEvent(HomeScreenUiEvent.LoadOffersData)
                    }
                }
            }
        }

        // Top Sliding Images
        toolsHome.banners?.let { banners ->
            item(span = { GridItemSpan(columns) }) {
                Column {
                    AppHorizontalPager(
                        pagerState = rememberPagerState { banners.size },
                        modifier = Modifier
                            .aspectRatio(ratio = AppTheme.aspectRatio.fullScreen)
                            .fillMaxWidth()
                    ) { page ->
                        ToolsHmScreenTopBanner(bannerDataPages = banners[page])
                    }
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
                }
            }
        }

        item(span = { GridItemSpan(columns) }) {
            when (subscriptionCategoryState) {
                is UiState.Idle -> {
                    LaunchedEffect(
                        key1 = Unit,
                        block = {
                            onEvent(HomeScreenUiEvent.LoadSubscriptionCategoryData)
                        }
                    )
                }

                is UiState.Loading -> {
                    SubscriptionLoadingCard(true)
                }

                is UiState.Success -> {
                    if (showSubscriptionPlans) {
                        SubscriptionTypesPager(
                            subscriptionPlans = subscriptionCategoryState.data
                        ) {
                            onEvent(HomeScreenUiEvent.NavigateToSubscriptionDurations(it))
                        }
                    } else {
//                        UserSubscribedPlanSection(subscriptionResponse.userSubscribedPlan!!)
                        // TODO: ALREADY PURCHASED UI
                    }
                }

                else -> {
                    SubscriptionLoadingCard(isLoading = false) {
                        onEvent(HomeScreenUiEvent.LoadSubscriptionCategoryData)
                    }
                }
            }
        }

        toolsHome.tools?.let {
            // My Tools text and View All button
            item(span = { GridItemSpan(columns) }) {
                Column {
                    ViewAllLayout(title = "My Tools")
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                }
            }

            // All The Tools Composable cards
            items(it) { tool ->
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
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                }
            }
        }

        toolsHome.testimonials?.let { testimonials ->
            // Testimonials Text and View All Button
            item(span = { GridItemSpan(columns) }) {
                ViewAllLayout(
                    title = "Testimonials",
                    clickString = "View All"
                ) { onNav(Graph.Testimonials.route) }
            }

            // Why our Customers text
            item(span = { GridItemSpan(columns) }) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TitleTexts.Level3(text = "Why our customers love ASTA?")
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                    AppDivider(modifier = Modifier.width(AppTheme.customSize.level9))
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                }
            }

            // Testimonials Banners in a Horizontal Pager
            item(span = { GridItemSpan(columns) }) {

                val pagerState = rememberPagerState { testimonials.size }

                Box(modifier = Modifier.padding(bottom = AppTheme.spacing.level1)) {
                    AppHorizontalPager(
                        modifier = Modifier
                            .padding(bottom = AppTheme.spacing.level2)
                            .fillMaxWidth(),
                        pagerState = pagerState,
                        contentPadding = PaddingValues(AppTheme.spacing.level2)
                    ) { page ->
                        AppCard(modifier = Modifier.fillMaxSize()) {
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
        item(span = { GridItemSpan(columns) }) {
            Column {
                RateAppCard()
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            }
        }

        // Feedback Card
        item(span = { GridItemSpan(columns) }) {
            Column {
                FeedbackCard {
                    onNav("$FEEDBACK_GRAPH_ROUTE/application")
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            }
        }

        // Refer and Earn Card
        item(span = { GridItemSpan(columns) }) {
            NewReferralDialogContent(
                refCode = refCode,
                shareRefLink = {
                    context.shareReferralCode(it, BuildConfig.APPLICATION_ID)
                }
            )
        }
    }
}

sealed interface HomeScreenUiEvent {
    data object LoadSubscriptionCategoryData : HomeScreenUiEvent
    data object LoadOffersData : HomeScreenUiEvent
    data class NavigateToSubscriptionDurations(val categoryId: String) : HomeScreenUiEvent
    data class NavigateToFinalPayment(val categoryId: String, val productId: String) :
        HomeScreenUiEvent
}
