package cuong.dev.dotymovie.model.movie

import java.util.Date

data class UpdateMovieDTO(
    val title: String? = null,
    val description: String? = null,
    val duration: Int? = null,
    val releaseDate: Date? = null,
    val genre: List<String>? = null,
    val status: String? = null,
    val isFavorite: Boolean? = null
)