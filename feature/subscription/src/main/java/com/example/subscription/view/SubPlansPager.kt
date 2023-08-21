package com.example.subscription.view

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
import androidx.compose.material3.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.spacing
import com.example.payment.model.OrderRequest
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.components.generic.carouselTransition
import fit.asta.health.common.ui.theme.iconButtonSize
import fit.asta.health.common.ui.theme.iconSize
import fit.asta.health.subscription.model.SubscriptionResponse.Data.SubscriptionPlans

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SubPlansPager(
    subscriptionPlans: SubscriptionPlans,
    onClick: (OrderRequest) -> Unit
) {
    val fullScreen = rememberSaveable { mutableStateOf(false) }
    val transition = updateTransition(targetState = fullScreen.value, label = "")

    val pagerState = rememberPagerState(pageCount = { subscriptionPlans.categories.size })
    val pageSpacing by transition.animateDp(label = "") {
        if (!it) spacing.small else 0.dp
    }
    val contentPadding by transition.animateDp(label = "") {
        if (it) {
            0.dp
        } else {
            spacing.extraLarge
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
            color = CardDefaults.cardColors(
                containerColor =
                if (pagerState.currentPage == page) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.secondaryContainer
                }
            ),
            onPayClick = onClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubPlanItem(
    item: SubscriptionPlans.Category,
    modifier: Modifier,
    color: CardColors,
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
            iconSize.medium / 2
        }
    }
    val closeButtonSize by transition.animateDp(label = "") {
        if (it) {
            iconButtonSize.medium
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
        Card(
            onClick = {
                if (!fullScreen) {
                    onFullScreenChange(true)
                }
            },
            modifier = Modifier
                .padding(top = cardTopPadding)
                .height(cardSize)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = color
        ) {
            IconButton(
                modifier = Modifier
                    .size(closeButtonSize)
                    .clip(CircleShape)
                    .align(Alignment.End),
                onClick = { onFullScreenChange(false) }
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "")
            }

            Crossfade(targetState = fullScreen, label = "") {
                if (it) {
                    Column {
                        AppTexts.HeadlineLarge(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = item.ttl
                        )

                    }
                } else {
                    AppTexts.HeadlineSmall(
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
                        Card(
                            modifier = Modifier
                                .padding(spacing.small)
                                .weight(1f)
                                .clickable {
                                    selectedDurationIndex = idx
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Column(modifier = Modifier.padding(spacing.small)) {
                                Text(text = duration.ttl)
                                Text(text = duration.price)
                            }

                        }
                    }
                }
            }

            Button(
                enabled = !fullScreen || selectedDurationIndex != -1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.medium),
                onClick = {
                    if (fullScreen) {
                        onPayClick(
                            OrderRequest(
                                subType = item.id,
                                durType = item.durations[selectedDurationIndex].id
                            )
                        )
                    } else {
                        onFullScreenChange(true)
                    }
                }) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = buttonText,
                    textAlign = TextAlign.Center
                )
            }

        }

        val animatedIconSize by transition.animateDp(label = "") {
            if (it) {
                0.dp
            } else {
                iconSize.medium
            }
        }
        Icon(
            modifier = Modifier
                .size(animatedIconSize)
                .clip(CircleShape)
                .background(Color.Green),
            imageVector = Icons.Default.AccountBox,
            contentDescription = ""
        )
    }

}