package fit.asta.health.designsystem.validation.state

import androidx.annotation.StringRes
import fit.asta.health.designsystem.validation.inerfaces.TextFieldId
import fit.asta.health.designsystem.validation.util.TextFieldType

data class ValidationState(
    var text: String = "",
    val type: TextFieldType = TextFieldType.Text,
    val id: TextFieldId,
    val isRequired: Boolean = true,
    var hasError: Boolean = true,
    @StringRes val errorMessageId: Int? = null,
)
