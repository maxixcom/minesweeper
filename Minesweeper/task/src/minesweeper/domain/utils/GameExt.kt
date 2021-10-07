package minesweeper.domain.utils

import minesweeper.domain.entity.CellType
import minesweeper.domain.entity.Coordinates
import minesweeper.domain.entity.Game

fun Game.plantMines() = mines.forEach { board.plant(it, CellType.Mine) }

fun Game.hintMines() {
    val hintPoints = mutableSetOf<Coordinates>()

    plantMines()

    for (m in mines) {
        board.validCellNeighbors(m)
            .map {
                it
            }
            .filter { board.cellType(it) == CellType.Empty }
            .forEach { hintPoints.add(it) }
    }

    hintPoints.forEach { cell ->
        val n = board.validCellNeighbors(cell).count { board.cellType(it) == CellType.Mine }
        board.plant(cell, CellType.Hint(n))
    }

    hideMines()
}

fun Game.hideMines() = mines.forEach { board.plant(it, CellType.Empty) }
