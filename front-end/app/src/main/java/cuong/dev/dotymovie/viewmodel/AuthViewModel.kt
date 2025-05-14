package cuong.dev.dotymovie.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cuong.dev.dotymovie.model.auth.AuthState
import cuong.dev.dotymovie.model.auth.LoginRequest
import cuong.dev.dotymovie.model.auth.LoginResponse
import cuong.dev.dotymovie.model.auth.UserRegister
import cuong.dev.dotymovie.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,

    @ApplicationContext
    private val context: Context,
): ViewModel() {
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var name = mutableStateOf("")
    var code = mutableStateOf("")
    val authState = mutableStateOf(AuthState())

    var loginResponse: LoginResponse? = null

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("auth_pref", Context.MODE_PRIVATE)

    private fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("auth_token", token)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    fun isUserLoggedIn(): Boolean {
        return getToken() != null
    }

    suspend fun login(email: String, password: String) {
        try {
            val response = authRepository.login(LoginRequest(email, password))

            if(response.isSuccessful) {
                loginResponse = response.body()
                loginResponse?.let {
                    it.accessToken?.let { accessToken -> saveToken(accessToken) }
                }

                authState.value = authState.value.copy(
                    isSuccess = true
                )
            } else {
                val errorJson = response.errorBody()?.string()
                val errorMessage = JSONObject(errorJson ?: "").optString("message", "Unknown error")

                authState.value = authState.value.copy(
                    isSuccess = false,
                    errorMessage = errorMessage
                )
            }
        } catch (e: Exception) {
            authState.value = authState.value.copy(
                isSuccess = false,
                errorMessage = "An error occurred: ${e.message}"
            )
        } finally {
            authState.value = authState.value.copy(isLoading = false)
        }
    }

    suspend fun register(email: String) {
        try {
            val response = authRepository.register(email)

            if(response.isSuccessful) {
                authState.value = authState.value.copy(
                    isSuccess = true
                )
            } else {
                val errorJson = response.errorBody()?.string()
                val errorMessage = JSONObject(errorJson ?: "").optString("message", "Unknown error")

                authState.value = authState.value.copy(
                    isSuccess = false,
                    errorMessage = errorMessage
                )
            }
        } catch (e: Exception) {
            authState.value = authState.value.copy(
                isSuccess = false,
                errorMessage = "An error occurred: ${e.message}"
            )
        } finally {
            authState.value = authState.value.copy(isLoading = false)
        }
    }

    suspend fun verify(code: String) {
        try {
            val response = authRepository.verify(UserRegister(this.email.value, this.password.value, this.name.value), code)

            if(response.isSuccessful) {
                authState.value = authState.value.copy(
                    isSuccess = true
                )
            } else {
                val errorJson = response.errorBody()?.string()
                val errorMessage = JSONObject(errorJson ?: "").optString("message", "Unknown error")

                authState.value = authState.value.copy(
                    isSuccess = false,
                    errorMessage = errorMessage
                )
            }
        } catch (e: Exception) {
            authState.value = authState.value.copy(
                isSuccess = false,
                errorMessage = "An error occurred: ${e.message}"
            )
        } finally {
            authState.value = authState.value.copy(isLoading = false)
        }
    }

     fun logout() {
        val editor = sharedPreferences.edit()
        editor.remove("auth_token")
        editor.apply()
    }
}