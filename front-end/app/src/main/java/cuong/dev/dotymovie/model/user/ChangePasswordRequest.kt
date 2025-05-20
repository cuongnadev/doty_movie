package cuong.dev.dotymovie.model.user

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)
