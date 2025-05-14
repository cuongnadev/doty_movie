package cuong.dev.dotymovie.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cuong.dev.dotymovie.model.theater.Theater
import cuong.dev.dotymovie.repository.TheaterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TheaterViewModel @Inject constructor(
    private val theaterRepository: TheaterRepository
): ViewModel() {
    private val _theaters = MutableStateFlow<List<Theater>>(emptyList())
    val theaters: Flow<List<Theater>> = _theaters.asStateFlow()

    private val _theater: MutableState<Theater?> = mutableStateOf(null)
    val theater: State<Theater?> = _theater

    suspend fun fetchAllTheaters() {
        try {
            val response = theaterRepository.getAllTheaters()

            if(response.isSuccessful) {
                response.body()?.let {
                    _theaters.value = it
                } ?: {
                    _theaters.value = emptyList()
                }
            } else {
                _theaters.value = emptyList()
                Log.e("TheaterViewModel", "Fetch theaters failed: ${response.code()}")
            }
        } catch (e: Exception) {
            _theaters.value = emptyList()
            Log.e("TheaterViewModel", "Exception during fetch theaters", e)
        }
    }

    fun setTheater(theater: Theater?) {
        _theater.value = theater
    }
}