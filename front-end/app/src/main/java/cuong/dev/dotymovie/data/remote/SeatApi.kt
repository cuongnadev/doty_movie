package cuong.dev.dotymovie.data.remote

import cuong.dev.dotymovie.model.seat.Seat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SeatApi {
    @GET("seat")
    suspend fun getSeatsByShowtime(@Query("showtimeId") showtimeId: Int): Response<List<Seat>>
}