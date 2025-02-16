package dsl

fun query(init: SqlSelectBuilder.() -> Unit): SqlSelectBuilder {
    return SqlSelectBuilder().apply(init)
}

abstract class Condition {

    fun and(init: Condition.() -> Unit) {
        addCondition(And().apply(init))
    }

    fun or(init: Condition.() -> Unit) {
        addCondition(Or().apply(init))
    }

    infix fun String.eq(value: Any?) {
        addCondition(Eq(this, value))
    }

    infix fun String.nonEq(value: Any?) {
        addCondition(NonEq(this, value))
    }

    protected abstract fun addCondition(condition: Condition)
}

open class CompositeCondition(private val sqlOperator: String) : Condition() {
    private val conditions: MutableList<Condition> = mutableListOf()

    override fun addCondition(condition: Condition) {
        conditions += condition
    }

    override fun toString(): String {
        return if (conditions.size == 1) {
            conditions.first().toString()
        } else {
            conditions.joinToString(prefix = "(", postfix = ")", separator = " $sqlOperator ") {
                "$it"
            }
        }
    }
}

class And: CompositeCondition("and")

class Or : CompositeCondition("or")

class Eq(private val column: String, private val value: Any?) : Condition() {
    override fun addCondition(condition: Condition) {
        throw IllegalStateException("Невозможно добавить условие")
    }

    override fun toString(): String {
        return when (value) {
            null -> "$column is null"
            is String -> "$column = '$value'"
            else -> "$column = $value"
        }
    }
}

class NonEq(private val column: String, private val value: Any?) : Condition() {
    override fun addCondition(condition: Condition) {
        throw IllegalStateException("Невозможно добавить условие")
    }

    override fun toString(): String {
        return when (value) {
            null -> "$column !is null"
            is String -> "$column != '$value'"
            else -> "$column != $value"
        }
    }
}

