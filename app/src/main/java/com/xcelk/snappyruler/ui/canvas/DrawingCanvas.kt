package com.xcelk.snappyruler.ui.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xcelk.snappyruler.domain.geometry.GeometryUtils
import com.xcelk.snappyruler.domain.geometry.TransformUtils
import com.xcelk.snappyruler.model.Point
import com.xcelk.snappyruler.model.Shape
import com.xcelk.snappyruler.model.Tooltype
import com.xcelk.snappyruler.viewmodel.DrawingViewModel
import java.lang.Math.toDegrees
import kotlin.math.*

@Composable
fun DrawingCanvas(
    modifier : Modifier = Modifier,
    viewModel: DrawingViewModel
) {
    var startPoint by remember { mutableStateOf<Offset?>(null) }
    var currentPoint by remember { mutableStateOf<Offset?>(null) }
    val interaction by viewModel.interactionState.collectAsStateWithLifecycle()
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        val canvasPos = TransformUtils.toCanvasCoord(
                            offset,
                            interaction
                        )
                        startPoint = canvasPos
                    },
                    onDragEnd = {
                        if (startPoint != null && currentPoint != null) {
                            viewModel.addShape(startPoint!!, currentPoint!!)
                        }
                        startPoint = null
                        currentPoint = null
                    },
                    onDrag = { change, _ ->
                        val canvasPos = TransformUtils.toCanvasCoord(
                            change.position,
                            interaction
                        )
                        currentPoint = canvasPos
                    }
                )
            },
    ) {
        viewModel.shapes.value.forEach { shape ->
            when (shape) {
                is Shape.Line -> {
                    val start = TransformUtils.toScreenCoord(
                        Offset(shape.start.x, shape.start.y),
                        interaction
                    )
                    val end = TransformUtils.toScreenCoord(
                        Offset(shape.end.x, shape.end.y),
                        interaction
                    )
                    drawLine(
                        color = Color.Black,
                        start = start,
                        end = end,
                        strokeWidth = 5f
                    )
                }

                is Shape.Circle -> {
                    val center = TransformUtils.toScreenCoord(
                        Offset(shape.center.x, shape.center.y),
                        interaction
                    )
                    val radius = shape.radius * interaction.zoom
                    drawCircle(
                        color = Color.Black,
                        center = center,
                        radius = radius,
                        style = Stroke(width = 5f)
                    )
                }

                is Shape.Rectangle -> {

                }
            }
        }

        if (startPoint != null || currentPoint != null) {
            when(viewModel.activeTool.value) {
                Tooltype.RULER -> {
                    val snapped = viewModel.snapLine(startPoint!!, currentPoint!!)
                    val start = TransformUtils.toScreenCoord(
                        startPoint!!,
                        interaction
                    )
                    val end = TransformUtils.toScreenCoord(
                        snapped,
                        interaction
                    )
                    drawLine(
                        color = Color.LightGray,
                        start = start,
                        end = end,
                        strokeWidth = 3f
                    )
                    val dx = (snapped.x - (startPoint?.x ?: 0f))
                    val dy = (snapped.y - (startPoint?.y ?: 0f))
                    val length = hypot(dx, dy)
                    val angle = atan2(dy, dx)

                    withTransform(
                        {
                            translate(left = startPoint!!.x, top = startPoint!!.y)
                            rotate(toDegrees(angle.toDouble()).toFloat())
                        }
                    ) {
                        drawRect(
                            color = Color.Gray.copy(alpha = 0.5f),
                            topLeft = Offset(x = 0f, y = -30f),
                            size = Size(length, 60f)
                        )
                    }

                }

                Tooltype.CIRCLE -> {
                    val  center = TransformUtils.toScreenCoord(
                        startPoint ?: Offset.Zero,
                        interaction
                    )
                    val radius = GeometryUtils.distance(
                        Point(startPoint?.x ?: 0f, startPoint?.y ?: 0f),
                        Point(currentPoint?.x ?: 0f, currentPoint?.y ?: 0f)
                    ) * interaction.zoom
                    drawCircle(
                        color = Color.LightGray,
                        center = center,
                        radius = radius,
                        style = Stroke(width = 3f)
                    )
                }

                Tooltype.PROTRACTOR -> TODO()
                Tooltype.LEVEL -> TODO()
                Tooltype.COMPASS -> TODO()
                Tooltype.SET_SQUARE -> {
                    if (startPoint != null && currentPoint != null) {
                        val dx = currentPoint!!.x - startPoint!!.x
                        val dy = currentPoint!!.y - startPoint!!.y
                        val angle = atan2(dy, dx)

                        val length = sqrt(dx * dx + dy * dy)
                        val rectSize = Size(length, length)

                        withTransform(
                            {
                                translate(left = startPoint!!.x, top = startPoint!!.y)
                                rotate(toDegrees(angle.toDouble()).toFloat(), pivot = Offset.Zero)
                            }
                        ) {
                            drawRect(
                                color = Color.Gray.copy(alpha = 0.5f),
                                topLeft = Offset.Zero,
                                size = rectSize,
                                style = Stroke(width = 3f)
                            )
                        }
                    }
                }
                Tooltype.CALIPER -> TODO()
                Tooltype.NONE -> TODO()
            }
        }
    }
}