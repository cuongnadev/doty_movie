package cuong.dev.dotymovie.repository

import cuong.dev.dotymovie.data.remote.TicketApi
import cuong.dev.dotymovie.model.ticket.TicketResponse
import cuong.dev.dotymovie.model.ticket.TicketRequest
import retrofit2.Response
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketApi: TicketApi
) {
    suspend fun getTicketsByUserId(userId: Int): Response<List<TicketResponse>> {
        return ticketApi.getTicketsByUser(userId)
    }

    suspend fun create(request: TicketRequest): Response<Unit> {
        return ticketApi.create(request)
    }
}