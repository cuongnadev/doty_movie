package cuong.dev.dotymovie.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cuong.dev.dotymovie.constants.TicketStatus
import cuong.dev.dotymovie.model.ticket.CreateTicketRequest
import cuong.dev.dotymovie.model.ticket.CreateTicketResponse
import cuong.dev.dotymovie.model.ticket.TicketResponse
import cuong.dev.dotymovie.repository.TicketRepository
import cuong.dev.dotymovie.utils.getSeatPrice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private val _ticket: MutableState<CreateTicketResponse?> = mutableStateOf(null)
    val ticket: State<CreateTicketResponse?> = _ticket

    private val _tickets = MutableStateFlow<List<TicketResponse>>(emptyList())
    val tickets: StateFlow<List<TicketResponse>> = _tickets

    private val _ticketStatus = MutableStateFlow<TicketStatus?>(null)
    val ticketStatus: StateFlow<TicketStatus?> = _ticketStatus

    private val _totalAmount: MutableState<Int> = mutableIntStateOf(0)
    val totalAmount: State<Int> = _totalAmount

    private val _ticketCounts = MutableStateFlow(TicketCount())
    val ticketCounts: StateFlow<TicketCount> = _ticketCounts

    private val _seatNumbers = MutableStateFlow<List<String>>(emptyList())
    val seatNumbers: StateFlow<List<String>> = _seatNumbers

    private val _limitSeats: MutableState<Int> = mutableIntStateOf(0)
    val limitSeats: State<Int> = _limitSeats

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

    suspend fun saveTicket(request: CreateTicketRequest) {
        try {
            delay(2000)
            val response = ticketRepository.create(request)

            if(response.isSuccessful) {
                _ticket.value = response.body()
                Log.i("Create Ticket", response.body().toString())
            } else {
                Log.e("Create Ticket", "Create ticket failed: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("Create Ticket", "Error during create ticket", e)
        }
    }

    suspend fun cancelTicket(ticketCode: String) {
        try {
            val response = ticketRepository.cancelTicket(ticketCode)

            if(response.isSuccessful) {
                Log.i("Cancel Ticket", response.body().toString())
            } else {
                Log.e("TicketViewModel", "Cancel ticket failed: ${response.code()}")
            }
        }catch (e: Exception) {
            Log.e("TicketViewModel", "Error during cancel ticket", e)
        }
    }

    suspend fun getStatusTicket(ticketCode: String) {
        try {
            val response = ticketRepository.getStatus(ticketCode)

            if(response.isSuccessful) {
                val body = response.body()
                body?.let {
                    val status = when(it.status.lowercase()) {
                        "paid" -> TicketStatus.PAID
                        "confirmed" -> TicketStatus.CONFIRMED
                        "cancelled" -> TicketStatus.CANCELLED
                        else -> TicketStatus.PENDING
                    }
                    _ticketStatus.value = status
                }
            } else {
                Log.e("TicketViewModel", "Cancel ticket failed: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("TicketViewModel", "Error during get Status ticket", e)
        }
    }

    suspend fun deleteTicket(ticketCode: String) {
        try {
            val response = ticketRepository.delete(ticketCode)

            if(response.isSuccessful) {
                Log.i("Delete Ticket", response.body().toString())
            } else {
                Log.e("Delete Ticket", "Delete ticket failed: ${response.code()}")
            }
        }catch (e: Exception) {
            Log.e("Delete Ticket", "Error during delete ticket", e)
        }
    }

    fun updateAdult(count: Int) {
        _ticketCounts.value = _ticketCounts.value.copy(adult = count)
        updateLimitSeats()
    }

    fun updateChild(count: Int) {
        _ticketCounts.value = _ticketCounts.value.copy(child = count)
        updateLimitSeats()
    }

    fun updateSeats(seat: String): Boolean {
        val currentSeat = _seatNumbers.value.toMutableList()
        if (currentSeat.contains(seat)) {
            currentSeat.remove(seat)
        } else {
            if (currentSeat.size == limitSeats.value) {
                return false
            }

            currentSeat.add(seat)
        }

        _seatNumbers.value = currentSeat
        setTotalAmount()

        return true
    }

    fun clearSeats() {
        _seatNumbers.value = emptyList()
        _ticketCounts.value = _ticketCounts.value.copy(adult = 0, child = 0)
        _limitSeats.value = 0
        setTotalAmount()
    }

    private fun setTotalAmount() {
        val seats = _seatNumbers.value
        val adultCount = _ticketCounts.value.adult
        val childCount = _ticketCounts.value.child

        val seatTotal = seats.sumOf { seatNumber ->
            getSeatPrice(seatNumber)
        }

        val extraFee = (adultCount * 10) + (childCount * 8)

        _totalAmount.value = seatTotal + extraFee
    }

    private fun updateLimitSeats() {
        _limitSeats.value = _ticketCounts.value.adult + _ticketCounts.value.child
    }
}