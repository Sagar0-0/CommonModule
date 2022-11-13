package fit.asta.health.common.validation.state

import androidx.annotation.StringRes
import fit.asta.health.common.validation.inerfaces.TextFieldId
import fit.asta.health.common.validation.util.TextFieldType

data class ValidationState(
    var text: String = "",
    val type: TextFieldType = TextFieldType.Text,
    val id: TextFieldId,
    val isRequired: Boolean = true,
    var hasError: Boolean = true,
    @StringRes val errorMessageId: Int? = null,
)
