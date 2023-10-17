package fit.asta.ccp.ui

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import fit.asta.ccp.R
import fit.asta.ccp.data.CountryData
import fit.asta.ccp.data.utils.getCountryName
import fit.asta.ccp.data.utils.getFlags
import fit.asta.ccp.data.utils.getLibCountries
import fit.asta.ccp.utils.searchCountry
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.dialog.AppDialog
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.textfield.AppBasicTextField
import fit.asta.health.designsystem.molecular.texts.BodyTexts

@Composable
fun CCPDialog(
    modifier: Modifier = Modifier,
    padding: Dp = 15.dp,
    defaultSelectedCountry: CountryData = getLibCountries.first(),
    showCountryCode: Boolean = true,
    pickedCountry: (CountryData) -> Unit,
    showFlag: Boolean = true,
    showCountryName: Boolean = false,
) {
    val context = LocalContext.current

    val countryList: List<CountryData> = getLibCountries
    var isPickCountry by remember {
        mutableStateOf(defaultSelectedCountry)
    }
    var isOpenDialog by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier
        .padding(padding)
        .clickable(
            interactionSource = interactionSource,
            indication = null,
        ) {
            isOpenDialog = true
        }) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showFlag) {
                AppLocalImage(
                    modifier = modifier.width(AppTheme.imageSize.level4),
                    painter = painterResource(id = getFlags(isPickCountry.countryCode))
                )
            }
            if (showCountryCode) {
                BodyTexts.Level1(
                    text = isPickCountry.countryPhoneCode,
                    modifier = Modifier.padding(start = AppTheme.spacing.level1)
                )
                AppIcon(imageVector = Icons.Default.ArrowDropDown)
            }
            if (showCountryName) {
                BodyTexts.Level1(
                    text = stringResource(id = getCountryName(isPickCountry.countryCode.lowercase())),
                    modifier = Modifier.padding(start = AppTheme.spacing.level1)
                )
                AppIcon(imageVector = Icons.Default.ArrowDropDown)
            }
        }


        if (isOpenDialog) {
            CountryDialog(countryList = countryList,
                onDismissRequest = { isOpenDialog = false },
                context = context,
                dialogStatus = isOpenDialog,
                onSelected = { countryItem ->
                    pickedCountry(countryItem)
                    isPickCountry = countryItem
                    isOpenDialog = false
                })
        }
    }
}

@Composable
fun CountryDialog(
    modifier: Modifier = Modifier,
    countryList: List<CountryData>,
    onDismissRequest: () -> Unit,
    onSelected: (item: CountryData) -> Unit,
    context: Context,
    dialogStatus: Boolean,
) {
    var searchValue by remember { mutableStateOf("") }

    if (!dialogStatus) searchValue = ""

    AppDialog(onDismissRequest = onDismissRequest, content = {
        AppSurface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(AppTheme.shape.level3)
        ) {
            AppScaffold { scaffold ->
                scaffold.calculateBottomPadding()
                Column(modifier = Modifier.fillMaxSize()) {
                    SearchTextField(
                        value = searchValue, onValueChange = { searchValue = it },
                        textColor = AppTheme.colors.onSurface,
                        leadingIcon = {
                            AppIcon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                modifier = Modifier.padding(horizontal = AppTheme.spacing.level0)
                            )
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .height(AppTheme.spacing.level5),
                    )
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level1))

                    LazyColumn {
                        items(
                            if (searchValue.isEmpty()) countryList else countryList.searchCountry(
                                searchValue, context
                            )
                        ) { countryItem ->
                            Row(
                                Modifier
                                    .padding(AppTheme.spacing.level2)
                                    .fillMaxWidth()
                                    .clickable(onClick = { onSelected(countryItem) }),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AppLocalImage(
                                    modifier = modifier.width(AppTheme.spacing.level4),
                                    painter = painterResource(id = getFlags(countryItem.countryCode))
                                )
                                BodyTexts.Level2(
                                    text = stringResource(id = getCountryName(countryItem.countryCode.lowercase())),
                                    Modifier.padding(horizontal = AppTheme.spacing.level2),
                                )
                            }
                        }
                    }
                }
            }
        }
    })
}


@Composable
private fun SearchTextField(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    value: String,
    textColor: Color = AppTheme.colors.onSurface,
    onValueChange: (String) -> Unit,
    hint: String = stringResource(id = R.string.search),
    fontSize: TextUnit = AppTheme.customTypography.body.level2.fontSize,
) {

    AppBasicTextField(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = AppTheme.spacing.level2),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        cursorBrush = SolidColor(AppTheme.colors.primary),
        textStyle = LocalTextStyle.current.copy(
            color = textColor, fontSize = fontSize
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier, verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) BodyTexts.Level3(text = hint, color = textColor)
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        })
}