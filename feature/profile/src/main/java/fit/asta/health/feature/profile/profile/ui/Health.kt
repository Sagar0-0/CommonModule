//@file:OptIn(
//    ExperimentalCoroutinesApi::class,
//    ExperimentalCoroutinesApi::class,
//    ExperimentalCoroutinesApi::class,
//    ExperimentalCoroutinesApi::class,
//    ExperimentalCoroutinesApi::class,
//    ExperimentalCoroutinesApi::class,
//    ExperimentalCoroutinesApi::class,
//    ExperimentalCoroutinesApi::class
//)

package fit.asta.health.feature.profile.profile.ui

//@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterialApi::class)
//@Composable
//fun HealthCreateScreen(
//    userProfileState: UserProfileState,
//    viewModel: ProfileViewModel = hiltViewModel()
//) {
//
//    val propertiesDataState by viewModel.propertiesData.collectAsStateWithLifecycle()
////    val composeFirstData: Map<Int, SnapshotStateList<HealthProperties>>? =
////        propertiesDataState[ComposeIndex.First]
//
////    val searchQuery = remember { mutableStateOf("") }
//
////    val scope = rememberCoroutineScope()
//
//    var currentBottomSheet: HealthCreateBottomSheetTypes? by remember {
//        mutableStateOf(null)
//    }
//
////    var modalBottomSheetValue by remember {
////        mutableStateOf(ModalBottomSheetValue.Hidden)
////    }
//
////    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = modalBottomSheetValue)
//
////    val openSheet = {
////        scope.launch {
////            modalBottomSheetState.show()
////            if (modalBottomSheetValue == ModalBottomSheetValue.HalfExpanded) {
////                modalBottomSheetValue = ModalBottomSheetValue.Expanded
////            }
////        }
////    }
//
////    val onBottomSheetItemClick: (String) -> Unit = { propertyType ->
////        currentBottomSheet?.let {
////            openSheet()
////            searchQuery.value = ""
////            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = propertyType))
////        }
////    }
//
////    val onItemClick: (HealthCreateBottomSheetTypes, String) -> Unit = { sheetType, propertyType ->
////        currentBottomSheet = sheetType
////        onBottomSheetItemClick(propertyType)
////    }
//
//
//    AppModalBottomSheetLayout(
//        sheetContent = {
//            Spacer(modifier = Modifier.height(1.dp))
//            currentBottomSheet?.let {
//
//            }
//        },
////        sheetState = modalBottomSheetState,
//        content = {
////            HealthContent(
////                userProfileState = userProfileState,
////                onHealthHistory = { onItemClick(HEALTHHISTORY, "ailment") },
////                onInjuries = { onItemClick(INJURIES, "injury") },
////                onAilments = { onItemClick(AILMENTS, "ailment") },
////                onMedications = { onItemClick(MEDICATIONS, "med") },
////                onHealthTargets = { onItemClick(HEALTHTARGETS, "tgt") },
////                onBodyInjurySelect = { onItemClick(BODYPARTS, "bp") },
////                onAddictionSelect = { onItemClick(ADDICTION, "add") },
////                composeFirstData = composeFirstData
////            )
//        }
//    )
//
//}

//@ExperimentalCoroutinesApi
//@Composable
//fun HealthContent(
//    userProfileState: UserProfileState,
//    viewModel: ProfileViewModel = hiltViewModel(),
//    onHealthHistory: () -> Unit,
//    onInjuries: () -> Unit,
//    onAilments: () -> Unit,
//    onMedications: () -> Unit,
//    onHealthTargets: () -> Unit,
//    onBodyInjurySelect: () -> Unit,
//    onAddictionSelect: () -> Unit,
//    composeFirstData: Map<Int, SnapshotStateList<HealthProperties>>?,
//) {

// Selection Inputs
//    val radioButtonSelections by viewModel.radioButtonSelections.collectAsStateWithLifecycle()
//
//    val selectedHealthHistory =
//        radioButtonSelections[MultiRadioBtnKeys.HEALTHHIS.key] as TwoRadioBtnSelections?
//    val selectedInjuries =
//        radioButtonSelections[MultiRadioBtnKeys.INJURIES.key] as TwoRadioBtnSelections?
//    val selectedAilment =
//        radioButtonSelections[MultiRadioBtnKeys.AILMENTS.key] as TwoRadioBtnSelections?
//    val selectedMedication =
//        radioButtonSelections[MultiRadioBtnKeys.MEDICATIONS.key] as TwoRadioBtnSelections?
//    val selectedHealthTarget =
//        radioButtonSelections[MultiRadioBtnKeys.HEALTHTAR.key] as TwoRadioBtnSelections?
//    val selectedAddiction =
//        radioButtonSelections[MultiRadioBtnKeys.ADDICTION.key] as TwoRadioBtnSelections?
//    val selectedBodyPart =
//        radioButtonSelections[MultiRadioBtnKeys.BODYPART.key] as TwoRadioBtnSelections?
//
//    //List Creation
//    val selectionList = listOf(
//        Pair(ComposeIndex.First, selectedHealthHistory),
//        Pair(ComposeIndex.First, selectedInjuries),
//        Pair(ComposeIndex.First, selectedBodyPart),
//        Pair(ComposeIndex.First, selectedAilment),
//        Pair(ComposeIndex.First, selectedMedication),
//        Pair(ComposeIndex.First, selectedHealthTarget),
//        Pair(ComposeIndex.First, selectedAddiction)
//    )
//
//    val onItemSelectionFunctionList = listOf(
//        onHealthHistory,
//        onInjuries,
//        onBodyInjurySelect,
//        onAilments,
//        onMedications,
//        onHealthTargets,
//        onAddictionSelect
//    )
//
//    val cardTypeList = listOf(
//        MultiRadioBtnKeys.HEALTHHIS,
//        MultiRadioBtnKeys.INJURIES,
//        MultiRadioBtnKeys.BODYPART,
//        MultiRadioBtnKeys.AILMENTS,
//        MultiRadioBtnKeys.MEDICATIONS,
//        MultiRadioBtnKeys.HEALTHTAR,
//        MultiRadioBtnKeys.ADDICTION
//    )

//    HealthContentLayout(
//        userProfileState = userProfileState
//    )

//}


//@Composable
//private fun HealthContentLayout(
//    userProfileState: UserProfileState,
//    composeFirstData: Map<Int, SnapshotStateList<HealthProperties>>?,
//    selections: List<Pair<ComposeIndex, TwoRadioBtnSelections?>>,
//    onItemSelectFunctions: List<() -> Unit>,
//    cardTypes: List<MultiRadioBtnKeys>,
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = AppTheme.spacing.level2)
//            .verticalScroll(rememberScrollState())
//            .background(color = AppTheme.colors.background),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
//
//        cardTypes.indices.forEach { index ->
//            val (composeIndex, selectedOption) = selections[index]
//            val cardType = cardTypes[index]
//            val onItemSelect = onItemSelectFunctions[index]
//
//            SelectionCardCreateProfile(
//                cardType = cardType.getListName(),
//                cardList = composeFirstData?.get(index),
//                onItemsSelect = onItemSelect,
//                selectedOption = selectedOption,
//                listName = cardType.getListName()
//            )
//
//            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
//        }
//        CreateProfileTwoButtonLayout(userProfileState)
//        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
//    }
//}


//sealed class HealthCreateBottomSheetTypes(val cardIndex: Int) {
//    data object HEALTHHISTORY : HealthCreateBottomSheetTypes(0)
//    data object INJURIES : HealthCreateBottomSheetTypes(1)
//    data object BODYPARTS : HealthCreateBottomSheetTypes(2)
//    data object AILMENTS : HealthCreateBottomSheetTypes(3)
//    data object MEDICATIONS : HealthCreateBottomSheetTypes(4)
//    data object HEALTHTARGETS : HealthCreateBottomSheetTypes(5)
//    data object ADDICTION : HealthCreateBottomSheetTypes(6)
//}