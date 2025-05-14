package cuong.dev.dotymovie.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import cuong.dev.dotymovie.model.ticket.TicketResponse
import cuong.dev.dotymovie.repository.TicketRepository
import cuong.dev.dotymovie.utils.getSeatPrice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class TicketCount (
    val adult: Int = 0,
    val child: Int = 0
)

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
): ViewModel() {
    private val _tickets = MutableStateFlow<List<TicketResponse>>(emptyList())
    val tickets: StateFlow<List<TicketResponse>> = _tickets

    private val _totalAmount: MutableState<Int> = mutableIntStateOf(0)
    val totalAmount: State<Int> = _totalAmount

    private val _ticketCounts = MutableStateFlow(TicketCount())
    val ticketCounts: StateFlow<TicketCount> = _ticketCounts

    private val _seatNumber = MutableStateFlow<List<String>>(emptyList())
    val seatNumbers: StateFlow<List<String>> = _seatNumber

    suspend fun fetchAllTicketsByUser(userId: Int) {
        try {
            val response = ticketRepository.getTicketsByUserId(userId)

            if (response.isSuccessful) {
                response.body()?.let {
                    _tickets.value = it
                }
            } else {
                _tickets.value = emptyList()
                Log.e("TicketViewModel", "Fetch data tickets with userId($userId) failed: ${response.code()}")
            }
        } catch (e: Exception) {
            _tickets.value = emptyList()
            Log.e("TicketViewModel", "Exception during fetch data tickets with userId($userId)", e)
        }
    }

    private fun setTotalAmount() {
        val seats = _seatNumber.value
        val adultCount = _ticketCounts.value.adult
        val childCount = _ticketCounts.value.child

        val seatTotal = seats.sumOf { seatNumber ->
            getSeatPrice(seatNumber)
        }

        val extraFee = (adultCount * 10) + (childCount * 8)

        _totalAmount.value = seatTotal + extraFee
    }

    fun updateAdult(count: Int) {
        _ticketCounts.value = _ticketCounts.value.copy(adult = count)
        setTotalAmount()
    }

    fun updateChild(count: Int) {
        _ticketCounts.value = _ticketCounts.value.copy(child = count)
        setTotalAmount()
    }

    fun updateSeats(seat: String) {
        val currentSeat = _seatNumber.value.toMutableList()
        if (currentSeat.contains(seat)) {
            currentSeat.remove(seat)
        } else {
            currentSeat.add(seat)
        }

        _seatNumber.value = currentSeat
        _ticketCounts.value = _ticketCounts.value.copy(adult = 0, child = 0)
        setTotalAmount()
    }

    fun clearSeats() {
        _seatNumber.value = emptyList()
        _ticketCounts.value = _ticketCounts.value.copy(adult = 0, child = 0)
    }
}