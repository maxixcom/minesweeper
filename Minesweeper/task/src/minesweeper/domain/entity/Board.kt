package minesweeper.domain.entity

class Board(val width: Int = 9, val height: Int = 9, fillType: CellType = CellType.Empty) {
    val data = List(height) {
        MutableList(width) {
            fillType
        }
    }
}
