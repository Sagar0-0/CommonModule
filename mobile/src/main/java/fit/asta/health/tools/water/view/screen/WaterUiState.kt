package fit.asta.health.tools.water.view.screen

import fit.asta.health.tools.water.model.network.ButterMilk
import fit.asta.health.tools.water.model.network.Coconut
import fit.asta.health.tools.water.model.network.FruitJuice
import fit.asta.health.tools.water.model.network.Meta
import fit.asta.health.tools.water.model.network.Milk
import fit.asta.health.tools.water.model.network.Water

data class WaterUiState(
    val target: Float = 0f,
    val dialogString: String = "",
    val targetAngle: Float = 0f,
    val angle: Float = 0f,
    val start: Boolean = false,
    val showCustomDialog: Boolean = false,
    val butterMilk: ButterMilk=ButterMilk(),
    val coconut: Coconut=Coconut(),
    val fruitJuice: FruitJuice=FruitJuice(),
    val milk: Milk=Milk(),
    val water: Water=Water(),
    val meta: Meta=Meta(),
)
