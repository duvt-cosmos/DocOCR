package com.example.dococr.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dococr.domain.OcrState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DocOcrViewModel : ViewModel() {
    private val _state = MutableStateFlow<OcrState>(OcrState.Idle)
    val state: StateFlow<OcrState> = _state.asStateFlow()

    fun processDocumentMock() {
        viewModelScope.launch {
            _state.value = OcrState.Processing
            delay(2000) // Simulate network call
            _state.value = OcrState.Success("This is the extracted text from AWS Textract.\n\nIt features multiple lines and paragraphs.")
        }
    }
    
    fun reset() {
        _state.value = OcrState.Idle
    }
}
