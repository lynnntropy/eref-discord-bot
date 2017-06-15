import model.ExampleItem
import model.NewsItem
import model.ResultItem
import kotlin.concurrent.fixedRateTimer

fun main(args: Array<String>)
{
    println("-- EREF Discord Bot started. --")

    val eref = ErefService()

    var latestNews: NewsItem? = null
    var latestResults: ResultItem? = null
    var latestExample: ExampleItem? = null

    fixedRateTimer(period = 60000)
    {
        println("Running loop.")

        val news = eref.getNews()
        val results = eref.getResults()
        val examples = eref.getExamples()

        if (latestNews != null)
            if (latestNews != news.first()) eref.httpService.sendToWebhook(news.first().getJson())

        if (latestResults != null)
            if (latestResults != results.first()) eref.httpService.sendToWebhook(results.first().getJson())

        if (latestExample != null)
            if (latestExample != examples.first()) eref.httpService.sendToWebhook(examples.first().getJson())

        latestNews = news.first()
        latestResults = results.first()
        latestExample = examples.first()
    }
}