package cuong.dev.dotymovie.utils

import cuong.dev.dotymovie.constants.SeatStatus
import cuong.dev.dotymovie.constants.SeatType
import cuong.dev.dotymovie.data.local.SeatData
import cuong.dev.dotymovie.model.seat.Seat

fun buildSeatMatrixWithReservations(reservedSeatIds: List<String>): List<List<Seat?>> {
    return SeatData.seatMatrix.map { row ->
        row.map { seat ->
            if (seat != null && seat.seatNumber in reservedSeatIds) {
                seat.copy(status = SeatStatus.BOOKED.toString())
            } else {
                seat
            }
        }
    }
}

fun getSeatPrice(seatNumber: String): Int {
    for (row in SeatData.seatMatrix) {
        for (seat in row) {
            if (seat?.seatNumber == seatNumber) {
                return if (seat.type == SeatType.VIP.toString()) 20 else 15
            }
        }
    }
    throw IllegalArgumentException("Seat $seatNumber not found")
}

