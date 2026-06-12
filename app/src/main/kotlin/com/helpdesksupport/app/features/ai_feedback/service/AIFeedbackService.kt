package com.helpdesksupport.app.features.ai_feedback.service

import android.content.Context
import com.helpdesksupport.app.data.database.dao.AIFeedbackDao
import com.helpdesksupport.app.data.database.entity.AIFeedbackEntity
import com.helpdesksupport.app.data.model.AIFeedbackResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Service for AI feedback processing and management
 */
class AIFeedbackService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val aiFeedbackDao: AIFeedbackDao
) {

    /**
     * Process AI feedback from the backend
     */
    suspend fun processFeedback(feedback: AIFeedbackResponse): Result<Unit> = try {
        val feedbackEntity = AIFeedbackEntity(
            callId = feedback.callId.toLong(),
            feedbackText = feedback.feedbackText,
            sentiment = feedback.sentiment,
            confidence = feedback.confidence
        )
        aiFeedbackDao.insertFeedback(feedbackEntity)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    /**
     * Get feedback for a specific call
     */
    suspend fun getCallFeedback(callId: Long) = aiFeedbackDao.getCallFeedbacks(callId)

    /**
     * Analyze call sentiment
     */
    suspend fun analyzeSentiment(callId: Long): String {
        val feedback = aiFeedbackDao.getAverageConfidence(callId)
        return when {
            feedback != null && feedback > 0.7f -> "POSITIVE"
            feedback != null && feedback > 0.4f -> "NEUTRAL"
            else -> "NEGATIVE"
        }
    }

    /**
     * Get recommendations based on feedback
     */
    fun getRecommendations(sentiment: String): List<String> {
        return when (sentiment) {
            "POSITIVE" -> listOf(
                "User satisfied with support",
                "Continue current support approach",
                "Document this positive interaction"
            )
            "NEUTRAL" -> listOf(
                "Follow up with user for clarification",
                "Provide additional resources",
                "Schedule follow-up call"
            )
            "NEGATIVE" -> listOf(
                "Escalate to senior support",
                "Provide alternative solutions",
                "Schedule callback for resolution"
            )
            else -> emptyList()
        }
    }
}
