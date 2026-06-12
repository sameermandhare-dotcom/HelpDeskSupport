package com.helpdesksupport.app.di

import android.content.Context
import androidx.room.Room
import com.helpdesksupport.app.data.api.HelpDeskApiService
import com.helpdesksupport.app.data.database.HelpDeskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Hilt dependency injection module for data layer
 */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val BASE_URL = "https://api.helpdesksupport.com/"

    /**
     * Provides Room database instance
     */
    @Singleton
    @Provides
    fun provideHelpDeskDatabase(
        @ApplicationContext context: Context
    ): HelpDeskDatabase {
        return Room.databaseBuilder(
            context,
            HelpDeskDatabase::class.java,
            "helpdesk_database"
        ).build()
    }

    /**
     * Provides User DAO
     */
    @Singleton
    @Provides
    fun provideUserDao(database: HelpDeskDatabase) = database.userDao()

    /**
     * Provides Call History DAO
     */
    @Singleton
    @Provides
    fun provideCallHistoryDao(database: HelpDeskDatabase) = database.callHistoryDao()

    /**
     * Provides AI Feedback DAO
     */
    @Singleton
    @Provides
    fun provideAIFeedbackDao(database: HelpDeskDatabase) = database.aiFeedbackDao()

    /**
     * Provides Support Ticket DAO
     */
    @Singleton
    @Provides
    fun provideSupportTicketDao(database: HelpDeskDatabase) = database.supportTicketDao()

    /**
     * Provides OkHttpClient with logging interceptor
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * Provides Retrofit instance
     */
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides HelpDesk API Service
     */
    @Singleton
    @Provides
    fun provideHelpDeskApiService(retrofit: Retrofit): HelpDeskApiService {
        return retrofit.create(HelpDeskApiService::class.java)
    }
}
