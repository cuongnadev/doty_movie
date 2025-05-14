package cuong.dev.dotymovie.model.movie

import cuong.dev.dotymovie.model.media.Media
import java.util.Date

data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val duration: Int,
    val releaseDate: Date?,
    val genre: List<String>,
    val status: String,
    val isFavorite: Boolean,
    val medias: List<Media>
) {
    fun applyUpdate(dto: UpdateMovieDTO): Movie {
        return this.copy(
            title = dto.title ?: this.title,
            description = dto.description ?: this.description,
            duration = dto.duration ?: this.duration,
            releaseDate = dto.releaseDate ?: this.releaseDate,
            genre = dto.genre ?: this.genre,
            status = dto.status ?: this.status,
            isFavorite = dto.isFavorite ?: this.isFavorite,
        )
    }
}