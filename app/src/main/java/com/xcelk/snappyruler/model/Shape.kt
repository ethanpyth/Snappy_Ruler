package com.xcelk.snappyruler.model

sealed class Shape{
    data class Line(val start: Point, val end: Point): Shape()
    data class Circle(val center: Point, val radius: Float): Shape()
    data class Rectangle(val topLeft: Point, val bottomRight: Point): Shape()
}

data class Point(val x: Float, val y: Float)