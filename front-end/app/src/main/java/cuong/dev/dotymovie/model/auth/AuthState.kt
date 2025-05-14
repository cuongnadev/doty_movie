package cuong.dev.dotymovie.model.auth

data class AuthState (
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)