package cuong.dev.dotymovie.data.local

import cuong.dev.dotymovie.constants.SeatType
import cuong.dev.dotymovie.model.seat.Seat

object SeatData {
    val seatMatrix: List<List<Seat?>> = listOf(
        // 4 top row
        listOf(Seat("A1", null, SeatType.VIP.toString()), Seat("A2", null, SeatType.VIP.toString()), Seat("A3", null, SeatType.VIP.toString()), Seat("A4", null, SeatType.VIP.toString()), Seat("A5", null, SeatType.VIP.toString()),  Seat("A6", null, SeatType.VIP.toString()), Seat("A7", null, SeatType.VIP.toString()), Seat("A8", null, SeatType.VIP.toString()), Seat("A9", null, SeatType.VIP.toString()), null, Seat("A10", null, SeatType.VIP.toString()), Seat("A11", null, SeatType.VIP.toString())),
        listOf(Seat("B1", null, SeatType.VIP.toString()), Seat("B2", null, SeatType.VIP.toString()), Seat("B3", null, SeatType.VIP.toString()), Seat("B4", null, SeatType.VIP.toString()), Seat("B5", null, SeatType.VIP.toString()),  Seat("B6", null, SeatType.VIP.toString()), Seat("B7", null, SeatType.VIP.toString()), Seat("B8", null, SeatType.VIP.toString()), Seat("B9", null, SeatType.VIP.toString()), null, Seat("B10", null, SeatType.VIP.toString()), Seat("B11", null, SeatType.VIP.toString())),
        listOf(Seat("C1", null, null), Seat("C2", null, null), Seat("C3", null, null), Seat("C4", null, null), Seat("C5", null, null),  Seat("C6", null, null), Seat("C7", null, null), Seat("C8", null, null), Seat("C9", null, null), null, Seat("C10", null, null), Seat("C11", null, null)),
        listOf(Seat("D1", null, null), Seat("D2", null, null), Seat("D3", null, null), Seat("D4", null, null), Seat("D5", null, null),  Seat("D6", null, null), Seat("D7", null, null), Seat("D8", null, null), Seat("D9", null, null), null, Seat("D10", null, null), Seat("D11", null, null)),

        List(11) { null },

        // 4 bottom row
        listOf(Seat("E1", null, null), Seat("E2", null, null), Seat("E3", null, null), Seat("E4", null, null), Seat("E5", null, null),  Seat("E6", null, null), Seat("E7", null, null), Seat("E8", null, null), Seat("E9", null, null), null, Seat("E10", null, null), Seat("E11", null, null)),
        listOf(Seat("F1", null, null), Seat("F2", null, null), Seat("F3", null, null), Seat("F4", null, null), Seat("F5", null, null),  Seat("F6", null, null), Seat("F7", null, null), Seat("F8", null, null), Seat("F9", null, null), null, Seat("F10", null, null), Seat("F11", null, null)),
        listOf(Seat("G1", null, null), Seat("G2", null, null), Seat("G3", null, null), Seat("G4", null, null), Seat("G5", null, null),  Seat("G6", null, null), Seat("G7", null, null), Seat("G8", null, null), Seat("G9", null, null), null, Seat("G10", null, null), Seat("G11", null, null)),
        listOf(Seat("H1", null, null), Seat("H2", null, null), Seat("H3", null, null), Seat("H4", null, null), Seat("H5", null, null),  Seat("H6", null, null), Seat("H7", null, null), Seat("H8", null, null), Seat("H9", null, null), null, Seat("H10", null, null), Seat("H11", null, null)),
    )
}