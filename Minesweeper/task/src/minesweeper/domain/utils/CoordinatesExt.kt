package minesweeper.domain.utils

import minesweeper.domain.entity.Coordinates

fun Coordinates.neighbors(): List<Coordinates> =
    (-1..1).flatMap { dx ->
        (-1..1).map { dy ->
            Coordinates(x + dx, y + dy)
        }
    }.filter { it != this }
