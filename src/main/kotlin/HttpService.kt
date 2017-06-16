import Config
import com.beust.klaxon.string
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.protocol.ClientContext
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.BasicCookieStore
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.cookie.BasicClientCookie
import org.apache.http.protocol.BasicHttpContext
import org.apache.http.ssl.SSLContextBuilder
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object HttpService
{
    private val httpClient = createHttpClient()

    private fun createHttpClient(): CloseableHttpClient
    {
        val builder = SSLContextBuilder()
        builder.loadTrustMaterial(null) { chain, authType -> true }

        return HttpClients.custom()
                .setSSLSocketFactory(SSLConnectionSocketFactory(builder.build(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER))
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0")
                .build()
    }

    fun fetchDocument(url: String): Document
    {
        val cookieStore = BasicCookieStore()

        val cookie = BasicClientCookie("eref_session", Config.values.string("eref_session"))
        cookie.domain = "eref.vts.su.ac.rs"
        cookie.path = "/"
        cookieStore.addCookie(cookie)

        val localContext = BasicHttpContext()
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore)

        val httpGet = HttpGet(url)
        val httpResponse = httpClient.execute(httpGet, localContext)
        val pageHtml = httpResponse.entity.content.bufferedReader().use { it.readText() }
        return Jsoup.parse(pageHtml)
    }

    fun sendToWebhook(json: String)
    {
        val httpPost = HttpPost(Config.values.string("webhook"))
        httpPost.entity = StringEntity(json, "application/json", "UTF-8")
        httpClient.execute(httpPost)
    }
}