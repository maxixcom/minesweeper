package minesweeper.domain.utils

import minesweeper.domain.entity.CellType
import minesweeper.domain.entity.Coordinates
import minesweeper.domain.entity.Game

fun Game.plantMines() = mines.forEach { board.plant(it, CellType.Mine) }

fun Game.hintMines() {
    val hintPoints = mutableSetOf<Coordinates>()

    for (m in mines) {
        board.cellNeighbors(m)
            .map {
                it
            }
            .filter { board.cellType(it) == CellType.Empty }
            .forEach { hintPoints.add(it) }
    }

    hintPoints.forEach { cell ->
        val n = board.cellNeighbors(cell).count { board.cellType(it) == CellType.Mine }
        board.plant(cell, CellType.Hint(n))
    }
}

fun Game.showMinesOnScreen() = mines.forEach { screen.plant(it, CellType.Mine) }

fun Game.showOnScreen(coordinates: Coordinates) {
    screen.data[coordinates.y][coordinates.x] = board.data[coordinates.y][coordinates.x]
}

