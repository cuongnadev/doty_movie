package cuong.dev.dotymovie.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import cuong.dev.dotymovie.model.showtime.Showtime
import cuong.dev.dotymovie.repository.ShowtimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ShowtimeViewModel @Inject constructor(
    private val showtimeRepository: ShowtimeRepository
): ViewModel() {
    private val _showtimes = MutableStateFlow<List<Showtime>>(emptyList())
    val showtimes: Flow<List<Showtime>> = _showtimes

    private val _showtime: MutableState<Showtime?> = mutableStateOf(null)
    val showtime: State<Showtime?> = _showtime

    suspend fun fetchShowtimes(movieId: Int, theaterId: Int) {
        try {
            val response = showtimeRepository.getShowtimes(movieId, theaterId)

            if(response.isSuccessful) {
                response.body()?.let {
                    _showtimes.value = it
                } ?: {
                    _showtimes.value = emptyList()
                }
            } else {
                _showtimes.value = emptyList()
                Log.e("ShowtimeViewModel", "Fetch showtimes with movieId($movieId), theaterId($theaterId) failed: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("ShowtimeViewModel", "Exception during fetch showtimes with movieId($movieId), theaterId($theaterId)", e)
        }
    }

    fun setShowtime(showtime: Showtime?) {
        _showtime.value = showtime
    }
}