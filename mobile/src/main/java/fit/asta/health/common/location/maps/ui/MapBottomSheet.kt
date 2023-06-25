package fit.asta.health.common.location.maps.ui

import android.location.Address
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import com.google.maps.android.compose.*
import fit.asta.health.common.location.maps.MapsViewModel
import fit.asta.health.common.location.maps.modal.AddressesResponse
import fit.asta.health.common.ui.theme.spacing
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapBottomSheet(
    navHostController: NavHostController,
    sheetScaffoldState: BottomSheetScaffoldState,
    address: Address,
    addressItem: AddressesResponse.Address,
    onButtonClick: () -> Unit,
    onCollapse: () -> Unit,
    mapsViewModel: MapsViewModel
) {
    if (sheetScaffoldState.bottomSheetState.currentValue == SheetValue.PartiallyExpanded) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = spacing.medium),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrentLocationUi(
                name = (if (addressItem.area.isNotEmpty()) addressItem.area + ", " else "") + (address.locality
                    ?: address.subAdminArea
                    ?: address.adminArea ?: ""),
                area = (address.adminArea ?: "") + ", " + (address.countryName ?: "")
            )

            OutlinedButton(
                onClick = onButtonClick,
                modifier = Modifier
                    .padding(bottom = spacing.medium)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(
                    maxLines = 1,
                    text = "Enter complete address",
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    } else {
        FillAddressSheet(
            navHostController = navHostController,
            onCloseIconClick = onCollapse,
            address = address,
            addressItem = addressItem,
            mapsViewModel = mapsViewModel
        )
    }
}