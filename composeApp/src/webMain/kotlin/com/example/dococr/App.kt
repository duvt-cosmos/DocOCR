package com.example.dococr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dococr.presentation.DocOcrViewModel
import com.example.dococr.ui.ResultPanel
import com.example.dococr.ui.UploadPanel

@Composable
fun App() {
    MaterialTheme {
        val viewModel = viewModel { DocOcrViewModel() }
        val state by viewModel.state.collectAsState()

        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .safeContentPadding()
                .fillMaxSize()
                .padding(16.dp)
        ) {
            UploadPanel(
                onUploadClicked = { viewModel.processDocumentMock() },
                modifier = Modifier.weight(1f)
            )
            ResultPanel(
                state = state,
                modifier = Modifier.weight(1f)
            )
        }
    }
}