package com.xcelk.snappyruler.domain.geometry

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.ui.geometry.Offset
import com.xcelk.snappyruler.model.IntersectionState

object TransformUtils {
    fun toCanvasCoord(screen: Offset, interaction: IntersectionState): Offset {
        val x = (screen.x - interaction.panX) / interaction.zoom
        val y = (screen.y - interaction.panY) / interaction.zoom
        return Offset(x, y)
    }

    fun toScreenCoord(canvas: Offset, interaction: IntersectionState): Offset {
        val x = canvas.x * interaction.zoom + interaction.panX
        val y = canvas.y * interaction.zoom + interaction.panY
        return Offset(x, y)
    }
}