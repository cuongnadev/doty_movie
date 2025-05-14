package cuong.dev.dotymovie.model.auth

data class VerifyRequest (
    val user: UserRegister,
    val code: String
)

data class UserRegister (
    val email: String,
    val password: String,
    val name: String
)