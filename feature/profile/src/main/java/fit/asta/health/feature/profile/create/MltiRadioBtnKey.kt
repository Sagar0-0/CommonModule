package fit.asta.health.feature.profile.create

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fit.asta.health.resources.strings.R

sealed class MultiRadioBtnKeys(val key: String, private val listName: Int) {

    @Composable
    fun getListName(): String = stringResource(id = listName)

    object GENDER : MultiRadioBtnKeys("gen", R.string.gender_profile_creation)
    object ISPREG : MultiRadioBtnKeys("prg", R.string.pregnant_profile_creation)
    object ISONPERIOD : MultiRadioBtnKeys("prd", (R.string.period_profile_creation))
    object HEALTHHIS : MultiRadioBtnKeys("hh", (R.string.healthHis_profile_creation))
    object INJURIES : MultiRadioBtnKeys("inj", (R.string.injury_profile_creation))
    object BODYPART : MultiRadioBtnKeys("bp", R.string.bodyPart_profile_creation)
    object AILMENTS : MultiRadioBtnKeys("ail", (R.string.ailments_profile_creation))
    object MEDICATIONS : MultiRadioBtnKeys("med", (R.string.medication_profile_creation))
    object HEALTHTAR : MultiRadioBtnKeys("htg", (R.string.healthTarget_profile_creation))
    object ADDICTION : MultiRadioBtnKeys("add", (R.string.addictions_profile_creation))
    object PHYACTIVE : MultiRadioBtnKeys("act", (R.string.physicalActive_profile_creation))
    object WORKINGENV : MultiRadioBtnKeys("we", (R.string.workingEnv_profile_creation))
    object WORKINGSTYLE : MultiRadioBtnKeys("ws", (R.string.workingStyle_profile_creation))
    object WORKINGHRS : MultiRadioBtnKeys("whr", (R.string.workingHour_profile_creation))
    object DIETREST : MultiRadioBtnKeys("drs", (R.string.dietRes_profile_creation))
}