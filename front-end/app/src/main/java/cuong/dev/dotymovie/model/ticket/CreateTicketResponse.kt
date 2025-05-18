package cuong.dev.dotymovie.model.ticket

data class CreateTicketResponse(
    val ticketCode: String,
    val amount: Int,
    val urlQR: String
)