package com.helpdesksupport.app.data.database.dao

import androidx.room.*
import com.helpdesksupport.app.data.database.entity.SupportTicketEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Support Ticket entity
 */
@Dao
interface SupportTicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: SupportTicketEntity): Long

    @Query("SELECT * FROM support_tickets WHERE id = :ticketId")
    suspend fun getTicketById(ticketId: Long): SupportTicketEntity?

    @Query("SELECT * FROM support_tickets WHERE userId = :userId ORDER BY createdAt DESC")
    fun getUserTickets(userId: Long): Flow<List<SupportTicketEntity>>

    @Query("SELECT * FROM support_tickets WHERE status = :status ORDER BY createdAt DESC")
    fun getTicketsByStatus(status: String): Flow<List<SupportTicketEntity>>

    @Query("SELECT * FROM support_tickets ORDER BY priority DESC, createdAt DESC")
    fun getAllTickets(): Flow<List<SupportTicketEntity>>

    @Update
    suspend fun updateTicket(ticket: SupportTicketEntity)

    @Delete
    suspend fun deleteTicket(ticket: SupportTicketEntity)

    @Query("SELECT COUNT(*) FROM support_tickets WHERE userId = :userId AND status = :status")
    suspend fun getTicketCountByStatus(userId: Long, status: String): Int
}
