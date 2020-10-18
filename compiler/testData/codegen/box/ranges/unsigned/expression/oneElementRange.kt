// DONT_TARGET_EXACT_BACKEND: WASM
// WASM_MUTE_REASON: STDLIB_COLLECTIONS
// KJS_WITH_FULL_RUNTIME
// Auto-generated by org.jetbrains.kotlin.generators.tests.GenerateRangesCodegenTestData. DO NOT EDIT!
// WITH_RUNTIME



fun box(): String {
    val list1 = ArrayList<UInt>()
    val range1 = 5u..5u
    for (i in range1) {
        list1.add(i)
        if (list1.size > 23) break
    }
    if (list1 != listOf<UInt>(5u)) {
        return "Wrong elements for 5u..5u: $list1"
    }

    val list2 = ArrayList<UInt>()
    val range2 = 5u.toUByte()..5u.toUByte()
    for (i in range2) {
        list2.add(i)
        if (list2.size > 23) break
    }
    if (list2 != listOf<UInt>(5u)) {
        return "Wrong elements for 5u.toUByte()..5u.toUByte(): $list2"
    }

    val list3 = ArrayList<UInt>()
    val range3 = 5u.toUShort()..5u.toUShort()
    for (i in range3) {
        list3.add(i)
        if (list3.size > 23) break
    }
    if (list3 != listOf<UInt>(5u)) {
        return "Wrong elements for 5u.toUShort()..5u.toUShort(): $list3"
    }

    val list4 = ArrayList<ULong>()
    val range4 = 5uL..5uL
    for (i in range4) {
        list4.add(i)
        if (list4.size > 23) break
    }
    if (list4 != listOf<ULong>(5uL)) {
        return "Wrong elements for 5uL..5uL: $list4"
    }

    return "OK"
}
