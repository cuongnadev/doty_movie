package cuong.dev.dotymovie.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import cuong.dev.dotymovie.model.seat.Seat
import cuong.dev.dotymovie.repository.SeatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SeatViewModel @Inject constructor(
    private val seatRepository: SeatRepository
): ViewModel() {
    private val _seats = MutableStateFlow<List<Seat>>(emptyList())
    val seats: Flow<List<Seat>> = _seats

    private val _seat: MutableState<Seat?> = mutableStateOf(null)
    val seat: State<Seat?> = _seat

    suspend fun fetchSeatsByShowtime(showtimeId: Int) {
        try {
            val response = seatRepository.getSeatsByShowtime(showtimeId)

            if(response.isSuccessful) {
                response.body()?.let {
                    _seats.value = it
                } ?: {
                    _seats.value = emptyList()
                }
            } else {
                _seats.value = emptyList()
                Log.e("SeatViewModel", "Fetch data seat with showtimeId($showtimeId) failed: ${response.code()}")
            }
        } catch (e: Exception) {
            _seats.value = emptyList()
            Log.e("SeatViewModel", "Exception during fetch seat by showtimeId($showtimeId)", e)
        }
    }

    fun setSeat(seat: Seat) {

    }
}