package minesweeper.domain.entity

data class Game(
    val mines: List<Coordinates>,
    val board: Board,
    val marks: MutableList<Coordinates> = mutableListOf(),
)
