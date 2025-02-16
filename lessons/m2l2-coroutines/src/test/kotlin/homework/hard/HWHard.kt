package ru.otus.otuskotlin.coroutines.homework.hard

import kotlinx.coroutines.*
import ru.otus.otuskotlin.coroutines.homework.hard.dto.Dictionary
import java.io.File
import kotlin.test.Test

class HWHard {
    @Test
    fun hardHw() {
        val dictionaryApi = DictionaryApi()
        val words = FileReader.readFile().split(" ", "\n").toSet()

        val dictionaries = findWords(dictionaryApi, words, Locale.EN)

        dictionaries.map { dictionary ->
            print("For word ${dictionary.word} i found examples: ")
            println(
                dictionary.meanings
                    .mapNotNull { definition ->
                        val r = definition.definitions
                            .mapNotNull { it.example.takeIf { it?.isNotBlank() == true } }
                            .takeIf { it.isNotEmpty() }
                        r
                    }
                    .takeIf { it.isNotEmpty() }
            )
        }
    }

    private fun findWords(
        dictionaryApi: DictionaryApi,
        words: Set<String>,
        @Suppress("SameParameterValue") locale: Locale
    ): List<Dictionary> {
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.IO + job)

        return runBlocking {
            words.map {
                scope.async {
                    dictionaryApi.findWord(locale, it)
                }
            }.mapNotNull {
                try {
                    it.await()
                } catch (e: Exception) {
                    println("Exception: $e")
                    null
                }
            }
        }
    }

    /*private fun findWords(
        dictionaryApi: DictionaryApi,
        words: Set<String>,
        @Suppress("SameParameterValue") locale: Locale
    ) =

        runBlocking(Dispatchers.IO) {
            words.map {
                async {
                    dictionaryApi.findWord(locale, it)
                }
            }
                .map {
                    it.await()
                }
        }*/
        // make some suspensions and async

    object FileReader {
        fun readFile(): String =
            File(
                this::class.java.classLoader.getResource("words.txt")?.toURI()
                    ?: throw RuntimeException("Can't read file")
            ).readText()
    }
}
