package cuong.dev.dotymovie.data.remote

import cuong.dev.dotymovie.model.ticket.TicketResponse
import cuong.dev.dotymovie.model.ticket.TicketRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TicketApi {
    @GET("ticket/my-ticket")
    suspend fun getTicketsByUser(@Query("userId") userId: Int): Response<List<TicketResponse>>

    @POST("ticket/create")
    suspend fun create(@Body request: TicketRequest): Response<Unit>
}