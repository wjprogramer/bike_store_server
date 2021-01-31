package com.giant_giraffe.core

import kotlin.reflect.KProperty

/**
 * 指派值僅能一次
 * [ref](https://stackoverflow.com/questions/44315778/assign-variable-only-if-it-is-null)
 *
 * Usage:
 *
 * ```kotlin
 * var value by Once<String>()
 *
 * fun main(args: Array<String>) {
 *     println(value) // 'null'
 *     value = "1"
 *     println(value) // '1'
 *     value = "2"
 *     println(value) // '1'
 * }
 * ```
 *
 * See also:
 *
 * - [Delegated property](https://kotlinlang.org/docs/reference/delegated-properties.html)
 */
class Once<T> {

    private var value: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        this.value = this.value ?: value
    }

}