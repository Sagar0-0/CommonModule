package fit.asta.health.subscription.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.carouselTransition
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.payment.remote.model.OrderRequest
import fit.asta.health.subscription.remote.model.SubscriptionResponse

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SubPlansPager(
    subscriptionPlans: SubscriptionResponse.SubscriptionPlans,
    onClick: (OrderRequest) -> Unit
) {
    val fullScreen = rememberSaveable { mutableStateOf(false) }
    val transition = updateTransition(targetState = fullScreen.value, label = "")

    val pagerState = rememberPagerState(pageCount = { subscriptionPlans.categories.size })
    val pageSpacing by transition.animateDp(label = "") {
        if (!it) AppTheme.spacing.level1 else 0.dp
    }
    val contentPadding by transition.animateDp(label = "") {
        if (it) {
            0.dp
        } else {
            AppTheme.spacing.level6
        }
    }
    HorizontalPager(
        modifier = Modifier.animateContentSize(),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = contentPadding),
        pageSpacing = pageSpacing,
        userScrollEnabled = !fullScreen.value
    ) { page ->
        SubPlanItem(
            fullScreen = fullScreen.value,
            onFullScreenChange = { fullScreen.value = it },
            transition = transition,
            item = subscriptionPlans.categories[page],
            modifier = Modifier
                .carouselTransition(page, pagerState),
            onPayClick = onClick
        )
    }
}

@Composable
private fun SubPlanItem(
    item: SubscriptionResponse.SubscriptionPlans.Category,
    modifier: Modifier,
    fullScreen: Boolean,
    onFullScreenChange: (Boolean) -> Unit,
    transition: Transition<Boolean>,
    onPayClick: (OrderRequest) -> Unit
) {
    val cardSize by transition.animateDp(label = "") {
        if (it) {
            LocalConfiguration.current.screenHeightDp.dp
        } else {
            500.dp
        }
    }
    val cardTopPadding by transition.animateDp(label = "") {
        if (it) {
            0.dp
        } else {
            AppTheme.iconSize.level6 / 2
        }
    }
    val closeButtonSize by transition.animateDp(label = "") {
        if (it) {
            AppTheme.buttonSize.level2
        } else {
            0.dp
        }
    }

    var selectedDurationIndex by rememberSaveable {
        mutableIntStateOf(-1)
    }
    val buttonText = if (fullScreen) {
        if (selectedDurationIndex == -1) {
            "Select a plan duration"
        } else {
            "Join ${item.ttl} plan for ${item.durations[selectedDurationIndex].ttl}"
        }
    } else {
        "Get Started"
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        AppCard(
            onClick = {
                if (!fullScreen) {
                    onFullScreenChange(true)
                }
            },
            modifier = Modifier
                .padding(top = cardTopPadding)
                .height(cardSize)
                .fillMaxWidth(),
        ) {
            AppIconButton(
                imageVector = Icons.Default.Close,
                modifier = Modifier
                    .size(closeButtonSize)
                    .clip(CircleShape)
                    .align(Alignment.End)
            ) { onFullScreenChange(false) }

            Crossfade(targetState = fullScreen, label = "") {
                if (it) {
                    Column {
                        HeadingTexts.Level1(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = item.ttl
                        )

                    }
                } else {
                    HeadingTexts.Level2(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = item.ttl
                    )
                }
            }


            AnimatedVisibility(visible = fullScreen) {
                //Features
                Row(
                    Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    item.durations.forEachIndexed { idx, duration ->
                        AppCard(
                            modifier = Modifier
                                .padding(AppTheme.spacing.level1)
                                .weight(1f)
                                .clickable {
                                    selectedDurationIndex = idx
                                },
                        ) {
                            Column(modifier = Modifier.padding(AppTheme.spacing.level1)) {
                                TitleTexts.Level2(text = duration.ttl)
                                TitleTexts.Level2(text = duration.price)
                            }

                        }
                    }
                }
            }

            AppTextButton(
                textToShow = buttonText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                enabled = !fullScreen || selectedDurationIndex != -1
            ) {
                if (fullScreen) {
                    onPayClick(
                        OrderRequest(
                            subscriptionDetail = OrderRequest.SubscriptionDetail(
                                subType = item.id,
                                durType = item.durations[selectedDurationIndex].id
                            )
                        )
                    )
                } else {
                    onFullScreenChange(true)
                }
            }
        }

        val animatedIconSize by transition.animateDp(label = "") {
            if (it) {
                0.dp
            } else {
                AppTheme.iconSize.level6
            }
        }
        AppIcon(
            modifier = Modifier
                .size(animatedIconSize)
                .clip(CircleShape)
                .background(Color.Green),
            imageVector = Icons.Default.AccountBox,
            contentDescription = ""
        )
    }

}