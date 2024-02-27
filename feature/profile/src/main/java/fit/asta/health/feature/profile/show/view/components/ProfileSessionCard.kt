package fit.asta.health.feature.profile.show.view.components

//@Composable
//fun ProfileSessionCard(
//    title: String,
//    sleepSchedule: TimeSchedule,
//) {
//    AppCard {
//        Column(
//            modifier = Modifier
//                .padding(AppTheme.spacing.level2)
//                .fillMaxWidth()
//        ) {
//            BodyTexts.Level3(text = title)
//            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
//            Row(
//                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
//            ) {
//                UserSleepCycles(
//                    columnType = "BED TIME",
//                    columnValue = sleepSchedule.from.toString()
//                )
//                Spacer(modifier = Modifier.width(AppTheme.spacing.level5))
//                UserSleepCycles(columnType = "WAKE UP", columnValue = sleepSchedule.to.toString())
//            }
//        }
//    }
//}