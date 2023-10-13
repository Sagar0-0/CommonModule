package fit.asta.health.navigation.tools.ui.view.component

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.R
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.other.AppRatingBar
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.navigation.tools.ui.viewmodel.RateUsEvent
import fit.asta.health.navigation.tools.ui.viewmodel.RateUsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Preview
@Composable
fun RateAppCard(
    modifier: Modifier = Modifier,
    viewModel: RateUsViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.state.reviewInfo) {
        viewModel.state.reviewInfo?.let {
            viewModel.reviewManager.launchReviewFlow((context as Activity), it)
        }
    }

    var isVisible by remember { mutableStateOf(true) }
    val rating by remember { mutableIntStateOf(3) }

    val ratingState = rememberUpdatedState(newValue = rating)

    AnimatedVisibility(modifier = modifier, visible = isVisible, exit = fadeOut()) {

        AppCard {
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Close Button
                ScheduleButtonIcon(imageVector = Icons.Filled.Close) {
                    isVisible = false
                }

                // Rating Image
                AppLocalImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(AppTheme.shape.level2)
                        .size(AppTheme.imageSize.newLevel)
                        .padding(horizontal = AppTheme.spacing.level4),
                    painter = painterResource(id = R.drawable.rating_image),
                    contentDescription = "Tagline",
                    contentScale = ContentScale.Fit
                )


                // Your Feedback Text
                TitleTexts.Level4(
                    text = "Your feedback will help us to make improvements",
                    modifier = Modifier
                        .padding(horizontal = AppTheme.spacing.level4)
                        .alpha(.7f),
                    textAlign = TextAlign.Center,
                    maxLines = 3
                )

                // Rating Stars Function
                AppRatingBar(rating = ratingState.value.toFloat(), onRatingChange = {})

                // Last 2 Buttons on the Card
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.spacing.level1),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    // No Thanks Button
                    AppOutlinedButton(textToShow = "No,Thanks") {
                        isVisible = false
                    }

                    // Rate on Play Store Button
                    AppFilledButton(textToShow = "Rate on Play Store") {
                        viewModel.onEvent(RateUsEvent.InAppReviewRequested)
                    }
                }
            }
        }
    }
}