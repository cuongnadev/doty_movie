package cuong.dev.dotymovie.model.showtime

import java.time.LocalDateTime

data class Showtime(
    val id: Int,
    val startTime: String,
    val endTime: String
)
