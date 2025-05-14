package cuong.dev.dotymovie.model.media

data class Media(
    val id: Int,
    val galleryUrl: List<String>?,
    val urlPoster: String?,
    val urlTrailer: String?,
    val urlPrimary: String?,
)
