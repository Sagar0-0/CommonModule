package fit.asta.health.feature.water.view.screen

import androidx.compose.runtime.Stable
import fit.asta.health.data.water.remote.model.ButterMilk
import fit.asta.health.data.water.remote.model.Coconut
import fit.asta.health.data.water.remote.model.FruitJuice
import fit.asta.health.data.water.remote.model.Meta
import fit.asta.health.data.water.remote.model.Milk
import fit.asta.health.data.water.remote.model.Water

data class WaterUiState(
    val target: Float = 0f,
    val dialogString: String = "",
    val targetAngle: Float = 0f,
    val angle: Float = 0f,
    val start: Boolean = false,
    val showCustomDialog: Boolean = false,
    val butterMilk: ButterMilk = ButterMilk(),
    val coconut: Coconut = Coconut(),
    val fruitJuice: FruitJuice = FruitJuice(),
    val milk: Milk = Milk(),
    val water: Water = Water(),
    val meta: Meta = Meta(),
)

@Stable
data class WaterToolUiState(
    val totalConsumed : Int = 0,
    val remainingToConsume : Int = 0,
    val consumedBevExist : Boolean = true,
    val undoBevQty : Int = 0,
    val recentConsumedBevName : String = "",
    val recentConsumedBevQty : Int = 0,
    val goal : Int = 10000,
    val sliderValueWater : Float = 0f,
    val sliderValueCoconut: Float = 0f,
    val sliderValueFirstPreference: Float = 0f,
    val sliderValueSecondPreference: Float = 0f,
    val sliderValueRecentAdded: Float = 0f
)
