package fit.asta.health.data.water.model

import android.util.Log
import fit.asta.health.data.water.model.domain.BeverageDetails
import fit.asta.health.data.water.model.domain.BeverageDetailsData
import fit.asta.health.data.water.model.domain.WaterTool
import fit.asta.health.data.water.model.network.TodayActivityData
import fit.asta.health.data.water.model.network.WaterToolResult

class WaterToolDataMapper {

    fun mapToDomainModel(networkModel: WaterToolResult): WaterTool {
        Log.i("WaterToolDataMapper 14", networkModel.toString())
        val beverageDetailsList = mutableListOf<BeverageDetailsData>()
        val todayActivityList = mutableListOf<TodayActivityData>()
        networkModel.data.userBeverageInfo.bev.forEach{
            beverageDetailsList.add(
                BeverageDetailsData(
                    beverageId = it.id,
                    title = it.title,
                    name = it.name,
                    rank =it.rank ,
                    unit =it.unit ,
                    count = it.count,
                    icon = it.icon,
                    code = it.code
                )
            )
            beverageDetailsList.sortBy { it.rank }
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