package cuong.dev.dotymovie.data.remote

import cuong.dev.dotymovie.model.user.ChangePasswordRequest
import cuong.dev.dotymovie.model.user.UpdateUserRequest
import cuong.dev.dotymovie.model.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface UserApi {
    @GET("user/{userId}")
    suspend fun getCurrentUser(@Path("userId") userId: Int): Response<User>

    @PATCH("user/{userId}")
    suspend fun updateUser(
        @Path("userId") userId: Int,
        @Body updateUserRequest: UpdateUserRequest
    ): Response<User>

    @PATCH("user/{userId}/password")
    suspend fun changePassword(
        @Path("userId") userId: Int,
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<Unit>
}