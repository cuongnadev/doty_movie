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

enum class PaymentStatus {
    SUCCESSFUL, FAILED
}

enum class MovieStatus(val status: String) {
    HIGHLIGHT("highlight"),
    NOW_SHOWING("now_showing"),
    COMING_SOON("coming_soon")
}

enum class SeatStatus(val status: String) {
    BOOKED("booked"),
    AVAILABLE("available")
}

enum class SeatType(val type: String) {
    STANDARD("standard"),
    VIP("vip")
}