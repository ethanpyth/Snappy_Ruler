package com.xcelk.snappyruler.domain.snap

import com.xcelk.snappyruler.model.Point
import java.lang.Math.toDegrees
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.roundToInt
import kotlin.math.sin

object SnapEngine {
    private val snapAngles = listOf(0f, 30f, 45f, 60f, 90f)

    fun snapToGrid(point: Point, gridSize: Float): Point {
        val snappedX = (point.x / gridSize).roundToInt() * gridSize
        val snappedY = (point.y / gridSize).roundToInt() * gridSize
        return Point(snappedX, snappedY)
    }

    fun snapToAngle(start: Point, end: Point): Point {
        val dx = end.x - start.x
        val dy = end.y - start.y
        val angle = atan2(dy.toDouble(), dx.toDouble())

        val nearest = snapAngles.minByOrNull { abs(it - toDegrees(angle)) } ?: angle
        val distance = hypot(dx.toDouble(), dy.toDouble())
        val rad = Math.toRadians(nearest.toDouble())
        val snappedX = start.x + (distance * cos(rad)).toFloat()
        val snappedY = start.y + (distance * sin(rad)).toFloat()
        return Point(snappedX, snappedY)
    }

    fun snapToPoint(target: Point, candidates: List<Point>, threshold: Float): Point? {
        for (candidate in candidates) {
            val distance = hypot((candidate.x - target.x).toDouble(), (candidate.y - target.y).toDouble()).toFloat()
            if (distance <= threshold) {
                return candidate
            }
        }
        return null
    }

    fun snapToLine(point: Point, lineStart: Point, lineEnd: Point, threshold: Float): Point? {
        val A = point.x - lineStart.x
        val B = point.y - lineStart.y
        val C = lineEnd.x - lineStart.x
        val D = lineEnd.y - lineStart.y

        val dot = A * C + B * D
        val lenSq = C * C + D * D
        val param = if (lenSq != 0f) dot / lenSq else -1f

        val nearestX: Float
        val nearestY: Float

        when {
            param < 0 -> {
                nearestX = lineStart.x
                nearestY = lineStart.y
            }
            param > 1 -> {
                nearestX = lineEnd.x
                nearestY = lineEnd.y
            }
            else -> {
                nearestX = lineStart.x + param * C
                nearestY = lineStart.y + param * D
            }
        }

        val distance = hypot((nearestX - point.x).toDouble(), (nearestY - point.y).toDouble()).toFloat()
        return if (distance <= threshold) Point(nearestX, nearestY) else null
    }

    fun snapToCircle(point: Point, circleCenter: Point, radius: Float, threshold: Float): Point? {
        val dx = point.x - circleCenter.x
        val dy = point.y - circleCenter.y
        val distance = hypot(dx.toDouble(), dy.toDouble()).toFloat()
        val distanceToCircumference = kotlin.math.abs(distance - radius)
        return if (distanceToCircumference <= threshold) {
            val scale = radius / distance
            Point(circleCenter.x + dx * scale, circleCenter.y + dy * scale)
        } else {
            null
        }
    }

    fun snapToIntersection(point: Point, lines: List<Pair<Point, Point>>, threshold: Float): Point? {
        val intersections = mutableListOf<Point>()
        for (i in lines.indices) {
            for (j in i + 1 until lines.size) {
                val intersection = getLineIntersection(lines[i].first, lines[i].second, lines[j].first, lines[j].second)
                if (intersection != null) {
                    intersections.add(intersection)
                }
            }
        }
        return snapToPoint(point, intersections, threshold)
    }

    fun getLineIntersection(p1: Point, p2: Point, p3: Point, p4: Point): Point? {
        val denom = (p1.x - p2.x) * (p3.y - p4.y) - (p1.y - p2.y) * (p3.x - p4.x)
        if (denom == 0f) return null // Parallel lines

        val x = ((p1.x * p2.y - p1.y * p2.x) * (p3.x - p4.x) - (p1.x - p2.x) * (p3.x * p4.y - p3.y * p4.x)) / denom
        val y = ((p1.x * p2.y - p1.y * p2.x) * (p3.y - p4.y) - (p1.y - p2.y) * (p3.x * p4.y - p3.y * p4.x)) / denom
        return Point(x, y)
    }
}