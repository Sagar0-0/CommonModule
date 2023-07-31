package fit.asta.health.navigation.home.view.component

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.R
import fit.asta.health.common.ui.components.generic.AppDefBtn
import fit.asta.health.common.ui.components.generic.AppDefCard
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

    AppDefCard(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(aspectRatio.large), content = {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(spacing.small)
        ) {

            AppDrawImg(
                imgId = R.drawable.rate_us_image,
                contentDescription = "Rate Us Card Img",
                modifier = Modifier.aspectRatio(aspectRatio.small),
            )

            Spacer(modifier = Modifier.width(spacing.medium))
            Box {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    AppTexts.TitleMedium(cardTitle = "Rate Us")
                    AppTexts.BodySmall(cardTitle = "We value your feedback pls let us know how we are doing by rating us.")
                    Spacer(modifier = Modifier.height(spacing.small))

                    AppDefBtn(onClick = {
                        viewModel.onEvent(RateUsEvent.InAppReviewRequested)
                    }, Modifier.height(buttonSize.large), contentPadding = PaddingValues(
                        vertical = spacing.minSmall, horizontal = spacing.small
                    ), content = {
                        AppTexts.LabelLarge(
                            cardTitle = "Rate Us",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    })

                }
            }
        }
    })

}