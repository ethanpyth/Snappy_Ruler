package com.xcelk.snappyruler.model

data class IntersectionState(
    val zoom: Float = 1f,
    val panX: Float = 0f,
    val panY: Float = 0f,
    val activeTool: Tooltype = Tooltype.NONE
)