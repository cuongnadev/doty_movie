package cuong.dev.dotymovie.model.movie

import cuong.dev.dotymovie.model.user.User

data class MovieFavorite(
    val id: Int,
    val movie: Movie,
    val user: User
)