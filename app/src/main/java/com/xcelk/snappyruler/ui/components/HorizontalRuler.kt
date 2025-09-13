package com.xcelk.snappyruler.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalRuler(
    modifier: Modifier = Modifier,
    canvasWidth: Float,
    zoom: Float,
    panX: Float
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp)
    ) {
        val step = 50f * zoom
        val start = -panX % step

        var x = start

        while (x < size.width) {
            drawLine(
                color = Color.Gray,
                start = Offset(x, size.height),
                end = Offset(x, size.height - 10)
            )

            if (((x + panX) / step).toInt() % 5 == 0) {
                drawLine(
                    color = Color.Black,
                    start = Offset(x, size.height),
                    end = Offset(x, size.height - 0f)
                )
            }

            x += step
        }
    }
}