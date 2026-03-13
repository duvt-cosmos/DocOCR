package com.example.dococr.domain

sealed class OcrState {
    data object Idle : OcrState()
    data object Processing : OcrState()
    data class Success(val extractedText: String) : OcrState()
    data class Error(val message: String) : OcrState()
}
