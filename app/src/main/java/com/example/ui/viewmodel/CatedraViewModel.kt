package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Catedra
import com.example.data.repository.CatedraRepository
import com.example.util.GeminiClient
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CatedraViewModel(private val repository: CatedraRepository) : ViewModel() {
    val catedras: StateFlow<List<Catedra>> = repository.getCatedras()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveCatedra(catedra: Catedra) {
        viewModelScope.launch {
            repository.saveCatedra(catedra)
        }
    }

    fun deleteCatedra(id: String) {
        viewModelScope.launch {
            repository.deleteCatedra(id)
        }
    }

    fun generateSummary(catedra: Catedra) {
        viewModelScope.launch {
            val summary = GeminiClient.summarize(catedra.description)
            if (summary != null) {
                repository.saveCatedra(catedra.copy(aiSummary = summary))
            }
        }
    }
}
