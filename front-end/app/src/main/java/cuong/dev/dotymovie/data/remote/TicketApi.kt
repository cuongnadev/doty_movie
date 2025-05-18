package cuong.dev.dotymovie.data.remote

import cuong.dev.dotymovie.model.ticket.TicketResponse
import cuong.dev.dotymovie.model.ticket.CreateTicketRequest
import cuong.dev.dotymovie.model.ticket.CreateTicketResponse
import cuong.dev.dotymovie.model.ticket.TicketStatusResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TicketApi {
    @GET("ticket/my-ticket")
    suspend fun getTicketsByUser(@Query("userId") userId: Int): Response<List<TicketResponse>>

    @POST("ticket/create")
    suspend fun create(@Body request: CreateTicketRequest): Response<CreateTicketResponse>

    @PATCH("ticket/cancel/{ticketCode}")
    suspend fun cancelTicket(@Path("ticketCode") ticketCode: String): Response<TicketResponse>

    @GET("ticket/status/{ticketCode}")
    suspend fun getStatus(@Path("ticketCode") ticketCode: String): Response<TicketStatusResponse>

    @DELETE("ticket/delete/{ticketCode}")
    suspend fun delete(@Path("ticketCode") ticketCode: String): Response<Unit>
}