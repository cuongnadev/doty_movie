package cuong.dev.dotymovie.repository

import cuong.dev.dotymovie.data.remote.TheaterApi
import cuong.dev.dotymovie.model.theater.Theater
import retrofit2.Response
import javax.inject.Inject

class TheaterRepository @Inject constructor(
    private val theaterApi: TheaterApi
) {
    suspend fun getAllTheaters(): Response<List<Theater>> {
        return theaterApi.getAllTheaters()
    }

    suspend fun getTheaterById(id: Int): Response<Theater> {
        return theaterApi.getTheaterById(id)
    }
}