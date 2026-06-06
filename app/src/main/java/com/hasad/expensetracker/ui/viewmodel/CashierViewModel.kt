package com.hasad.expensetracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasad.expensetracker.data.entity.CashierSession
import com.hasad.expensetracker.data.repository.CashierRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CashierViewModel(private val repository: CashierRepository) : ViewModel() {
    private val _currentSession = MutableStateFlow<CashierSession?>(null)
    val currentSession: StateFlow<CashierSession?> = _currentSession.asStateFlow()

    private val _openSessions = MutableStateFlow<List<CashierSession>>(emptyList())
    val openSessions: StateFlow<List<CashierSession>> = _openSessions.asStateFlow()

    private val _closedSessions = MutableStateFlow<List<CashierSession>>(emptyList())
    val closedSessions: StateFlow<List<CashierSession>> = _closedSessions.asStateFlow()

    private val _isSessionOpen = MutableStateFlow(false)
    val isSessionOpen: StateFlow<Boolean> = _isSessionOpen.asStateFlow()

    init {
        loadOpenSessions()
    }

    private fun loadOpenSessions() {
        viewModelScope.launch {
            repository.getOpenSessions().collect { sessions ->
                _openSessions.value = sessions
                _isSessionOpen.value = sessions.isNotEmpty()
                if (sessions.isNotEmpty()) {
                    _currentSession.value = sessions.first()
                }
            }
        }
    }

    fun openSession(session: CashierSession) {
        viewModelScope.launch {
            val sessionId = repository.openSession(session)
            _currentSession.value = session.copy(id = sessionId)
            _isSessionOpen.value = true
            loadOpenSessions()
        }
    }

    fun closeSession(sessionId: Long) {
        viewModelScope.launch {
            repository.closeSession(sessionId)
            _isSessionOpen.value = false
            _currentSession.value = null
            loadOpenSessions()
            loadClosedSessions()
        }
    }

    fun updateSession(session: CashierSession) {
        viewModelScope.launch {
            repository.updateSession(session)
            _currentSession.value = session
        }
    }

    private fun loadClosedSessions() {
        viewModelScope.launch {
            repository.getClosedSessions().collect { sessions ->
                _closedSessions.value = sessions
            }
        }
    }

    fun getSessionsByDate(date: String) {
        viewModelScope.launch {
            repository.getSessionsByDate(date).collect { sessions ->
                _closedSessions.value = sessions
            }
        }
    }
}
