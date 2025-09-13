package com.xcelk.snappyruler.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.xcelk.snappyruler.domain.geometry.GeometryUtils
import com.xcelk.snappyruler.domain.snap.SnapEngine
import com.xcelk.snappyruler.model.IntersectionState
import com.xcelk.snappyruler.model.Point
import com.xcelk.snappyruler.model.Shape
import com.xcelk.snappyruler.model.Tooltype
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.cos
import kotlin.math.sin

class DrawingViewModel : ViewModel() {
    private val _shapes = MutableStateFlow<List<Shape>>(emptyList())
    val shapes = _shapes.asStateFlow()

    private val _activeTool = MutableStateFlow(Tooltype.RULER)
    val activeTool = _activeTool.asStateFlow()

    private val _intersectionState = MutableStateFlow(IntersectionState())
    val interactionState = _intersectionState.asStateFlow()

    fun setActiveTool(tool: Tooltype) {
        _activeTool.value = tool
    }

    fun addShape(start: Offset, end: Offset) {
        when (_activeTool.value) {
            Tooltype.RULER -> {
                val snapped = SnapEngine.snapToAngle(
                    Point(start.x, start.y),
                    Point(end.x, end.y)
                )
                val newLine = Shape.Line(
                    start = Point(start.x, start.y),
                    end = Point(snapped.x, snapped.y)
                )
                _shapes.value = _shapes.value + newLine
            }

            Tooltype.PROTRACTOR -> {
                val angle = GeometryUtils.angleBetweenPoints(
                    Point(start.x, start.y),
                    Point(end.x, end.y)
                )

                val length = 100f
                val rad = Math.toRadians(angle.toDouble())
                val angleEnd = Point(
                    start.x + (length * cos(rad)).toFloat(),
                    start.y + (length * sin(rad)).toFloat()
                )

                val angleLine = Shape.Line(
                    start = Point(start.x, start.y),
                    end = angleEnd
                )

                _shapes.value = _shapes.value + angleLine
            }

            Tooltype.LEVEL -> {

            }

            Tooltype.COMPASS -> {
                val radius = GeometryUtils.distance(
                    Point(start.x, start.y),
                    Point(end.x, end.y)
                )

                val circle = Shape.Circle(
                    Point(start.x, start.y),
                    radius
                )

                _shapes.value = _shapes.value + circle
            }

            Tooltype.SET_SQUARE -> {

            }

            Tooltype.CALIPER -> {
                val newLine = Shape.Line(
                    start = Point(start.x, start.y),
                    end = Point(end.x, end.y)
                )
                _shapes.value = _shapes.value + newLine
            }

            Tooltype.NONE -> {

            }

            Tooltype.CIRCLE -> {
                val radius = GeometryUtils.distance(
                    Point(start.x, start.y),
                    Point(end.x, end.y)
                )

                val circle = Shape.Circle(
                    Point(start.x, start.y),
                    radius
                )

                _shapes.value = _shapes.value + circle
            }
        }
    }

    fun snapLine(start: Offset, end: Offset): Offset {
        val point = SnapEngine.snapToAngle(Point(start.x, start.y), Point(end.x, end.y))

        return Offset(point.x, point.y)
    }

    fun updateZoom(zoom: Float, panX: Float, panY: Float) {
        _intersectionState.value = _intersectionState.value.copy(zoom = zoom,)
    }

    fun updatePan(panX: Float, panY: Float) {
        _intersectionState.value = _intersectionState.value.copy(panX = panX, panY = panY)
    }
}