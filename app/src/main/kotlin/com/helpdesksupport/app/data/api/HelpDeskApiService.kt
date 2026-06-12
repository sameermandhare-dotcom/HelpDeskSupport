package com.helpdesksupport.app.data.api

import com.helpdesksupport.app.data.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Retrofit API interface for Help Desk Support backend
 */
interface HelpDeskApiService {

    // Call Management endpoints
    @POST("/api/v1/calls/initiate")
    suspend fun initiateCall(@Body request: CallRequest): Response<CallResponse>

    @GET("/api/v1/calls/{callId}/status")
    suspend fun getCallStatus(@Path("callId") callId: String): Response<CallResponse>

    @POST("/api/v1/calls/{callId}/end")
    suspend fun endCall(@Path("callId") callId: String): Response<Void>

    @GET("/api/v1/calls/history/{userId}")
    suspend fun getCallHistory(@Path("userId") userId: Long): Response<List<CallHistoryResponse>>

    // AI Feedback endpoints
    @GET("/api/v1/feedback/{callId}")
    suspend fun getCallFeedback(@Path("callId") callId: String): Response<AIFeedbackResponse>

    @POST("/api/v1/feedback/submit")
    suspend fun submitFeedback(@Body feedback: AIFeedbackResponse): Response<Void>

    // Support Ticket endpoints
    @POST("/api/v1/tickets/create")
    suspend fun createTicket(@Body request: SupportTicketRequest): Response<SupportTicketResponse>

    @GET("/api/v1/tickets/{ticketId}")
    suspend fun getTicket(@Path("ticketId") ticketId: String): Response<SupportTicketResponse>

    @GET("/api/v1/tickets/user/{userId}")
    suspend fun getUserTickets(@Path("userId") userId: Long): Response<List<SupportTicketResponse>>

    @PATCH("/api/v1/tickets/{ticketId}/status")
    suspend fun updateTicketStatus(
        @Path("ticketId") ticketId: String,
        @Query("status") status: String
    ): Response<SupportTicketResponse>

    // User Profile endpoints
    @GET("/api/v1/users/{userId}/profile")
    suspend fun getUserProfile(@Path("userId") userId: Long): Response<UserProfile>

    @PUT("/api/v1/users/{userId}/profile")
    suspend fun updateUserProfile(
        @Path("userId") userId: Long,
        @Body profile: UserProfile
    ): Response<UserProfile>

    // Authentication endpoints
    @POST("/api/v1/auth/login")
    suspend fun login(@Body credentials: LoginRequest): Response<LoginResponse>

    @POST("/api/v1/auth/logout")
    suspend fun logout(): Response<Void>
}

/**
 * Login request model
 */
data class LoginRequest(
    val email: String,
    val password: String
)

/**
 * Login response model
 */
data class LoginResponse(
    val token: String,
    val userId: Long,
    val user: UserProfile
)
