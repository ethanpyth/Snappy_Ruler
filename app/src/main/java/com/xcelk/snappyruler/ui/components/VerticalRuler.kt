package com.xcelk.snappyruler.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun VerticalRuler(
    canvasHeight: Float,
    zoom: Float,
    panY: Float
) {
    Canvas(modifier = Modifier.width(30.dp).fillMaxWidth()) {
        val step = 50f * zoom
        val start = -panY % step

        var y = start
        while (y < size.height) {
            drawLine(
                color = Color.Gray,
                start = androidx.compose.ui.geometry.Offset(size.width, y),
                end = androidx.compose.ui.geometry.Offset(size.width - 10, y)
            )

            if (((y + panY) / step).toInt() % 5 == 0) {
                drawLine(
                    color = Color.Black,
                    start = androidx.compose.ui.geometry.Offset(size.width, y),
                    end = androidx.compose.ui.geometry.Offset(0f, y)
                )
            }

            y += step
        }
    }
}