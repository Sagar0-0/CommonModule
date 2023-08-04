package fit.asta.health.navigation.home.view.component

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smarttoolfactory.ratingbar.model.RatingInterval
import fit.asta.health.R
import fit.asta.health.common.ui.components.functional.AppRatingBar
import fit.asta.health.common.ui.components.generic.AppButtons
import fit.asta.health.common.ui.components.generic.AppCard
import fit.asta.health.common.ui.components.generic.AppDrawImg
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.aspectRatio
import fit.asta.health.common.ui.theme.buttonSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.navigation.home.viewmodel.RateUsEvent
import fit.asta.health.navigation.home.viewmodel.RateUsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun RateUsCard(viewModel: RateUsViewModel = hiltViewModel()) {

    val context = LocalContext.current
    LaunchedEffect(key1 = viewModel.state.reviewInfo) {
        viewModel.state.reviewInfo?.let {
            viewModel.reviewManager.launchReviewFlow((context as Activity), it)
        }
    }

    AppCard(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(aspectRatio.common), content = {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(spacing.small)
        ) {
            AppDrawImg(
                imgId = R.drawable.rate_us_image,
                contentDescription = "Rate Us Card Img",
                modifier = Modifier.aspectRatio(aspectRatio.fullScreen),
            )
            Spacer(modifier = Modifier.width(spacing.medium))
            Box {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    AppTexts.TitleMedium(text = "Rate Us")
                    AppTexts.BodySmall(text = "We value your feedback pls let us know how we are doing by rating us.")
                    Spacer(modifier = Modifier.height(spacing.small))

                    AppButtons.AppStandardButton(onClick = {
                        viewModel.onEvent(RateUsEvent.InAppReviewRequested)
                    },
                        modifier = Modifier.height(buttonSize.large),
                        contentPadding = PaddingValues(
                            vertical = spacing.minSmall, horizontal = spacing.small
                        ),
                        content = {
                            AppTexts.LabelLarge(
                                text = "Rate Us", color = MaterialTheme.colorScheme.onPrimary
                            )
                        })
                }
            }
        }
    })
}


@OptIn(ExperimentalCoroutinesApi::class)
@Preview
@Composable
fun RateUsDemoCard(viewModel: RateUsViewModel = hiltViewModel()) {

    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.state.reviewInfo) {
        viewModel.state.reviewInfo?.let {
            viewModel.reviewManager.launchReviewFlow((context as Activity), it)
        }
    }

    var isVisible by remember { mutableStateOf(true) }

    val rating by remember {
        mutableIntStateOf(5)
    }
    val ratingState = rememberUpdatedState(newValue = rating)
    val imageBackground = painterResource(id = R.drawable.star_background)
    val imageForeground = painterResource(id = R.drawable.star_foreground)

    AnimatedVisibility(visible = isVisible, exit = fadeOut()) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp), contentAlignment = Alignment.Center
        ) {
            AppCard {
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ScheduleButtonIcon(
                        onButtonClick = { isVisible = false }, imageVector = Icons.Filled.Close
                    )
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.large),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        AppDrawImg(
                            imgId = R.drawable.splash_logo,
                            contentDescription = "Tagline",
                            modifier = Modifier
                                .fillMaxSize()
                                .aspectRatio(aspectRatio.common)
                        )
                    }
                    Spacer(modifier = Modifier.height(spacing.small))
                    AppTexts.BodyLarge(
                        text = "Your feedback will help us to make improvements",
                        modifier = Modifier.padding(horizontal = spacing.large),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(spacing.medium))
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(spacing.small),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        AppRatingBar(
                            rating = ratingState.value.toFloat(),
                            painterEmpty = imageBackground,
                            painterFilled = imageForeground,
                            onRatingChange = {},
                            ratingInterval = RatingInterval.Full,
                            space = 8.dp,
                        )
                    }
                    Spacer(modifier = Modifier.height(spacing.small))
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier.weight(0.6f)
                        ) {
                            OutlinedButton(
                                onClick = { isVisible = false },
                                modifier = Modifier.fillMaxWidth(),
                                border = ButtonDefaults.outlinedButtonBorder.copy(width = 5.dp)
                            ) {
                                AppTexts.LabelSmall(text = "No,Thanks")
                            }
                        }
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            AppButtons.AppStandardButton(
                                onClick = { viewModel.onEvent(RateUsEvent.InAppReviewRequested) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(buttonSize.medium)
                            ) {
                                AppTexts.LabelLarge(
                                    text = "Rate on Play Store",
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(spacing.medium))
                }

            }
        }
    }
}