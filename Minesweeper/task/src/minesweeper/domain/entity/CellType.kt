package minesweeper.domain.entity

sealed class CellType(val character: Char) {
    object Empty : CellType('.')
    object Mine : CellType('X')
    class Hint(digit: Int) : CellType(digit.toString().first())
    object Mark : CellType('*')
}
