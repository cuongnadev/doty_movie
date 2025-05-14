package cuong.dev.dotymovie.data.remote

import cuong.dev.dotymovie.model.theater.Theater
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TheaterApi {
    @GET("theater")
    suspend fun getAllTheaters(): Response<List<Theater>>

    @GET("theater/{id}")
    suspend fun getTheaterById(@Path("id") id: Int): Response<Theater>
}