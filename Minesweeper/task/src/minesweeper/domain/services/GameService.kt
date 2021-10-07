package minesweeper.domain.services

import minesweeper.domain.entity.Coordinates
import minesweeper.domain.entity.Game

interface GameService {
    fun createGame(width: Int, height: Int, numberOfMines: Int): Game
    fun playerMarksMine(game: Game, coordinate: Coordinates)
    fun areAllMinesFound(game: Game): Boolean
}
