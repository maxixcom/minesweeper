package minesweeper.domain.utils

import minesweeper.domain.entity.Board
import minesweeper.domain.entity.CellType
import minesweeper.domain.entity.Coordinates

/**
 * @deprecated
 */
fun Board.has(c: Coordinates): Boolean {
    return if (height > 0) {
        c.x >= 0 && c.y >= 0 && c.x < width && c.y < height
    } else {
        false
    }
}

fun Board.validCellNeighbors(c: Coordinates): List<Coordinates> =
    c.neighbors()
        .filter { it.x >= 0 && it.y >= 0 && it.x < width && it.y < height }
        .filter { it != c }

fun Board.plant(cell: Coordinates, cellType: CellType) {
    data[cell.y][cell.x] = cellType
}

fun Board.cellType(cell: Coordinates) = data[cell.y][cell.x]
