package cuong.dev.dotymovie.repository

import cuong.dev.dotymovie.data.remote.MovieFavoriteApi
import cuong.dev.dotymovie.model.movie.MovieFavorite
import cuong.dev.dotymovie.model.movie.MovieFavoriteDTO
import cuong.dev.dotymovie.model.ticket.TicketRequest
import retrofit2.Response
import javax.inject.Inject

class MovieFavoriteRepository @Inject constructor(
    private val movieFavoriteApi: MovieFavoriteApi
) {
    suspend fun getMovieFavorites(userId: Int): Response<List<MovieFavorite>> {
        return movieFavoriteApi.getMovieFavorites(userId)
    }

    suspend fun createMovieFavorite(request: MovieFavoriteDTO): Response<MovieFavorite> {
        return movieFavoriteApi.createMovieFavorite(request)
    }

    suspend fun deleteMovieFavorite(request: MovieFavoriteDTO): Response<Unit> {
        return movieFavoriteApi.deleteMovieFavorite(request)
    }
}