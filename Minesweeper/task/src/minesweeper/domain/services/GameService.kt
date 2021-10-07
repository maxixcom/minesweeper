package minesweeper.domain.services

import minesweeper.domain.entity.Board

interface GameService {
    fun createBoard(width: Int, height: Int, numberOfMines: Int): Board
}
