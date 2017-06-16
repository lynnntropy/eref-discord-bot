import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser

object Config
{
    val values = Parser().parse(fileName = "config.json") as JsonObject
}

