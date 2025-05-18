package cuong.dev.dotymovie.data.remote

import cuong.dev.dotymovie.model.movie.Movie
import cuong.dev.dotymovie.model.movie.UpdateMovieDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/high-light")
    suspend fun getMovieHighLights(): Response<List<Movie>>

    @GET("movie/new-movie")
    suspend fun getNewMovies(): Response<List<Movie>>

    @GET("movie/coming-soon")
    suspend fun getComingSoonMovies(): Response<List<Movie>>

    @GET("movie/{movieId}/{userId}")
    suspend fun getMovieById(@Path("movieId") movieId: Int, @Path("userId") userId: Int): Response<Movie>

    @GET("movie/favorites")
    suspend fun getMovieFavorite(): Response<List<Movie>>

    @PATCH("movie/{id}")
    suspend fun update(@Path("id") id: Int, @Body updateData: UpdateMovieDTO): Response<Unit>

    @GET("movie/search")
    suspend fun search(@Query("q") query: String): Response<List<Movie>>
}