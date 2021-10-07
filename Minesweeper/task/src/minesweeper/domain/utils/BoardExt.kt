package minesweeper.domain.utils

import minesweeper.domain.entity.Board
import minesweeper.domain.entity.CellType
import minesweeper.domain.entity.Coordinates

fun Board.cellNeighbors(c: Coordinates): List<Coordinates> =
    c.neighbors()
        .filter { it.x >= 0 && it.y >= 0 && it.x < width && it.y < height }
        .filter { it != c }

fun Board.plant(cell: Coordinates, cellType: CellType) {
    data[cell.y][cell.x] = cellType
}

fun Board.cellType(cell: Coordinates) = data[cell.y][cell.x]

fun Board.plantMines(mines: List<Coordinates>) =
    mines.forEach { plant(it, CellType.Mine) }

fun Board.hintMines(mines: List<Coordinates>) {
    val hintPoints = mutableSetOf<Coordinates>()

    for (m in mines) {
        cellNeighbors(m)
            .map {
                it
            }
            .filter { cellType(it) == CellType.Empty }
            .forEach { hintPoints.add(it) }
    }

    hintPoints.forEach { cell ->
        val n = cellNeighbors(cell).count { cellType(it) == CellType.Mine }
        plant(cell, CellType.Hint(n))
    }
}
