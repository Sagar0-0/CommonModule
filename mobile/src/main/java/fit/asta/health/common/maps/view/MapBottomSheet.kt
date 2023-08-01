package fit.asta.health.common.maps.view

import android.location.Address
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import com.google.maps.android.compose.*
import fit.asta.health.common.maps.modal.AddressesResponse
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.getLocationName
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapBottomSheet(
    sheetScaffoldState: BottomSheetScaffoldState,
    address: Address,
    myAddressItem: AddressesResponse.MyAddress,
    onEnterAddressClick: () -> Unit,
    onCollapse: () -> Unit,
    onBackPress: () -> Unit,
    onSaveAddress: (address: AddressesResponse.MyAddress, onSuccess: () -> Unit) -> Unit
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
                name = address.getAddressLine(0),
                area = getLocationName(address)
            )

            OutlinedButton(
                onClick = onEnterAddressClick,
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
            address = address,
            myAddressItem = myAddressItem,
            onBackPress = onBackPress,
            onCloseIconClick = onCollapse,
            onSaveAddress = onSaveAddress
        )
    }
}