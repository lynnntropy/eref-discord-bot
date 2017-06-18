import com.beust.klaxon.array
import model.ExampleItem
import model.NewsItem
import model.ResultItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

object ErefService
{
    private val BASE_URL = "https://eref.vts.su.ac.rs"
    private val EBOARD_NEWS_URL = "$BASE_URL/sr/default/eboard/news/noauth/1"
    private val EBOARD_EXAMPLES_URL = "$BASE_URL/sr/default/eboard/examples/noauth/1"
    private val EBOARD_RESULTS_URL = "$BASE_URL/sr/default/eboard/results/noauth/1"

    fun getNews(): List<NewsItem>
    {
        val document = HttpService.fetchDocument(EBOARD_NEWS_URL)
        val newsItems = mutableListOf<NewsItem>()

        for (post in document.select(".eboard-post"))
        {
            val author = post.select(".professor-f").text().trim()
            val subject = post.select(".subjects-f").text().trim()
            val title = post.select(".eboard-post-title").text().trim()
            val body = post.select(".eboard-post-content").html().trim().replace("<br>", "\n").replace("\n\n", "\n")

            val dateTime = getPostDateTime(post.html())

            if (subject in Config.values.array<String>("subjects")!!)
                newsItems.add(NewsItem(dateTime, author, subject, title, body))
        }

        return newsItems
    }

    fun getExamples(): List<ExampleItem>
    {
        val document = HttpService.fetchDocument(EBOARD_EXAMPLES_URL)
        val exampleItems = mutableListOf<ExampleItem>()

        for (post in document.select(".eboard-post"))
        {
            val author = post.select(".professor-f").text().trim()
            val subject = post.select(".subjects-f").text().trim()
            val body = post.select(".eboard-post-content").html().trim().replace("<br>", "\n").replace("\n\n", "\n")

            val link =  post.select(".eboard-post-toolbar a").firstOrNull()
            val fileUrl = link?.let { BASE_URL + it.attr("href")}

            val dateTime = getPostDateTime(post.html())

            if (subject in Config.values.array<String>("subjects")!!)
                exampleItems.add(ExampleItem(dateTime, author, subject, body, fileUrl))
        }

        return exampleItems
    }

    fun getResults(): List<ResultItem>
    {
        val document = HttpService.fetchDocument(EBOARD_RESULTS_URL)
        val resultItems = mutableListOf<ResultItem>()

        for (post in document.select(".eboard-post"))
        {
            val author = post.select(".professor-f").text().trim()
            val subject = post.select(".subjects-f").text().trim()
            val title = post.select(".eboard-post-title").text().trim()
            val body = post.select(".eboard-post-content").html().trim().replace("<br>", "\n").replace("\n\n", "\n")

            val link =  post.select(".eboard-post-toolbar a").firstOrNull()
            val fileUrl = link?.let { BASE_URL + it.attr("href")}

            val dateTime = getPostDateTime(post.html())

            if (subject in Config.values.array<String>("subjects")!!)
                resultItems.add(ResultItem(dateTime, author, subject, title, body, fileUrl))
        }

        return resultItems
    }

    private fun getPostDateTime(postHtml: String): LocalDateTime
    {
        return LocalDateTime.parse(
                Regex("Datum i vreme: (.*)").find(postHtml)!!.groupValues[1],
                DateTimeFormatter.ofPattern("dd.MM.yyyy. HH.mm.ss"))
    }
}