package fit.asta.health.profile

sealed class MultiRadioBtnKeys(val key: String, val listName: String) {
    object GENDER : MultiRadioBtnKeys("gen", "Gender")
    object ISPREG : MultiRadioBtnKeys("prg", "Pregnant")
    object ISONPERIOD : MultiRadioBtnKeys("prd", "Period")
    object HEALTHHIS : MultiRadioBtnKeys("hh", "Health History")
    object INJURIES : MultiRadioBtnKeys("inj", "Injury")
    object BODYPART : MultiRadioBtnKeys("bp", "Body Part")
    object AILMENTS : MultiRadioBtnKeys("ail", "Ailments")
    object MEDICATIONS : MultiRadioBtnKeys("med", "Medication")
    object HEALTHTAR : MultiRadioBtnKeys("htg", "Health Target")
    object ADDICTION : MultiRadioBtnKeys("add", "Addictions")
    object PHYACTIVE : MultiRadioBtnKeys("act", "Physical Active")
    object WORKINGENV : MultiRadioBtnKeys("we", "Working Environment")
    object WORKINGSTYLE : MultiRadioBtnKeys("ws", "Working Style")
    object WORKINGHRS : MultiRadioBtnKeys("whr", "Working Hour")
    object DIETREST : MultiRadioBtnKeys("drs", "Diet Restrictions")
}