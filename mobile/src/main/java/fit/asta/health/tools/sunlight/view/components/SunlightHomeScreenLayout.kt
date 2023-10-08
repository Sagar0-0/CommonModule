package fit.asta.health.tools.sunlight.view.components

//@Composable
//fun SunlightHomeScreenLayout(paddingValues: PaddingValues) {
//
//    Column(
//        Modifier
//            .fillMaxWidth()
//            .padding(paddingValues)
//            .verticalScroll(rememberScrollState())
//
//    ) {
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Row(
//            Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp)
//        ) {
//            Text(text = "Upcoming Slots",
//                fontWeight = FontWeight.Bold,
//                fontSize = 16.sp,
//                lineHeight = 22.4.sp,
//                //color = Color.Black
//                color = AppTheme.colors.onSurface
//            )
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Row(modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Box(
//                Modifier
//                    .fillMaxWidth()
//                    .weight(1f)
//            ) {
//                UpcomingSlotsCard()
//            }
//
//            Spacer(modifier = Modifier.width(8.dp))
//
//            Box(
//                Modifier
//                    .fillMaxWidth()
//                    .weight(1f)) {
//                UpcomingSlotsCard()
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Row(
//            Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp)) {
//            Text(text = "Total Duration",
//                fontWeight = FontWeight.Bold,
//                fontSize = 16.sp,
//                lineHeight = 22.4.sp,
//                color = Color.Black)
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        CardSunBurn(cardTitle = "Duration",
//            cardValue = "1 hr ",
//            recommendedTitle = "Vitamin D\nRecommended",
//            recommendedValue = "1hr 30 min",
//            goalTitle = "Vitamin D\nDaily Goal",
//            goalValue = "50 min",
//            remainingTitle = "Sunburn\nTime Remaining",
//            remainingValue = "30 min",
//        valueChanged = null)
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Row(
//            Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp)) {
//            Text(text = "Total Vitamin D ",
//                fontWeight = FontWeight.Bold,
//                fontSize = 16.sp,
//                lineHeight = 22.4.sp,
//                color = Color.Black)
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        TotalVitaminDCard()
//
//        Spacer(modifier = Modifier.height(64.dp))
//
//    }
//}