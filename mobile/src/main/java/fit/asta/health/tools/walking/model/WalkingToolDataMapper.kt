package fit.asta.health.tools.walking.model

import fit.asta.health.tools.walking.model.domain.WalkingTool
import fit.asta.health.tools.walking.model.network.response.HomeData

fun HomeData.mapToDomain(): WalkingTool {
    return WalkingTool(

        distanceRecommend = this.data.walkingRecommendation.recommend.distance.distance.toDouble(),
        durationRecommend = this.data.walkingRecommendation.recommend.duration.duration,
        stepsRecommend = this.data.walkingRecommendation.recommend.steps.steps,

        code = this.data.walkingTool.code,
        id = this.data.walkingTool.id,
        name = this.data.walkingTool.name,
        sType = this.data.walkingTool.sType,
        distanceTarget = this.data.walkingTool.target.distance.distance.toDouble(),
        durationTarget = this.data.walkingTool.target.duration.duration,
        stepsTarget = this.data.walkingTool.target.steps.steps,
        uid = this.data.walkingTool.uid,
        titleMode = this.data.walkingTool.prc[0].ttl,
        valuesMode = this.data.walkingTool.prc[0].values[0].name,
        titleMusic = this.data.walkingTool.prc[1].values[0].name,
        valuesMusic = this.data.walkingTool.prc[1].values[0].name,
        titleGoal = this.data.walkingTool.prc[2].ttl,
        valuesGoal = this.data.walkingTool.prc[2].values.map { it.name },
        titleType = this.data.walkingTool.prc[3].ttl,
        valuesType = this.data.walkingTool.prc[3].values[0].name
    )
}
