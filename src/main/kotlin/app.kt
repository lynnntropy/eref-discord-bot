import model.ExampleItem
import model.NewsItem
import model.ResultItem
import kotlin.concurrent.fixedRateTimer

fun main(args: Array<String>)
{
    println("-- EREF Discord Bot started. --")

    var latestNews: NewsItem? = null
    var latestResults: ResultItem? = null
    var latestExample: ExampleItem? = null

    fixedRateTimer(period = 60000)
    {
        Logger.log("Running loop...")

        val news = ErefService.getNews()
        val results = ErefService.getResults()
        val examples = ErefService.getExamples()

        Logger.log("Fetched: ${news.size} news, ${results.size} results, ${examples.size} examples.")

        if (latestNews != null && latestNews != news.first())
        {
            HttpService.sendToWebhook(news.first().getJson())
            Logger.log("Old: $latestNews")
            Logger.log("New: ${news.first()}")
        }

        if (latestResults != null && latestResults != results.first())
        {
            HttpService.sendToWebhook(results.first().getJson())
            Logger.log("Old: $latestResults")
            Logger.log("New: ${results.first()}")
        }

        if (latestExample != null && latestExample != examples.first())
        {
            HttpService.sendToWebhook(examples.first().getJson())
            Logger.log("Old: $latestExample")
            Logger.log("New: ${examples.first()}")
        }

        latestNews = news.first()
        latestResults = results.first()
        latestExample = examples.first()
    }
}