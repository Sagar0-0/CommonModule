@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.imageSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@Composable
fun ProfileChipCard(
    viewModel: ProfileViewModel = hiltViewModel(),
    icon: Int,
    title: String,
    list: List<HealthProperties>,
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(spacing.small),
        elevation = CardDefaults.cardElevation(cardElevation.smallExtraMedium)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier.size(imageSize.largeMedium)
                    )
                    Spacer(modifier = Modifier.width(spacing.small))
                    Text(
                        text = title,
                        fontSize = 10.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 1.5.sp,
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(spacing.medium))
            FlowRow(mainAxisSpacing = spacing.small, crossAxisSpacing = spacing.extraSmall) {
                list.forEach {
                    DisabledChipForList(textOnChip = it.name)
                }
            }
        }
    }
}