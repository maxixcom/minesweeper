package minesweeper.console

import minesweeper.domain.AppRunner
import minesweeper.domain.services.GameService

class ConsoleRunner : AppRunner {
    private val gameService: GameService = Application.gameService
    override fun run() {
        val numberOfMines = readNumberOfMines()

        val board = gameService.createBoard(9, 9, numberOfMines)

        board.print()
    }
}
