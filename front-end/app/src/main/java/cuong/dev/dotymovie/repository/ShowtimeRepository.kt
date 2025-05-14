package cuong.dev.dotymovie.repository

import cuong.dev.dotymovie.data.remote.ShowtimeApi
import cuong.dev.dotymovie.model.showtime.Showtime
import retrofit2.Response
import javax.inject.Inject

class ShowtimeRepository @Inject constructor(
    private val showtimeApi: ShowtimeApi
) {
    suspend fun getShowtimes(
        movieId: Int,
        theaterId: Int
    ): Response<List<Showtime>> {
        return showtimeApi.getShowtimes(movieId, theaterId)
    }
}