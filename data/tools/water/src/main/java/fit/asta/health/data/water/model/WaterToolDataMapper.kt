package fit.asta.health.data.water.model

import android.util.Log
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.water.model.domain.BeverageDetailsData
import fit.asta.health.data.water.model.domain.WaterTool
import fit.asta.health.data.water.model.network.Data
import fit.asta.health.data.water.model.network.TodayActivityData
import fit.asta.health.data.water.model.network.WaterToolResult

class WaterToolDataMapper {

    fun mapToWaterTool(networkModel: Data): WaterTool {
        Log.i("WaterToolDataMapper 14", networkModel.toString())
        val beverageDetailsList = mutableListOf<BeverageDetailsData>()
        val todayActivityList = mutableListOf<TodayActivityData>()
        networkModel.userBeverageInfo.bev.forEach {
            beverageDetailsList.add(
                BeverageDetailsData(
                    beverageId = it.id,
                    title = it.title,
                    name = it.name,
                    rank = it.rank,
                    unit = it.unit,
                    count = it.count,
                    icon = it.icon,
                    code = it.code
                )
            )
            beverageDetailsList.sortBy { it.rank }
        }

        networkModel.todayActivityData?.let { todayActivityList.addAll(it) }

        return WaterTool(
            waterToolData = networkModel.waterToolData,
            uid = networkModel.waterToolData.uid,
            butterMilk = networkModel.progressData.butterMilk,
            coconut = networkModel.progressData.coconut,
            fruitJuice = networkModel.progressData.fruitJuice,
            milk = networkModel.progressData.milk,
            water = networkModel.progressData.water,
            meta = networkModel.progressData.meta,
            time = networkModel.progressData.time,
            todayActivityData = todayActivityList,
            beveragesDetails = beverageDetailsList
        )
    }
}