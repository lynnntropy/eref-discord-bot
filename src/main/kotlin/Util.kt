import org.apache.commons.lang3.text.WordUtils
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun LocalDateTime.minutesAgo() = ChronoUnit.MINUTES.between(this, LocalDateTime.now())
fun String.lowerAndCapitalize(): String = WordUtils.capitalizeFully(this)