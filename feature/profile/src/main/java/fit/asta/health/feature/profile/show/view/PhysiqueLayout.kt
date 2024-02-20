package fit.asta.health.feature.profile.show.view

//@Composable
//fun PhysiqueLayout(
//    physique: Physique,
//) {
//    val genderLayoutDataList = createGenderLayoutDataList(physique)
//    AppLazyVerticalGrid(
//        count = 2,
//        content = {
//            items(genderLayoutDataList) { genderLayoutData ->
//                GenderOptionsLayout(
//                    cardImg = genderLayoutData.cardImg,
//                    cardType = genderLayoutData.cardType,
//                    cardValue = genderLayoutData.cardValue
//                )
//            }
//        },
//        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
//        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
//    )
//}
//
//@Composable
//private fun createGenderLayoutDataList(physique: Physique): List<GenderLayoutData> {
//    val genderList = if (physique.gender == 2) {
//        listOf(
//            GenderLayoutData(
//                RDraw.drawable.age,
//                stringResource(R.string.age),
//                physique.age.toString()
//            ),
//            GenderLayoutData(
//                RDraw.drawable.gender,
//                stringResource(R.string.gender),
//                getGenderLabel(physique.gender)
//            ),
//            GenderLayoutData(
//                RDraw.drawable.height,
//                stringResource(R.string.height),
//                "${physique.height.toInt()} Cm"
//            ),
//            GenderLayoutData(
//                RDraw.drawable.weight,
//                stringResource(R.string.weight),
//                "${physique.weight.toInt()} Kg"
//            ),
//            GenderLayoutData(
//                RDraw.drawable.bmi, stringResource(R.string.bmi), physique.bmi.toInt().toString()
//            ),
//            GenderLayoutData(
//                RDraw.drawable.pregnant,
//                stringResource(R.string.pregnancy),
//                "${physique.pregnancyWeek} Week"
//            )
//        )
//
//    } else {
//        listOf(
//            GenderLayoutData(
//                RDraw.drawable.age,
//                stringResource(R.string.age),
//                physique.age.toString()
//            ),
//            GenderLayoutData(
//                RDraw.drawable.gender,
//                stringResource(R.string.gender),
//                getGenderLabel(physique.gender)
//            ),
//            GenderLayoutData(
//                RDraw.drawable.height,
//                stringResource(R.string.height),
//                "${physique.height.toInt()} Cm"
//            ),
//            GenderLayoutData(
//                RDraw.drawable.weight,
//                stringResource(R.string.weight),
//                "${physique.weight.toInt()} Kg"
//            ),
//            GenderLayoutData(
//                RDraw.drawable.bmi, stringResource(R.string.bmi), physique.bmi.toInt().toString()
//            ),
//        )
//    }
//
//    return genderList
//}
//
//@Composable
//private fun getGenderLabel(gender: Int): String {
//    return when (gender) {
//        1 -> stringResource(R.string.male)
//        2 -> stringResource(R.string.female)
//        else -> stringResource(R.string.others)
//    }
//}
//
//data class GenderLayoutData(
//    val cardImg: Int,
//    val cardType: String,
//    val cardValue: String,
//)