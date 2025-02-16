import kotlin.test.Test
import kotlin.test.assertEquals

/*
* Реализовать функцию, которая преобразует список словарей строк в ФИО
* Функцию сделать с использованием разных функций для разного числа составляющих имени
* Итого, должно получиться 4 функции
*
* Для успешного решения задания, требуется раскомментировать тест, тест должен выполняться успешно
* */
class HomeWork1Test {

    @Test
    fun mapListToNamesTest() {
        val input = listOf(
            mapOf(
                "first" to "Иван",
                "middle" to "Васильевич",
                "last" to "Рюрикович",
            ),
            mapOf(
                "first" to "Петька",
            ),
            mapOf(
                "first" to "Сергей",
                "last" to "Королев",
            ),
        )
        val expected = listOf(
            "Рюрикович Иван Васильевич",
            "Петька",
            "Королев Сергей",
        )
        val res = mapListToNames(input)
        assertEquals(expected, res)
    }

    private fun mapListToNames(listNames: List<Map<String, String>>): List<String> {
        return listNames.map {
            when (it.size) {
                3 -> mapToString(it["last"], it["middle"], it["first"])
                2 -> mapToString(it["last"], it["first"])
                1 -> mapToString(it["first"])
                else -> throw IllegalArgumentException("Неверное количество аргументов")
            }
        }
    }

    private fun mapToString(last: String?, middle: String?, first: String?) = "$last $first $middle"

    private fun mapToString(last: String?, first: String?) = "$last $first"

    private fun mapToString(first: String?) = "$first"
}