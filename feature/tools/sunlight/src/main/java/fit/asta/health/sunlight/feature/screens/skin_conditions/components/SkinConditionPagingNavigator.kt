package fit.asta.health.sunlight.feature.screens.skin_conditions.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionPager
import fit.asta.health.resources.strings.R as StringR


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SkinConditionPagingNavigator(
    state: androidx.compose.foundation.pager.PagerState,
    modifier: Modifier = Modifier,
    uploadDataAndNavigate: () -> Unit,
    goto: (Int) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
    ) {
        Box(
            modifier = Modifier.weight(1f, fill = true),
            propagateMinConstraints = true
        ) {
            if (state.currentPage > 0) {
                AppFilledButton(textToShow = stringResource(id = StringR.string.back)) {
                    goto.invoke(state.currentPage - 1)
                }
            }
        }
        Box(
            modifier = Modifier.weight(1f, fill = true),
            propagateMinConstraints = true
        ) {
            AppFilledButton(
                textToShow = if (state.currentPage == SkinConditionPager.SELECT_SUPPLEMENT) stringResource(
                    id = StringR.string.done
                ) else stringResource(id = StringR.string.next)
            ) {
                if (state.currentPage == SkinConditionPager.SELECT_SUPPLEMENT) {
                    uploadDataAndNavigate.invoke()
                } else {
                    goto.invoke(state.currentPage + 1)
                }
            }
        }
    }
}