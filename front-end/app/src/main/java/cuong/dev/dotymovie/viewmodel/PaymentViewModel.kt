package cuong.dev.dotymovie.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cuong.dev.dotymovie.model.ticket.TicketRequest
import cuong.dev.dotymovie.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
): ViewModel() {
    private val _ticket: MutableState<TicketRequest?> = mutableStateOf(null)
    val ticket: State<TicketRequest?> = _ticket

    private val _paymentSuccess = MutableStateFlow<Boolean?>(null)
    val paymentSuccess: StateFlow<Boolean?> = _paymentSuccess

    fun setTicketData(ticketData: TicketRequest) {
        _ticket.value = ticketData
    }

    suspend fun payment(request: TicketRequest) {
        println("Sending payment request: $request")
        try {
            val response = ticketRepository.create(request)

            if(response.isSuccessful) {
                _paymentSuccess.value = true
            } else {
                _paymentSuccess.value = false
                Log.e("PaymentViewModel", "Payment ticket failed: ${response.code()}")
            }
        } catch (e: Exception) {
            _paymentSuccess.value = false
            Log.e("PaymentViewModel", "Error during payment ticket", e)
        }
    }
}
