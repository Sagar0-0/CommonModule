package fit.asta.health.designsystem.molecular.textfield

/**
 * This class contains all the types of the TextField that we can have and it covers all the
 * use cases.
 *
 * @param minStringSize This is the minimum String Size permitted to be entered in the textField
 * @param maxStringSize This is the maximum String Size permitted to be entered in the textField
 *
 *
 * @property Phone for making the user enter Phone Number type of things where the number needs
 * to be 10 digits
 * @property Mail to make sure the entered input from the user would be a Mail id
 * @property Default This is for all the input where the user is restricted from 1 - 256 letters
 * @property Custom This is made to pass your own custom Implementation for the Text input by the
 * user
 */
sealed class AppTextFieldType(
    val minStringSize: Int = 1,
    val maxStringSize: Int = 256
) {
    data object None : AppTextFieldType(0, 0)
    data object Phone : AppTextFieldType(10, 10)
    data object Mail : AppTextFieldType(6, 50)
    data object Default : AppTextFieldType(1, 256)
    data class Custom(
        val minSize: Int = 0,
        val maxSize: Int = 256,
        val isInvalidLogic: ((input: String, isTyping: Boolean) -> Boolean) = { input, isTyping ->
            if (!isTyping)
                false
            else
                input.length < minSize || input.length > maxSize
        },
        val getErrorMessageLogic: ((input: String) -> String) = { input ->
            if (input.isEmpty())
                "It can't be empty"
            else
                "Text should be between $minSize - $maxSize"
        },
        val getStringCounterLogic: ((input: String) -> String) = {
            "${it.length}/$maxSize"
        },
        val isTextValidLogic: ((input: String) -> Boolean) = { input ->
            input.length in minSize..maxSize
        }
    ) : AppTextFieldType(minSize, maxSize)
}