package cuong.dev.dotymovie.repository

import cuong.dev.dotymovie.data.remote.TicketApi
import cuong.dev.dotymovie.model.ticket.TicketResponse
import cuong.dev.dotymovie.model.ticket.CreateTicketRequest
import cuong.dev.dotymovie.model.ticket.CreateTicketResponse
import cuong.dev.dotymovie.model.ticket.TicketStatusResponse
import retrofit2.Response
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketApi: TicketApi
) {
    suspend fun getTicketsByUserId(userId: Int): Response<List<TicketResponse>> {
        return ticketApi.getTicketsByUser(userId)
    }

    suspend fun create(request: CreateTicketRequest): Response<CreateTicketResponse> {
        return ticketApi.create(request)
    }

    suspend fun cancelTicket(ticketCode: String): Response<TicketResponse> {
        return ticketApi.cancelTicket(ticketCode)
    }

    suspend fun getStatus(ticketCode: String): Response<TicketStatusResponse> {
        return ticketApi.getStatus(ticketCode);
    }

    suspend fun delete(ticketCode: String): Response<Unit> {
        return ticketApi.delete(ticketCode)
    }
}