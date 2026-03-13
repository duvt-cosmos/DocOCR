# DocOCR UI Implementation Plan

> **For agentic workers:** REQUIRED: Use superpowers:subagent-driven-development (if subagents available) or superpowers:executing-plans to implement this plan. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build the Split View KMP frontend for DocOCR, including file selection, a mocked server response, and result display.

**Architecture:** A Compose Multiplatform UI (`commonMain`) featuring a two-panel layout. State is managed via a ViewModel. Network calls are mocked for this phase.

**Tech Stack:** Kotlin, Compose Multiplatform, Material 3, Coroutines.

---

## Chunk 1: Domain Models and State

**Files:**
- Create: `composeApp/src/commonMain/kotlin/com/example/dococr/domain/OcrState.kt`
- Create: `composeApp/src/commonMain/kotlin/com/example/dococr/presentation/DocOcrViewModel.kt`

- [ ] **Step 1: Create State Models**
Create `OcrState.kt` representing the UI state (Idle, Processing, Success with text, Error).
```kotlin
package com.example.dococr.domain

sealed class OcrState {
    data object Idle : OcrState()
    data object Processing : OcrState()
    data class Success(val extractedText: String) : OcrState()
    data class Error(val message: String) : OcrState()
}
```

- [ ] **Step 2: Create ViewModel with Mocked Action**
Create `DocOcrViewModel.kt` using `androidx.lifecycle.ViewModel`. Add a method to mock an upload and extraction process (delay 2 seconds, emit Success).
```kotlin
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
```

- [ ] **Step 3: Commit State & ViewModel**
```bash
git add composeApp/src/commonMain/kotlin/com/example/dococr/domain/
git add composeApp/src/commonMain/kotlin/com/example/dococr/presentation/
git commit -m "feat: add OCR state and ViewModel with mocked processing"
```

## Chunk 2: UI Components

**Files:**
- Create: `composeApp/src/commonMain/kotlin/com/example/dococr/ui/UploadPanel.kt`
- Create: `composeApp/src/commonMain/kotlin/com/example/dococr/ui/ResultPanel.kt`

- [ ] **Step 1: Create ResultPanel.kt**
A composable taking `OcrState` as input. If Success, show a scrollable text area. If Processing, show a CircularProgressIndicator. If Idle, show a placeholder.
*(Note: Use standard Material3 components `Text`, `CircularProgressIndicator`, `Box`)*

- [ ] **Step 2: Create UploadPanel.kt**
A composable with a large Button/Box acting as the dropzone/upload trigger. Pass a lambda `onUploadClicked: () -> Unit`.

- [ ] **Step 3: Commit UI Components**
```bash
git add composeApp/src/commonMain/kotlin/com/example/dococr/ui/
git commit -m "feat: add Upload and Result UI panels"
```

## Chunk 3: Main App Integration

**Files:**
- Modify: `composeApp/src/commonMain/kotlin/com/example/dococr/App.kt`

- [ ] **Step 1: Modify App.kt to use Split View**
Remove the existing boilerplate `Greeting` code.
Instantiate the `DocOcrViewModel`. Use a `Row` to split the screen 50/50.
Left side: `UploadPanel(onUploadClicked = { viewModel.processDocumentMock() })`
Right side: `ResultPanel(state = state.value)` (collect state from viewmodel).

*(Note: Use `androidx.lifecycle.viewmodel.compose.viewModel` or simply remember a generic instance for now if dependencies are tricky).*

- [ ] **Step 2: Test rendering via Wasm**
Run: `./gradlew :composeApp:wasmJsBrowserDevelopmentRun`
Expectation: The browser opens, showing the split layout. Clicking the upload area shows a spinner on the right, then text appears after 2 seconds.

- [ ] **Step 3: Commit App integration**
```bash
git add composeApp/src/commonMain/kotlin/com/example/dococr/App.kt
git commit -m "feat: integrate Split View layout into main App"
```
