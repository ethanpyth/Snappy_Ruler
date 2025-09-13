package com.xcelk.snappyruler.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.ShapeLine
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.xcelk.snappyruler.model.Tooltype
import com.xcelk.snappyruler.viewmodel.DrawingViewModel

@Composable
fun ToolPicker(
    modifier: Modifier = Modifier,
    viewModel: DrawingViewModel,
) {
    Row(
        modifier = modifier
            .wrapContentSize()
            .padding(8.dp)
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
        ,
    ) {
        IconButton(
            modifier = Modifier,
            onClick = { viewModel.setActiveTool(Tooltype.RULER) }
        ) {
            Column(
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(bottom = 4.dp),
                    imageVector = Icons.Outlined.ShapeLine,
                    contentDescription = "Ruler Tool"
                )
                Text("Line")
            }
        }

        IconButton(
            modifier = Modifier,
            onClick = { viewModel.setActiveTool(Tooltype.CIRCLE) }
        ) {
            Column(
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(bottom = 4.dp),
                    imageVector = Icons.Outlined.Circle,
                    contentDescription = "Circle Tool"
                )
                Text("Circle")
            }
        }
    }
}