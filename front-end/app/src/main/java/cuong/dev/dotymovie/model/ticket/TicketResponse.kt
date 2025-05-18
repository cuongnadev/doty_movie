package cuong.dev.dotymovie.model.ticket

import cuong.dev.dotymovie.constants.TicketStatus
import cuong.dev.dotymovie.viewmodel.TicketCount

data class TicketResponse (
    val id: Int,
    val ticketCount: TicketCount,
    val selectedSeats: List<String>,
    val movie: String,
    val theater: String,
    val startTime: String,
    val endTime: String,
    val amount: Int,
    val isUsed: Boolean,
    val status: TicketStatus
)