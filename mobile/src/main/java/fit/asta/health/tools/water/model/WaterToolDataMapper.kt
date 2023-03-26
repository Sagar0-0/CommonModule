package fit.asta.health.tools.water.model

import android.util.Log
import fit.asta.health.tools.water.model.domain.BeverageDetails
import fit.asta.health.tools.water.model.domain.WaterTool
import fit.asta.health.tools.water.model.network.NetWaterToolRes
import fit.asta.health.tools.water.model.network.UserBeverageData

class WaterToolDataMapper {

    fun mapToDomainModel(networkModel: NetWaterToolRes): WaterTool {
        Log.i("WaterToolDataMapper 14", networkModel.toString())
        val list = mutableListOf<BeverageDetails>()
        val isSelectedList = mutableListOf<String>()

        //if(networkModel.waterTool.userBeverageInfo.userBeverageList!=null) {
        networkModel.waterTool.userBeverageInfo.userBeverageList.forEach{
                isSelectedList.add(it.code)
            }

            networkModel.waterTool.allBeveragesList.forEach {
                list.add(
                    BeverageDetails(
                        beverageId = it.id,
                        title = it.title,
                        name = it.name,
                        rank = getRank(
                            networkModel.waterTool.userBeverageInfo.userBeverageList,
                            it.id
                        ),
                        unit = getUnit(
                            networkModel.waterTool.userBeverageInfo.userBeverageList,
                            it.id
                        ),
                        containers = it.containers as MutableList<Int>,
                        qty = getQty(
                            networkModel.waterTool.userBeverageInfo.userBeverageList,
                            it.id,
                            it.containers[0]
                        ),
                        icon = it.icon,
                        code = it.code,
                        isSelected = getIsSelected(
                            networkModel.waterTool.userBeverageInfo.userBeverageList,
                            it.id
                        )
                    )
                )
            }
//        }else{
//            networkModel.waterTool.allBeveragesList.forEach {
//                healthHisList.add(
//                    BeverageDetails(
//                        beverageId = it.id,
//                        title = it.title,
//                        name = it.name,
//                        rank = getRank(
//                            listOf(),
//                            it.id
//                        ),
//                        unit = getUnit(
//                            listOf(),
//                            it.id
//                        ),
//                        containers = it.containers,
//                        qty = getQty(
//                            listOf(),
//                            it.id,
//                            it.containers[0]
//                        ),
//                        icon = it.icon,
//                        code = it.code,
//                        isSelected = getIsSelected(
//                            listOf(),
//                            it.id
//                        )
//                    )
//                )
//            }
//        }

        val f =WaterTool(
            waterToolData = networkModel.waterTool.waterToolData,
            progressData = networkModel.waterTool.progressData,
            beveragesDetails = list,
            selectedListId = isSelectedList
        )
        Log.i("WaterToolDataMapper 44", f.toString())
        return f
    }

    fun mapToNetworkModel(domainModel: WaterTool): NetWaterToolRes {
        TODO("Not yet implemented")
    }

    fun getRank(userList: List<UserBeverageData> ,beverageId: String ): Int{
        userList.forEach{
            if(it.beverageId==beverageId){
                return it.rank
            }
        }
        return 0
    }

    fun getUnit(userList: List<UserBeverageData> ,beverageId: String ): String{
        userList.forEach{
            if(it.beverageId==beverageId){
                return it.unit
            }
        }
        return "ml"
    }

    fun getQty(userList: List<UserBeverageData> ,beverageId: String,defaultValue: Int ): Int{
        userList.forEach{
            if(it.beverageId==beverageId){
                return it.qty
            }
        }
        return defaultValue
    }

    fun getIsSelected(userList: List<UserBeverageData> ,beverageId: String): Boolean{
        userList.forEach{
            if(it.beverageId==beverageId){
                return true
            }
        }
        return false
    }
}