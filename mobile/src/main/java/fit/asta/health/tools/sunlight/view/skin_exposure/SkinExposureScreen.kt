package fit.asta.health.tools.sunlight.view.skin_exposure

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fit.asta.health.R
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.molecular.AppScaffold
import fit.asta.health.designsystem.molecular.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.tools.sunlight.viewmodel.SunlightViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SkinExposureLayout(navController: NavController, homeViewModel: SunlightViewModel) {

    val cardList = remember {
        mutableStateListOf(
            SkinExposerData(1, percentValue = "40%", cardImg = R.drawable.girl_avatar_40),
            SkinExposerData(2, percentValue = "30%", cardImg = R.drawable.boy_avatar_30),
            SkinExposerData(3, percentValue = "45%", cardImg = R.drawable.girl_avatar_45),
            SkinExposerData(4, percentValue = "50%", cardImg = R.drawable.boy_avatar_50),
            SkinExposerData(5, percentValue = "25%", cardImg = R.drawable.girl_avatar_25),
            SkinExposerData(6, percentValue = "40%", cardImg = R.drawable.boy_avatar_40),
            SkinExposerData(7, percentValue = "20%", cardImg = R.drawable.girl_avatar_20),
            SkinExposerData(8, percentValue = "20%", cardImg = R.drawable.boy_avatar_20),
        )
    }
    SkinExposureScreen(
        navController = navController,
        list = cardList
    ) { homeViewModel.onSkinExposureSelection(it) }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SkinExposureScreen(
    navController: NavController,
    list: MutableList<SkinExposerData>,
    onClick: (String) -> Unit
) {
    val itemSelection = remember {
        mutableIntStateOf(-1)
    }

    AppScaffold(
        topBar = {
            AppTopBarWithHelp(
                title = "Skin Exposure",
                onBack = { navController.popBackStack() },
                onHelp = { /*TODO*/ }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    TitleTexts.Level2(
                        text = "Please select skin exposure percentage based on clothing",
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(10.dp),
                    userScrollEnabled = false
                ) {
                    items(list.size) { index ->
                        SkinExposureCardModified(
                            cardValue = list[index].percentValue,
                            cardImg = list[index].cardImg,
                            itemData = list[index],
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    onClick(list[index].percentValue)
                                    itemSelection.value =
                                        if (itemSelection.value != index) index
                                        else -1
                                },
                            isSelected = itemSelection.value == index
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun SkinExposureCardModified(
    itemData: SkinExposerData,
    cardValue: String,
    cardImg: Int,
    modifier: Modifier,
    isSelected: Boolean
) {
    AppCard(
        modifier = modifier.padding(8.dp),
        colors = CardDefaults.cardColors(Color(0x66959393))
    ) {
        SkinExposureCardComponentModified(
            cardValue = cardValue,
            cardImg = cardImg,
            itemData = itemData,
        )
    }
    if (isSelected) {
        Box(contentAlignment = Alignment.TopEnd) {
            AppIcon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color.Green,
                modifier = Modifier
                    .size(25.dp)
                    .padding(top = 8.dp, end = 8.dp)
            )
        }
    }
}

@Composable
fun SkinExposureCardComponentModified(
    itemData: SkinExposerData,
    cardValue: String,
    cardImg: Int,
) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppLocalImage(
                painter = painterResource(id = cardImg),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            TitleTexts.Level1(
                text = cardValue,
            )
        }
    }
}