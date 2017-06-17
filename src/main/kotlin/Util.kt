import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun LocalDateTime.minutesAgo() = ChronoUnit.MINUTES.between(this, LocalDateTime.now())