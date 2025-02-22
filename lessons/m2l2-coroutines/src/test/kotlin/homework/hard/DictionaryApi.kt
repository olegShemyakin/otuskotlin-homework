package ru.otus.otuskotlin.coroutines.homework.hard

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.Response
import ru.otus.otuskotlin.coroutines.homework.hard.dto.Dictionary

class DictionaryApi(
    private val objectMapper: ObjectMapper = jacksonObjectMapper()
) {

    fun findWord(locale: Locale, word: String): Dictionary? { // make something with context
        val url = "$DICTIONARY_API/${locale.code}/$word"
        println("Searching $url")
        return getBody(HttpClient.get(url))?.firstOrNull()
    }


    private fun getBody(response: Response): List<Dictionary>? {
        if (response.code == 429) {
            println("Exception: ${response.code}")
        }
        if (!response.isSuccessful) {
            return emptyList()
        }

        return response.body?.use {
            objectMapper.readValue(it.string())
        }
    }
}
