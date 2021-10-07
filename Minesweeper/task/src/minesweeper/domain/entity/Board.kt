package minesweeper.domain.entity

class Board(val width: Int = 9, val height: Int = 9) {
    val data = List<MutableList<CellType>>(height) {
        MutableList(width) {
            CellType.Free
        }
    }
}
