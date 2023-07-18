package fit.asta.health.tools.sunlight.model

import fit.asta.health.tools.sunlight.model.domain.SunlightTool
import fit.asta.health.tools.sunlight.model.network.response.ResponseData

class SunlightToolDataMapper {

    fun mapToDomainModel(networkModel: ResponseData): SunlightTool {
        val sunlightData = networkModel.data.sunlightTool
        val sunSlotData = networkModel.data.sunSlotData
        val sunlightProgressData = networkModel.data.sunlightProgressData

        return SunlightTool(
            id = sunlightData.id,
            uid = sunlightData.uid,
            code = sunlightData.code,
//            sunscreenValue = sunlightData.prc[0].values[0].name,
//            sunscreenTitle = sunlightData.prc[0].ttl,
//            skincolorValue = sunlightData.prc[2].values[0].name,
//            skincolorTitle = sunlightData.prc[2].ttl,
//            skinexposureValue = sunlightData.prc[1].values[0].name,
//            skinexposureTitle = sunlightData.prc[1].ttl,
//            ageValue = sunlightData.prc[5].values[0].name,
//            ageTitle = sunlightData.prc[5].ttl,
            latitude = sunSlotData.latitude.toString(),
            longitude = sunSlotData.longitude.toString(),
            location = sunSlotData.location,
            date = sunSlotData.date,
            generationTimeMs = sunSlotData.generationTimeMs,
            utcOffsetSeconds = sunSlotData.utcOffsetSeconds,
            elevation = sunSlotData.elevation,
            hourlyUnits = SunlightTool.HourlyUnits(
                time = sunSlotData.hourlyUnits.time,
                temperature_2m = sunSlotData.hourlyUnits.temperature2m,
                weathercode = sunSlotData.hourlyUnits.weathercode
            ),
            time = sunSlotData.hourly.time,
            temperature2m = sunSlotData.hourly.temperature2m,
            weathercode = sunSlotData.hourly.weathercode,
            spdid = sunlightProgressData.spdid,
            spduid = sunlightProgressData.spduid,
            tgt = sunlightProgressData.tgt,
            ach = sunlightProgressData.ach,
            rem = sunlightProgressData.rem,
            rcm = sunlightProgressData.rcm,
            rcmIu = sunlightProgressData.rcmIu,
            achIu = sunlightProgressData.achIu,
            metIu = sunlightProgressData.metIu
        )
    }

    fun mapToNetworkModel(domainModel: SunlightTool): ResponseData {
        TODO("Not yet implemented")
    }
}