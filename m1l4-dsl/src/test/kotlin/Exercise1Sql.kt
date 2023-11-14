@file:Suppress("unused")

package ru.otus.otuskotlin.marketplace.m1l4

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

// Реализуйте dsl для составления sql запроса, чтобы все тесты стали зелеными.
class Exercise1Sql {
    private fun checkSQL(expected: String, sql: SqlSelectBuilder) {
        assertEquals(expected, sql.build())
    }

    @Test
    fun `simple select all from table`() {
        val expected = "select * from table"

        val real = query {
            from("table")
        }

        checkSQL(expected, real)
    }

    @Test
    fun `check that select can't be used without table`() {
        assertFailsWith<Exception> {
            query {
                select("col_a")
            }.build()
        }
    }

    @Test
    fun `select certain columns from table`() {
        val expected = "select col_a, col_b from table"

        val real = query {
            select("col_a", "col_b")
            from("table")
        }

        checkSQL(expected, real)
    }

    @Test
    fun `select certain columns from table 1`() {
        val expected = "select col_a, col_b from table"

        val real = query {
            select("col_a", "col_b")
            from("table")
        }

        checkSQL(expected, real)
    }

    /**
     * __eq__ is "equals" function. Must be one of char:
     *  - for strings - "="
     *  - for numbers - "="
     *  - for null - "is"
     */
    @Test
    fun `select with complex where condition with one condition`() {
        val expected = "select * from table where col_a = 'id'"

        val real = query {
            from("table")
            where { "col_a" eq "id" }
        }

        checkSQL(expected, real)
    }

    /**
     * __nonEq__ is "non equals" function. Must be one of chars:
     *  - for strings - "!="
     *  - for numbers - "!="
     *  - for null - "!is"
     */
    @Test
    fun `select with complex where condition with two conditions`() {
        val expected = "select * from table where col_a != 0"

        val real = query {
            from("table")
            where {
                "col_a" nonEq 0
            }
        }

        checkSQL(expected, real)
    }

    @Test
    fun `when 'or' conditions are specified then they are respected`() {
        val expected = "select * from table where (col_a = 4 or col_b !is null)"

        val real = query {
            from("table")
            where {
                or {
                    "col_a" eq 4
                    "col_b" nonEq null
                }
            }
        }

        checkSQL(expected, real)
    }
}


private fun query(function: SqlSelectBuilder.() -> Unit): SqlSelectBuilder = SqlSelectBuilder().apply(function)

// "select * from table where (col_a = 4 or col_b !is null)"

class SqlSelectBuilder(
    private var _table: String = "",
    private var _column: String = "*",
    private var where: String = ""
) {

    fun where(block: WhereDsl.() -> Unit) {
        val names = WhereDsl().apply(block)
        where = names.where
        println("where from WhereDsl ${names.where} ")
    }

    fun build(): String {
        // if (_table.isEmpty()) throw Exception("select can't be used without table")
        require(_table.isNotEmpty()) {
            "Select can't be used without table"
        }
        return "select $_column from $_table$where"
    }

    fun from(tb: String) {
        _table = tb
    }

    fun select(s: String) {
        _column = s
    }

    fun select(s: String, s1: String) {
        _column = "$s, $s1"
    }
}

class WhereDsl(

    var _eqStr: String = "",
    var _nonEqStr: String = ""
) {
    val where: String
        get() = when {
            _eqStr.isNotEmpty() && _nonEqStr.isEmpty() -> " where $_eqStr"
            _eqStr.isEmpty() && _nonEqStr.isNotEmpty() -> " where $_nonEqStr"
            _eqStr.isNotEmpty() && _nonEqStr.isNotEmpty() -> " where ($_eqStr or $_nonEqStr)"
            else -> ""
        }

    fun or(block: OrDsl.() -> Unit) {
        val names = OrDsl().apply(block)
        println("_where in WhereDsl $where , eq = $_eqStr, non = $_nonEqStr")
    }
    infix fun String.eq(value: Any?) {
        _eqStr = when (value) {
            is String -> "$this = '$value'"
            is Int -> "$this = $value"
            null -> "$this is null"
            else -> ""
        }
        println("_eqStr $_eqStr")
        println("_nonStr $_nonEqStr")
    }

    infix fun String.nonEq(value: Any?) {
        _nonEqStr = when (value) {
            is String -> "$this != '$value'"
            is Int -> "$this != $value"
            null -> "$this !is null"
            else -> ""
        }
        println("_eqStr $_eqStr")
        println("_nonStr $_nonEqStr")
    }
}

//а если будет and?
class OrDsl(

) {

}
