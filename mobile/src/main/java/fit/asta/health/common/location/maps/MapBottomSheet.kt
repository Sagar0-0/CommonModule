package com.example.googlemapsapp.maps


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.maps.android.compose.*
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapBottomSheet(
    sheetScaffoldState: BottomSheetScaffoldState,
    locationName: String,
    onButtonClick: () -> Unit,
    onCollapse: () -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        if (sheetScaffoldState.bottomSheetState.isCollapsed) {
            LazyColumn(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(Color.DarkGray)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Row(
                        Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .size(30.dp),
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = Color.Red
                        )
                        Text(
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            text = locationName,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                    }
                }
                item {
                    Button(
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp)),
                        onClick = onButtonClick
                    ) {
                        Text(
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(10.dp),
                            text = "Enter complete address",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

        } else {
            FillAddressSheet(
                onCloseIconClick = onCollapse,
                locationName = locationName,
                onSaveAddress = {
                    //Save the filled address
                }
            )
        }
    }
}