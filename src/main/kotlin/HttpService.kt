import com.beust.klaxon.string
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import org.apache.http.ssl.SSLContextBuilder
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object HttpService
{
    init
    {
        val builder = SSLContextBuilder()
        builder.loadTrustMaterial(null) { chain, authType -> true }

        FuelManager.instance.socketFactory = builder.build().socketFactory
        FuelManager.instance.baseHeaders = mapOf(
                "Cookie" to "eref_session=${Config.values.string("eref_session")}",
                "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0")
    }

    fun fetchDocument(url: String): Document
    {
        val (request, response, result) = url.httpGet().responseString()
        return Jsoup.parse(result.get())
    }

    fun sendToWebhook(json: String)
    {
        Config.values.string("webhook")!!.httpPost().body(json).header(mapOf("Content-Type" to "application/json")).response()
    }
}