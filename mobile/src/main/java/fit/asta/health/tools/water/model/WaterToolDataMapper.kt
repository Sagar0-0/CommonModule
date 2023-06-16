package fit.asta.health.tools.water.model

import android.util.Log
import fit.asta.health.tools.water.model.domain.BeverageDetails
import fit.asta.health.tools.water.model.domain.WaterTool
import fit.asta.health.tools.water.model.network.TodayActivityData
import fit.asta.health.tools.water.model.network.WaterToolResult

class WaterToolDataMapper {

    fun mapToDomainModel(networkModel: WaterToolResult): WaterTool {
        Log.i("WaterToolDataMapper 14", networkModel.toString())
        val beverageDetailsList = mutableListOf<BeverageDetails>()
        val todayActivityList = mutableListOf<TodayActivityData>()
        networkModel.data.userBeverageInfo.bev.forEach{
            beverageDetailsList.add(
                BeverageDetails(
                    beverageId = it.id,
                    title = it.title,
                    name = it.name,
                    rank =it.rank ,
                    unit =it.unit ,
                    containers = it.containerList as MutableList<Int>,
                    qty = it.qty,
                    icon = it.icon,
                    code = it.code
                )
            )
        }

        networkModel.data.todayActivityData?.let{todayActivityList.addAll(it)}

        return WaterTool(
            waterToolData = networkModel.data.waterToolData,
            uid =networkModel.data.waterToolData.uid ,
            butterMilk = networkModel.data.progressData.butterMilk,
            coconut = networkModel.data.progressData.coconut,
            fruitJuice =networkModel.data.progressData.fruitJuice ,
            milk = networkModel.data.progressData.milk,
            water =networkModel.data.progressData.water ,
            meta = networkModel.data.progressData.meta,
            time =networkModel.data.progressData.time ,
            todayActivityData = todayActivityList,
            beveragesDetails =beverageDetailsList
        )
    }
}