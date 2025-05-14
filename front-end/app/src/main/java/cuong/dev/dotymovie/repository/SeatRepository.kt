package cuong.dev.dotymovie.repository

import cuong.dev.dotymovie.data.remote.SeatApi
import cuong.dev.dotymovie.model.seat.Seat
import retrofit2.Response
import javax.inject.Inject

class SeatRepository @Inject constructor(
    private val seatApi: SeatApi
) {
    suspend fun getSeatsByShowtime(showtimeId: Int): Response<List<Seat>> {
        return seatApi.getSeatsByShowtime(showtimeId)
    }
}