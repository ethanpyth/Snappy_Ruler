package com.xcelk.snappyruler.domain.snap

import com.xcelk.snappyruler.model.Point

sealed class SnapTarget {
    data class GridPoint(val point: Point): SnapTarget()
    data class ShapeAnchor(val point: Point): SnapTarget()
    data class Angle(val vertex: Point, val angle: Float): SnapTarget()
    data class GuideLine(val start: Point, val end: Point): SnapTarget()
}