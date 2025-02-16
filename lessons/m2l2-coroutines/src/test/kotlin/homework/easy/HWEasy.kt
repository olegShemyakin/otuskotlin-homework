package homework.easy

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class HWEasy {

    @Test
    fun easyHw() {
        val numbers = generateNumbers()
        val toFind = 10
        val toFindOther = 1000

        runBlocking {
            val res1 = async {
                findNumberInList(toFind, numbers)
            }
            val  res2 = async {
                findNumberInList(toFindOther, numbers)
            }

            val foundNumbers = listOf(
                res1.await(),
                res2.await()
            )

            foundNumbers.forEach {
                if (it != -1) {
                    println("Your number $it found!")
                } else {
                    println("Not found number $toFind || $toFindOther")
                }
            }
        }
    }
}