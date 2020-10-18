// DONT_TARGET_EXACT_BACKEND: WASM
// WASM_MUTE_REASON: STDLIB_COLLECTIONS
// KJS_WITH_FULL_RUNTIME
// Auto-generated by org.jetbrains.kotlin.generators.tests.GenerateRangesCodegenTestData. DO NOT EDIT!
// WITH_RUNTIME



fun box(): String {
    val list1 = ArrayList<UInt>()
    for (i in (8u downTo 3u step 2).reversed()) {
        list1.add(i)
        if (list1.size > 23) break
    }
    if (list1 != listOf<UInt>(4u, 6u, 8u)) {
        return "Wrong elements for (8u downTo 3u step 2).reversed(): $list1"
    }

    val list2 = ArrayList<UInt>()
    for (i in (8u.toUByte() downTo 3u.toUByte() step 2).reversed()) {
        list2.add(i)
        if (list2.size > 23) break
    }
    if (list2 != listOf<UInt>(4u, 6u, 8u)) {
        return "Wrong elements for (8u.toUByte() downTo 3u.toUByte() step 2).reversed(): $list2"
    }

    val list3 = ArrayList<UInt>()
    for (i in (8u.toUShort() downTo 3u.toUShort() step 2).reversed()) {
        list3.add(i)
        if (list3.size > 23) break
    }
    if (list3 != listOf<UInt>(4u, 6u, 8u)) {
        return "Wrong elements for (8u.toUShort() downTo 3u.toUShort() step 2).reversed(): $list3"
    }

    val list4 = ArrayList<ULong>()
    for (i in (8uL downTo 3uL step 2L).reversed()) {
        list4.add(i)
        if (list4.size > 23) break
    }
    if (list4 != listOf<ULong>(4u, 6u, 8u)) {
        return "Wrong elements for (8uL downTo 3uL step 2L).reversed(): $list4"
    }

    return "OK"
}
