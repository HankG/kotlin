// DONT_TARGET_EXACT_BACKEND: WASM
// WASM_MUTE_REASON: EXCEPTIONS_NOT_IMPLEMENTED
// WITH_RUNTIME

import kotlin.UninitializedPropertyAccessException

fun box(): String {
    lateinit var str: String
    var str2: String = ""
    try {
        str2 = str
        return "Should throw an exception"
    }
    catch (e: UninitializedPropertyAccessException) {
        return "OK"
    }
    catch (e: Throwable) {
        return "Unexpected exception: ${e::class}"
    }
}
