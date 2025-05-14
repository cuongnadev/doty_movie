package cuong.dev.dotymovie.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(dateString: String): Date? {
    val inputDateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputDateFormatter.timeZone = TimeZone.getTimeZone("UTC")

    val date = try {
        inputDateFormatter.parse(dateString)
    } catch (e: Exception) {
        null
    }

    return date
}

fun formatTimeDropdown(dateString: String): String {
    val date = formatDate(dateString);

    return if (date != null) {
        val formatter = SimpleDateFormat("MMMM dd, yyyy - HH:mm a", Locale.getDefault())
        formatter.format(date)
    } else {
        "Invalid date"
    }
}

fun formatTimeTicket(startTime: String, endTime: String): String {
    val start = formatDate(startTime)
    val end = formatDate(endTime)

    return if(start != null && end != null) {
        val formatter = SimpleDateFormat("HH:mma", Locale.getDefault())
        formatter.format(start).lowercase() + " - " + formatter.format(end).lowercase()
    } else {
        "Invalid date"
    }
}
