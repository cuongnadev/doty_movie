package cuong.dev.dotymovie.model.ticket

import cuong.dev.dotymovie.viewmodel.TicketCount

data class CreateTicketRequest(
    val userId: Int,
    val ticketCount: TicketCount,
    val showtimeId: Int,
    val seatNumber: List<String>,
    val amount: Int
)
