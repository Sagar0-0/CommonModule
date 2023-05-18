package fit.asta.health.profile.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.R
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.model.domain.Physique
import fit.asta.health.profile.view.components.FemaleLayout
import fit.asta.health.profile.view.components.MaleLayout
import fit.asta.health.profile.view.components.UserBodyType

@Composable
fun PhysiqueLayout(
    phy: Physique,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(spacing.medium))

        if (phy.gender == 2) FemaleLayout(
            phy
        ) else MaleLayout(
            phy
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        UserBodyType(
            bodyType = phy.bodyType.toString(), bodyImg = R.drawable.bodyfat
        )

        Spacer(modifier = Modifier.height(spacing.medium))

    }
}