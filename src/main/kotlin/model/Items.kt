package model

import com.beust.klaxon.JsonObject
import com.beust.klaxon.json
import java.time.LocalDateTime

data class NewsItem(
        val dateTime: LocalDateTime,
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
                            "description" to "**Vest** - Postavio/la **$author** u predmet **$subject**\n\n$body",
                            "color" to 0x8BC34A
                    )))))
        }

        return logic.toJsonString()
    }
}

data class ResultItem(
        val dateTime: LocalDateTime,
        val author: String,
        val subject: String,
        val title: String,
        val body: String,
        val fileUrl: String?)
{
    val fileLink: String
        get() = if (fileUrl != null) "\n\n**[[Preuzmi]]($fileUrl)**" else ""

    fun getJson(): String
    {
        val logic = json {
            obj(Pair("embeds", array(
                    JsonObject(mapOf(
                            "title" to title,
                            "description" to "**Rezultati** - Postavio/la **$author** u predmet **$subject**\n\n$body$fileLink",
                            "color" to 0xFF5722
                    )))))
        }

        return logic.toJsonString()
    }
}

data class ExampleItem(
        val dateTime: LocalDateTime,
        val author: String,
        val subject: String,
        val body: String,
        val fileUrl: String?)
{
    val fileLink: String
        get() = if (fileUrl != null) "\n\n**[[Preuzmi]]($fileUrl)**" else ""

    fun getJson(): String
    {
        val logic = json {
            obj(Pair("embeds", array(
                    JsonObject(mapOf(
                            "title" to "Kliše za: $subject",
                            "description" to "**Kliše** - Postavio/la **$author**\n\n$body$fileLink",
                            "color" to 0x2196F3
                    )))))
        }

        return logic.toJsonString()
    }
}