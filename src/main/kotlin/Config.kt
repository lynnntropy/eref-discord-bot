import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser

class Config
{
    companion object {
        val jsonObject = Parser().parse(fileName = "config.json") as JsonObject
    }
}

