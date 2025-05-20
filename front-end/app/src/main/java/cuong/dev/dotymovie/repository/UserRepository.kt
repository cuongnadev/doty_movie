package cuong.dev.dotymovie.repository

import cuong.dev.dotymovie.data.remote.UserApi
import cuong.dev.dotymovie.model.user.ChangePasswordRequest
import cuong.dev.dotymovie.model.user.UpdateUserRequest
import cuong.dev.dotymovie.model.user.User
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi
) {
    suspend fun getCurrentUser(userId: Int): Response<User> {
        return userApi.getCurrentUser(userId)
    }
    suspend fun updateUser(userId: Int, name: String, email: String): Response<User> {
        return userApi.updateUser(userId, UpdateUserRequest(name, email))
    }
    suspend fun changePassword(userId: Int, oldPassword: String, newPassword: String): Response<Unit> {
        return userApi.changePassword(userId, ChangePasswordRequest(oldPassword, newPassword))
    }
}