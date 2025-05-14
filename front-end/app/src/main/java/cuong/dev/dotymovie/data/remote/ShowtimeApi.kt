package cuong.dev.dotymovie.data.remote

import cuong.dev.dotymovie.model.showtime.Showtime
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowtimeApi {
    @GET("showtime/movie-theater")
    suspend fun getShowtimes(
        @Query("movieId") movieId: Int,
        @Query("theaterId") theaterId: Int
    ): Response<List<Showtime>>
}