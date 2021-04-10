package com.giant_giraffe.utils.exposed

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager

/**
 * Edited from: https://github.com/LorittaBot/Loritta/blob/development/loritta-discord/src/main/java/com/mrpowergamerbr/loritta/utils/exposed/array.kt
 * license: [AGPL-3.0 License](https://github.com/LorittaBot/Loritta/blob/development/LICENSE)
 *
 * Permissions:
 *  - Commercial use
 *  - Modification
 *  - Distribution
 *  - Patent use
 *  - Private use
 *
 * Limitations:
 *  - Liability
 *  - Warranty
 */

fun <T> Table.array(name: String, columnType: ColumnType): Column<Array<T>> = registerColumn(name, ArrayColumnType(columnType))

class ArrayColumnType(private val type: ColumnType) : ColumnType() {

    private fun supportsArrays() = true // !loritta.config.database.type.startsWith("SQLite")

    override fun sqlType(): String = buildString {
        if (!supportsArrays()) {
            append("TEXT")
        } else {
            append(type.sqlType())
            append(" ARRAY")
        }
    }

    override fun valueToDB(value: Any?): Any? {
        if (!supportsArrays())
            return "'NOT SUPPORTED'"
        if (value is Array<*>) {
            val columnType = type.sqlType().split("(")[0]
            val jdbcConnection = TransactionManager.current().connection
            return jdbcConnection.createArrayOf(columnType, value)
        } else {
            return super.valueToDB(value)
        }
    }
    override fun valueFromDB(value: Any): Any {
        if (!supportsArrays()) {
            val clazz = type::class
            val clazzName = clazz.simpleName
            if (clazzName == "LongColumnType")
                return arrayOf<Long>()
            if (clazzName == "TextColumnType")
                return arrayOf<String>()
            error("Unsupported Column Type")
        }
        if (value is java.sql.Array) {
            return value.array
        }
        if (value is Array<*>) {
            return value
        }
        error("Array does not support for this database")
    }
    override fun notNullValueToDB(value: Any): Any {
        if (!supportsArrays())
            return "'NOT SUPPORTED'"
        if (value is Array<*>) {
            if (value.isEmpty())
                return "'{}'"
            val columnType = type.sqlType().split("(")[0]
            val jdbcConnection = TransactionManager.current().connection
            return jdbcConnection.createArrayOf(columnType, value) ?: error("Can't create non null array for $value")
        } else {
            return super.notNullValueToDB(value)
        }
    }
}

class AnyOp(val expr1: Expression<*>, val expr2: Expression<*>) : Op<Boolean>() {
    override fun toSQL(queryBuilder: QueryBuilder): String = buildString {
        if (expr2 is OrOp) {
            append("(").append(expr2).append(")")
        } else {
            append(expr2)
        }
        append(" = ANY (")
        if (expr1 is OrOp) {
            append("(").append(expr1).append(")")
        } else {
            append(expr1)
        }
        append(")")
    }
}

class ContainsOp(expr1: Expression<*>, expr2: Expression<*>) : ComparisonOp(expr1, expr2, "@>")
infix fun<T, S> ExpressionWithColumnType<T>.any(t: S) : Op<Boolean> {
    if (t == null) {
        return IsNullOp(this)
    }
    return AnyOp(this, QueryParameter(t, columnType))
}
infix fun<T, S> ExpressionWithColumnType<T>.contains(arry: Array<in S>) : Op<Boolean> = ContainsOp(this, QueryParameter(arry, columnType))