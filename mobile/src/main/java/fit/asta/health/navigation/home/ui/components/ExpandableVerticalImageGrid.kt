package fit.asta.health.navigation.home.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fit.asta.health.R

@Composable
fun ExpandedVerticalImageGrid() {
    Column(Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()) {
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.waterimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }
                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Water",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.breathingimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }

                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Breathing",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.meditationimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }

                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Meditation",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
        }


        Spacer(modifier = Modifier.padding(top = 16.dp))



        Row(modifier = Modifier
            .fillMaxWidth()) {
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.sunlightimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }

                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Sunlight",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.sleepimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }

                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Sleep",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.stepsimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }

                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Steps",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
        }
    }
}