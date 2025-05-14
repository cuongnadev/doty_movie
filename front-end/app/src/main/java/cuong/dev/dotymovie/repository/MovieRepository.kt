package cuong.dev.dotymovie.repository

import cuong.dev.dotymovie.data.remote.MovieApi
import cuong.dev.dotymovie.model.movie.Movie
import cuong.dev.dotymovie.model.movie.UpdateMovieDTO
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi
) {
    suspend fun getMovieHighLights(): Response<List<Movie>> {
        return movieApi.getMovieHighLights()
    }

    suspend fun getNewMovies(): Response<List<Movie>> {
        return movieApi.getNewMovies()
    }

    suspend fun getComingSoonMovies(): Response<List<Movie>> {
        return movieApi.getComingSoonMovies()
    }

    suspend fun getMovieById(movieId: Int, userId: Int): Response<Movie> {
        return movieApi.getMovieById(movieId, userId)
    }

    suspend fun getMovieFavorite(): Response<List<Movie>> {
        return movieApi.getMovieFavorite()
    }

    suspend fun update(movieId: Int, updateData: UpdateMovieDTO): Response<Unit> {
        return movieApi.update(movieId, updateData)
    }
}