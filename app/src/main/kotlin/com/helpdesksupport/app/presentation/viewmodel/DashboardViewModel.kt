package com.helpdesksupport.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helpdesksupport.app.data.database.entity.CallHistoryEntity
import com.helpdesksupport.app.data.repository.CallRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI State for Dashboard Screen
 */
data class DashboardUiState(
    val totalCalls: Int = 0,
    val totalDuration: Long = 0L,
    val averageDuration: Long = 0L,
    val recentCalls: List<CallHistoryEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel for Dashboard Screen
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val callRepository: CallRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    /**
     * Load dashboard statistics and recent calls
     */
    fun loadDashboardData(userId: Long = 1) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                // Collect call statistics
                val stats = callRepository.getUserCallStatistics(userId)
                _uiState.value = _uiState.value.copy(
                    totalCalls = stats.totalCalls,
                    totalDuration = stats.totalDuration,
                    averageDuration = stats.averageDuration
                )

                // Collect recent calls
                callRepository.getRecentCalls(10).collect { recentCalls ->
                    _uiState.value = _uiState.value.copy(
                        recentCalls = recentCalls,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load dashboard data"
                )
            }
        }
    }

    /**
     * Clear error message
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
