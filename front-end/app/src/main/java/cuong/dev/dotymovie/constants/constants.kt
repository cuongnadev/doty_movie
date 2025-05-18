package cuong.dev.dotymovie.constants

const val numOfStep = 4;

enum class TypeStep(val value: Int) {
    BUY_TICKET(1),
    TICKET_DETAILS(2),
    PAYMENT_METHOD(3),
    PAYMENT_S_F(4)
}

enum class TypeMovieItem {
    HORIZONTAL, VERTICAL
}

enum class TicketStatus(val value: String) {
    PENDING("pending"),
    CONFIRMED("confirmed"),
    CANCELLED("cancelled"),
    PAID("paid");

    override fun toString(): String = value

    companion object {
        fun fromValue(value: String): TicketStatus? {
            return values().find { it.value == value }
        }
    }
}

enum class SeatStatus(val status: String) {
    BOOKED("booked"),
    AVAILABLE("available")
}

enum class SeatType(val type: String) {
    STANDARD("standard"),
    VIP("vip")
}