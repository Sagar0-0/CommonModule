package fit.asta.health.navigation.today.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.navigation.home.view.component.NameAndMoodHomeScreenHeader

@Composable
fun TodayScreenLayout() {

    Box(Modifier.fillMaxSize()) {

        LazyColumn(modifier = Modifier.fillMaxWidth()) {

            item {
                Modifier.height(spacing.medium)
            }

            item {
                NameAndMoodHomeScreenHeader()
            }


        }

    }

}