package minesweeper.console

import minesweeper.domain.AppRunner
import minesweeper.domain.entity.CellType
import minesweeper.domain.services.GameService
import minesweeper.domain.utils.cellType

class ConsoleRunner : AppRunner {
    private val gameService: GameService = Application.gameService
    override fun run() {
        val numberOfMines = readNumberOfMines()

        val game = gameService.createGame(9, 9, numberOfMines)

        while (true) {
            game.board.print()
            val coordinates = readPlayerInput { c ->
                if (game.board.cellType(c) is CellType.Hint) {
                    println("There is a number here!")
                    false
                } else {
                    true
                }
            }
            gameService.playerMarksMine(game, coordinates)
            if (gameService.areAllMinesFound(game)) {
                game.board.print()
                printAllMinesFound()
                break
            }
        }
    }
}
