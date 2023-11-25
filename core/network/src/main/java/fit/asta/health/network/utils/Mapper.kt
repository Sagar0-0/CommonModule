package fit.asta.health.network.utils

import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.Value

fun NetSheetData.toValue(): Value {
    return Value(
        dsc = this.dsc, id = "", ttl = this.name, url = this.url ?: "", code = this.code
    )
}