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

//    val news = ErefService.getNews()
//    val results = ErefService.getResults()
//    val examples = ErefService.getExamples()

    fixedRateTimer(period = 60000)
    {
        try
        {
            Logger.log("Running loop...")

            val news = ErefService.getNews()
            val results = ErefService.getResults()
            val examples = ErefService.getExamples()

            Logger.log("Fetched: ${news.size} news, ${results.size} results, ${examples.size} examples.")

            if (latestNews != null && latestNews != news[0] && news[0].dateTime.minutesAgo() < 10)
            {
                HttpService.sendToWebhook(news[0].getJson())
                Logger.log("Old: $latestNews")
                Logger.log("New: ${news[0]}")
            }

            if (latestResults != null && latestResults != results[0] && results[0].dateTime.minutesAgo() < 10)
            {
                HttpService.sendToWebhook(results[0].getJson())
                Logger.log("Old: $latestResults")
                Logger.log("New: ${results[0]}")
            }

            if (latestExample != null && latestExample != examples[0] && examples[0].dateTime.minutesAgo() < 10)
            {
                HttpService.sendToWebhook(examples[0].getJson())
                Logger.log("Old: $latestExample")
                Logger.log("New: ${examples[0]}")
            }

            latestNews = news[0]
            latestResults = results[0]
            latestExample = examples[0]
        }
        catch (e: Exception)
        {
            Logger.log("Loop execution interrupted. Cause: $e")
        }
    }
}