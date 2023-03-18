/*
 * Copyright (c) 2014-2022 Stream.io Inc. All rights reserved.
 *
 * Licensed under the Stream License;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://github.com/GetStream/stream-chat-android/blob/main/LICENSE
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fit.asta.health.common.utils


/**
 *  A class which encapsulates a successful outcome with a value of type [T] or a failure with [ChatError].
 */
class Result<T : Any> private constructor(
    private val data: T?,
    private val error: ChatError?,
) {

    @Suppress("DEPRECATION")
    constructor(data: T) : this(data, null)

    @Suppress("DEPRECATION")
    constructor(error: ChatError) : this(null, error)

    /**
     * Returns true if a request of payload response has been successful.
     */
    val isSuccess: Boolean
        get() = data != null

    /**
     * Returns true if a request of payload response has been failed.
     */
    val isError: Boolean
        get() = error != null

    /**
     * Returns the successful data payload.
     */
    fun data(): T {
        return checkNotNull(data) { "Result is not successful. Check result.isSuccess before reading the data." }
    }

    /**
     * Returns the [ChatError] error payload.
     */
    fun error(): ChatError {
        return checkNotNull(error) {
            "Result is successful, not an error. Check result.isSuccess before reading the error."
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Result<*>

        if (data != other.data) return false
        if (error != other.error) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data?.hashCode() ?: 0
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Result(data=$data, error=$error)"
    }

    companion object {

        /**
         * Creates a [Result] object with [data] payload.
         *
         * @param data successful data payload.
         *
         * @return [Result] of [T] that contains successful data payload.
         */
        @JvmStatic
        fun <T : Any> success(data: T): Result<T> {
            return Result(data)
        }

        /**
         * Creates a [Result] object with error payload.
         *
         * @param t Unexpected [Exception] or [Throwable].
         *
         * @return [Result] of [T] that contains [ChatError] error payload.
         */
        @JvmStatic
        fun <T : Any> error(t: Throwable): Result<T> {
            return Result(null, ChatError(t.message, t))
        }

        /**
         * Creates a [Result] object with error payload.
         *
         * @param error [ChatError] error payload.
         *
         * @return [Result] of [T] that contains [ChatError] error payload.
         */
        @JvmStatic
        fun <T : Any> error(error: ChatError): Result<T> {
            return Result(null, error)
        }
    }
}

/**
 * Returns a [Result] of [Unit] from any type of a [Result].
 *
 * @return [Result] of [Unit].
 */
/*@InternalStreamChatApi
public fun Result<*>.toUnitResult(): Result<Unit> = map {}*/

/**
 * Runs the [successSideEffect] lambda function if the [Result] contains a successful data payload.
 *
 * @param successSideEffect A lambda that receives the successful data payload.
 *
 * @return The original instance of the [Result].
 */
@JvmSynthetic
inline fun <T : Any> Result<T>.onSuccess(
    crossinline successSideEffect: (T) -> Unit,
): Result<T> = apply {
    if (isSuccess) {
        successSideEffect(data())
    }
}

/**
 * Runs the suspending [successSideEffect] lambda function if the [Result] contains a successful data payload.
 *
 * @param successSideEffect A suspending lambda that receives the successful data payload.
 *
 * @return The original instance of the [Result].
 */
@JvmSynthetic
suspend inline fun <T : Any> Result<T>.onSuccessSuspend(
    crossinline successSideEffect: suspend (T) -> Unit,
): Result<T> = apply {
    if (isSuccess) {
        successSideEffect(data())
    }
}

/**
 * Returns a transformed [Result] of applying the given [mapper] function if the [Result]
 * contains a successful data payload.
 * Returns an original [Result] if the [Result] contains an error payload.
 *
 * @param mapper A lambda for mapping [Result] of [T] to [Result] of [K].
 *
 * @return A transformed instance of the [Result] or the original instance of the [Result].
 */
@JvmSynthetic
fun <T : Any, K : Any> Result<T>.map(mapper: (T) -> K): Result<K> {
    return if (isSuccess) {
        Result(mapper(data()))
    } else {
        Result(error())
    }
}

/**
 * Returns a transformed [Result] of applying the given suspending [mapper] function if the [Result]
 * contains a successful data payload.
 * Returns an original [Result] if the [Result] contains an error payload.
 *
 * @param mapper A suspending lambda for mapping [Result] of [T] to [Result] of [K].
 *
 * @return A transformed instance of the [Result] or the original instance of the [Result].
 */
@JvmSynthetic
suspend fun <T : Any, K : Any> Result<T>.mapSuspend(mapper: suspend (T) -> K): Result<K> {
    return if (isSuccess) {
        Result(mapper(data()))
    } else {
        Result(error())
    }
}

/**
 * Recovers the error payload by applying the given [errorMapper] function if the [Result]
 * contains an error payload.
 *
 * @param errorMapper A lambda that receives [ChatError] and transforms it as a payload [T].
 *
 * @return A transformed instance of the [Result] or the original instance of the [Result].
 */
@JvmSynthetic
fun <T : Any> Result<T>.recover(errorMapper: (ChatError) -> T): Result<T> {
    return if (isSuccess) {
        this
    } else {
        Result(errorMapper(error()))
    }
}

/**
 * Recovers the error payload by applying the given suspending [errorMapper] function if the [Result]
 * contains an error payload.
 *
 * @param errorMapper A suspending lambda that receives [ChatError] and transforms it as a payload [T].
 *
 * @return A transformed instance of the [Result] or the original instance of the [Result].
 */
@JvmSynthetic
suspend fun <T : Any> Result<T>.recoverSuspend(errorMapper: suspend (ChatError) -> T): Result<T> {
    return if (isSuccess) {
        this
    } else {
        Result(errorMapper(error()))
    }
}

/**
 * Runs the [errorSideEffect] lambda function if the [Result] contains an error payload.
 *
 * @param errorSideEffect A lambda that receives the [ChatError] payload.
 *
 * @return The original instance of the [Result].
 */
@JvmSynthetic
inline fun <T : Any> Result<T>.onError(
    crossinline errorSideEffect: (ChatError) -> Unit,
): Result<T> = apply {
    if (isError) {
        errorSideEffect(error())
    }
}

/**
 * Runs the suspending [errorSideEffect] lambda function if the [Result] contains an error payload.
 *
 * @param errorSideEffect A suspending lambda that receives the [ChatError] payload.
 *
 * @return The original instance of the [Result].
 */
@JvmSynthetic
suspend inline fun <T : Any> Result<T>.onErrorSuspend(
    crossinline errorSideEffect: suspend (ChatError) -> Unit,
): Result<T> = apply {
    if (isError) {
        errorSideEffect(error())
    }
}

/**
 * Returns a transformed [Result] from results of the [func] if the [Result] contains a successful data payload.
 * Returns an original [Result] if the [Result] contains an error payload.
 *
 * @param func A lambda that returns [Result] of [R].
 *
 * @return A transformed instance of the [Result] or the original instance of the [Result].
 */
@JvmSynthetic
fun <T : Any, R : Any> Result<T>.flatMap(func: (T) -> Result<R>): Result<R> {
    return if (isSuccess) {
        func(data())
    } else {
        Result.error(error())
    }
}

/**
 * Returns a transformed [Result] from results of the suspending [func] if the [Result] contains a successful data
 * payload.
 * Returns an original [Result] if the [Result] contains an error payload.
 *
 * @param func A suspending lambda that returns [Result] of [R].
 *
 * @return A transformed instance of the [Result] or the original instance of the [Result].
 */
@JvmSynthetic
suspend fun <T : Any, R : Any> Result<T>.flatMapSuspend(func: suspend (T) -> Result<R>): Result<R> {
    return if (isSuccess) {
        func(data())
    } else {
        Result.error(error())
    }
}

/**
 * Returns a [Result] that contains an instance of [T] as a data payload.
 *
 * @return A [Result] the contains an instance of [T] as a data payload.
 */
fun <T : Any> T.toResult(): Result<T> = Result.success(this)

/**
 * Returns a [Result] of type [T] that contains an they same error as payload.
 *
 * @return A [Result] of type [T] that contains an they same error as payload.
 */
fun <T : Any> ChatError.toResultError(): Result<T> = Result.error(this)
