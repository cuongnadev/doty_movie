package cuong.dev.dotymovie.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cuong.dev.dotymovie.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _oldPassword = mutableStateOf("")
    val oldPassword: State<String> = _oldPassword

    private val _newPassword = mutableStateOf("")
    val newPassword: State<String> = _newPassword

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _successMessage = mutableStateOf<String?>(null)
    val successMessage: State<String?> = _successMessage

    suspend fun loadUser(userId: Int) {
        viewModelScope.launch {
            try {
                val response = userRepository.getCurrentUser(userId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _name.value = it.name
                        _email.value = it.email
                    }
                } else {
                    _errorMessage.value = "Error when fetch data user {$userId} ${response.code()}"
                    Log.e("UserViewModel", "Error when fetch data user {$userId} ${response.code()}")
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e("UserViewModel", "Exception during fetch data user {$userId}", e)
            }
        }
    }

    fun updateUser(userId: Int) {
        viewModelScope.launch {
            try {
                val response = userRepository.updateUser(
                    userId,
                    _name.value, _email.value
                )
                if (response.isSuccessful) {
                    _successMessage.value = "Update successful"
                    response.body()?.let {
                        _name.value = it.name
                        _email.value = it.email
                    }
                } else {
                    _errorMessage.value = "Failed to update user ($userId): ${response.code()}"
                    Log.e("UserViewModel", "Failed to update user $userId - Code: ${response.code()}")
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e("UserViewModel", "Exception while updating user $userId", e)
            }
        }
    }

    fun changePassword(userId: Int) {
        viewModelScope.launch {
            try {
                val response = userRepository.changePassword(
                    userId,
                    _oldPassword.value, _newPassword.value
                )
                if (response.isSuccessful) {
                    _successMessage.value = "Password changed successfully"
                } else {
                    _errorMessage.value = "Failed to change password ($userId): ${response.code()}"
                    Log.e("UserViewModel", "Failed to change password for user $userId - Code: ${response.code()}")
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e("UserViewModel", "Exception while changing password for user $userId", e)
            }
        }
    }

}