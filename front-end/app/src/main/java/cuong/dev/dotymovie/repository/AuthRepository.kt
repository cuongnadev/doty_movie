package cuong.dev.dotymovie.repository

import cuong.dev.dotymovie.data.remote.AuthApi
import cuong.dev.dotymovie.model.auth.LoginRequest
import cuong.dev.dotymovie.model.auth.LoginResponse
import cuong.dev.dotymovie.model.auth.RegisterRequest
import cuong.dev.dotymovie.model.auth.UserRegister
import cuong.dev.dotymovie.model.auth.VerifyRequest
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) {
    suspend fun login(requestLogin: LoginRequest): Response<LoginResponse> {
        return authApi.login(requestLogin)
    }

    suspend fun register(email: String): Response<Unit> {
        return authApi.register(RegisterRequest(email))
    }

    suspend fun verify(user: UserRegister, code: String): Response<Unit> {
        val request = VerifyRequest(
            user,
            code
        )
        return authApi.verifications(request)
    }
}