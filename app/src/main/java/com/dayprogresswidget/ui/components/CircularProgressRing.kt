package com.dayprogresswidget.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * CircularProgressRing displays a circular ring that fills based on progress.
 * 
 * This composable creates a circular progress indicator with:
 * - A background ring (unfilled portion)
 * - A foreground ring (filled portion based on progress)
 * - Centered text showing the percentage
 * 
 * @param progress Progress value from 0.0 to 1.0
 * @param size Diameter of the circular ring
 * @param strokeWidth Width of the ring stroke
 * @param backgroundColor Color of the unfilled portion
 * @param progressColor Color of the filled portion
 */
@Composable
fun CircularProgressRing(
    progress: Float,
    modifier: Modifier = Modifier,
    size: Dp = 280.dp,
    strokeWidth: Dp = 24.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    progressColor: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Draw the circular progress ring
        Canvas(modifier = Modifier.size(size)) {
            val canvasSize = this.size.minDimension
            val radius = (canvasSize - strokeWidth.toPx()) / 2
            
            // Draw background ring (unfilled portion)
            drawCircle(
                color = backgroundColor,
                radius = radius,
                style = Stroke(width = strokeWidth.toPx())
            )
            
            // Draw progress ring (filled portion)
            // Start angle is -90 degrees (top of circle)
            // Sweep angle is progress * 360 degrees (full circle)
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }
        
        // Display percentage in the center
        Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
