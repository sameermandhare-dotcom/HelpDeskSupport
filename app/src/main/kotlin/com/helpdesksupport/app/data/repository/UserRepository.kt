package com.helpdesksupport.app.data.repository

import com.helpdesksupport.app.data.api.HelpDeskApiService
import com.helpdesksupport.app.data.database.dao.UserDao
import com.helpdesksupport.app.data.database.entity.UserEntity
import com.helpdesksupport.app.data.model.UserProfile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository for user profile management
 */
class UserRepository @Inject constructor(
    private val apiService: HelpDeskApiService,
    private val userDao: UserDao
) {

    /**
     * Get user profile from remote and sync locally
     */
    suspend fun getUserProfile(userId: Long): Result<UserProfile> = try {
        val response = apiService.getUserProfile(userId)
        if (response.isSuccessful && response.body() != null) {
            val profile = response.body()!!
            // Sync to local database
            val userEntity = UserEntity(
                id = profile.userId,
                username = profile.username,
                email = profile.email,
                phoneNumber = profile.phoneNumber,
                profileImageUrl = profile.profileImageUrl
            )
            userDao.insertUser(userEntity)
            Result.success(profile)
        } else {
            Result.failure(Exception("Failed to fetch user profile"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    /**
     * Update user profile
     */
    suspend fun updateUserProfile(profile: UserProfile): Result<UserProfile> = try {
        val response = apiService.updateUserProfile(profile.userId, profile)
        if (response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Exception("Failed to update user profile"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    /**
     * Get all users flow for local cache
     */
    fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUsers()
    }

    /**
     * Get user by ID from local database
     */
    suspend fun getUserById(userId: Long): UserEntity? {
        return userDao.getUserById(userId)
    }
}
