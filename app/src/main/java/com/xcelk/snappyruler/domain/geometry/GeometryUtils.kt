package com.xcelk.snappyruler.domain.geometry

import com.xcelk.snappyruler.model.Point
import java.lang.Math.toDegrees
import kotlin.math.atan2
import kotlin.math.hypot

object GeometryUtils {
    fun distance(a: Point, b: Point): Float {
        return hypot((b.x - a.x).toDouble(), (b.y - a.y).toDouble()).toFloat()
    }

    fun angle(a: Point, b: Point): Float =
        toDegrees(atan2((b.x - a.x).toDouble(), (b.y - a.y).toDouble())).toFloat()

    fun midpoint(a: Point, b: Point): Point =
        Point((a.x + b.x) / 2, (a.y + b.y) / 2)

    fun translate(point: Point, dx: Float, dy: Float): Point =
        Point(point.x + dx, point.y + dy)

    fun rotate(point: Point, center: Point, angleDegrees: Float): Point {
        val angleRadians = Math.toRadians(angleDegrees.toDouble())
        val cosTheta = kotlin.math.cos(angleRadians)
        val sinTheta = kotlin.math.sin(angleRadians)
        val translatedX = point.x - center.x
        val translatedY = point.y - center.y
        val rotatedX = translatedX * cosTheta - translatedY * sinTheta
        val rotatedY = translatedX * sinTheta + translatedY * cosTheta
        return Point((rotatedX + center.x).toFloat(), (rotatedY + center.y).toFloat())
    }

    fun scale(point: Point, center: Point, scaleFactor: Float): Point {
        val translatedX = point.x - center.x
        val translatedY = point.y - center.y
        val scaledX = translatedX * scaleFactor
        val scaledY = translatedY * scaleFactor
        return Point((scaledX + center.x), (scaledY + center.y))
    }

    fun angleBetweenPoints(a: Point, b: Point): Float {
        return toDegrees(atan2((b.y - a.y).toDouble(), (b.x - a.x).toDouble())).toFloat()
    }
}