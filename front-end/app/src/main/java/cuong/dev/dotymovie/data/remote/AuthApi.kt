package cuong.dev.dotymovie.data.remote

import cuong.dev.dotymovie.model.auth.LoginRequest
import cuong.dev.dotymovie.model.auth.LoginResponse
import cuong.dev.dotymovie.model.auth.RegisterRequest
import cuong.dev.dotymovie.model.auth.VerifyRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/verifications")
    suspend fun verifications(@Body request: VerifyRequest): Response<Unit>
}