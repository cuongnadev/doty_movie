package cuong.dev.dotymovie.data.remote

import cuong.dev.dotymovie.model.movie.MovieFavoriteDTO
import cuong.dev.dotymovie.model.movie.MovieFavorite
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path

interface MovieFavoriteApi {
    @GET("movie-favorite/{userId}")
    suspend fun getMovieFavorites(@Path("userId") userId: Int): Response<List<MovieFavorite>>

    @POST("movie-favorite")
    suspend fun createMovieFavorite(@Body request: MovieFavoriteDTO): Response<MovieFavorite>

    @HTTP(method = "DELETE", path = "movie-favorite", hasBody = true)
    suspend fun deleteMovieFavorite(@Body request: MovieFavoriteDTO): Response<Unit>
}