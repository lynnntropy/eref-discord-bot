package model

import com.beust.klaxon.JsonObject
import com.beust.klaxon.json

data class NewsItem(
        val author: String,
        val subject: String,
        val title: String,
        val body: String)
{
    fun getJson(): String
    {
        val logic = json {
            obj(Pair("embeds", array(
                    JsonObject(mapOf(
                            "title" to title,
                            "description" to "**Vest** - Postavio/la **$author** u predmet **$subject**\n\n$body"
                    )))))
        }

        return logic.toJsonString()
    }
}

data class ResultItem(
        val author: String,
        val subject: String,
        val title: String,
        val body: String)
{
    fun getJson(): String
    {
        val logic = json {
            obj(Pair("embeds", array(
                    JsonObject(mapOf(
                            "title" to title,
                            "description" to "**Rezultati** - Postavio/la **$author** u predmet **$subject**\n\n$body"
                    )))))
        }

        return logic.toJsonString()
    }
}

data class ExampleItem(
        val author: String,
        val subject: String,
        val body: String)
{
    fun getJson(): String
    {
        val logic = json {
            obj(Pair("embeds", array(
                    JsonObject(mapOf(
                            "title" to "Kliše za: $subject",
                            "description" to "**Kliše** - Postavio/la **$author**\n\n$body"
                    )))))
        }

        return logic.toJsonString()
    }
}