package com.xcelk.snappyruler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xcelk.snappyruler.ui.canvas.DrawingCanvas
import com.xcelk.snappyruler.ui.components.HorizontalRuler
import com.xcelk.snappyruler.ui.components.ToolPicker
import com.xcelk.snappyruler.ui.components.VerticalRuler
import com.xcelk.snappyruler.viewmodel.DrawingViewModel

class MainActivity : ComponentActivity() {
    private val viewModel = DrawingViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                val intersectionState by viewModel.interactionState.collectAsStateWithLifecycle()
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(30.dp))
                        VerticalRuler(
                            1000f,
                            zoom = intersectionState.zoom,
                            panY = intersectionState.panY
                        )
                    }

                    Column {
                        HorizontalRuler(
                            canvasWidth = 1000f,
                            zoom = intersectionState.zoom,
                            panX = intersectionState.panX
                        )
                        DrawingCanvas(
                            viewModel = viewModel
                        )
                    }
                }
                ToolPicker(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    viewModel = viewModel
                )
            }
        }
    }
}