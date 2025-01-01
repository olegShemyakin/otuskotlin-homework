package dsl

class SqlSelectBuilder {

    private val columns: MutableList<String> = mutableListOf()
    private lateinit var table: String
    private var condition: Condition? = null

    fun select(vararg columns: String) {
        if (columns.isEmpty()) throw IllegalArgumentException("Необходимо определить хотя бы один столбец")

        this.columns.addAll(columns)
    }

    fun from(table: String) {
        this.table = table
    }

    fun where(init: Condition.() -> Unit) {
        condition = And().apply(init)
    }

    fun build(): String {
        if (!::table.isInitialized) throw IllegalStateException("Таблица не была определена")
        val columnsSelect: String =
            if (columns.isEmpty()) {
                "*"
            } else {
                columns.joinToString(", ")
            }

        val conditionStr =
            if (condition == null) {
                ""
            } else {
                " where $condition"
            }

        return "select $columnsSelect from $table$conditionStr"
    }
}