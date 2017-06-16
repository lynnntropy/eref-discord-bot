import java.time.LocalTime
import java.time.format.DateTimeFormatter

object Logger
{
    fun log(message: String)
    {
        val timeString = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        println("[$timeString] $message")
    }
}