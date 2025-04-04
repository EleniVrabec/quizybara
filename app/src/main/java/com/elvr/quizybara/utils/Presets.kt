package com.elvr.quizybara.utils

import com.elvr.quizybara.R
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter

import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import java.util.concurrent.TimeUnit

object Presets {
    fun festive(): List<Party> {
        return listOf(
            Party(
                speed = 0f,
                maxSpeed = 30f,
                damping = 0.9f,
                spread = 360,
                colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
                position = Position.Relative(0.5, 0.3),
                shapes = listOf(Shape.Square, Shape.Square)
            )
        )
    }
    fun fireworks(): List<Party> {
        return listOf(
            Party(
                speed = 20f,
                maxSpeed = 30f,
                damping = 0.9f,
                spread = 360,
                colors = listOf(
                    0xFFFF1744.toInt(), // Bright Red
                    0xFF651FFF.toInt(), // Purple
                    0xFF00E5FF.toInt(), // Neon Blue
                    0xFFFFEA00.toInt(), // Bright Yellow
                    0xFF00E676.toInt(), // Bright Green
                    0xFFFF9100.toInt(), // Orange
                    0xFFEA80FC.toInt()  // Light Purple
                ),

                        // colors = listOf(0xFFE91E63.toInt(), 0xFF2196F3.toInt(), 0xFF4CAF50.toInt(), 0xFFFFC107.toInt()),
                emitter = Emitter(duration = 300, TimeUnit.MILLISECONDS).max(150),
                position = Position.Relative(0.5, 0.5),
                shapes = listOf(Shape.Circle),

            )
        )
    }
    fun victory(): List<Party> {
        return listOf(
            Party(
                speed = 10f,
                maxSpeed = 15f,
                spread = 90,
                colors = listOf(0xFF4CAF50.toInt(), 0xFFCDDC39.toInt()),
                emitter = Emitter(duration = 2, TimeUnit.SECONDS).perSecond(460),
                position = Position.Relative(0.5, 1.0),
                shapes = listOf(Shape.Square)
            )
        )
    }

    fun balloons(): List<Party> {
        return listOf(
            Party(
                speed = 10f,
                maxSpeed = 15f,
                damping = 0.9f,
                angle = 1270, // Go upward
                spread = 360,
                colors = listOf(
                    0xFFFFA500.toInt(), // Orange
                    0xFFFF69B4.toInt(), // Hot Pink
                    0xFF87CEFA.toInt(), // Sky Blue
                    0xFF98FB98.toInt(), // Pale Green
                    0xFFFFFF00.toInt()  // Yellow
                ),

                emitter = Emitter(duration = 3, TimeUnit.SECONDS).perSecond(25),
                position = Position.Relative(0.5, 0.5),
                shapes = listOf(Shape.Circle),
                size = listOf(Size.LARGE)
            )
        )
    }

  /*  fun balloons(): List<Party> {
        val colors = listOf(
            0xFFFFA500.toInt(), // Orange
            0xFFFF69B4.toInt(), // Hot Pink
            0xFF87CEFA.toInt(), // Sky Blue
            0xFF98FB98.toInt(), // Pale Green
            0xFFFFFF00.toInt()  // Yellow
        )

        val positions = listOf(0.0, 0.25, 0.5, 0.75, 1.0) // Cover left to right

        return positions.map { x ->
            Party(
                speed = 2f,
                maxSpeed = 5f,
                damping = 0.9f,
                angle = 1270, // Go upward
                spread = 360,
                colors = colors,
                emitter = Emitter(duration = 3, TimeUnit.SECONDS).perSecond(25),
                position = Position.Relative(0.5, 0.5), // Across bottom
                shapes = listOf(Shape.Circle),
                //shapes = listOf(Shape.DrawableShape(R.drawable.))

                        size = listOf(Size.LARGE)
            )
        }
    }*/


}
