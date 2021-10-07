package minesweeper.console

import minesweeper.console.input.EndCommand
import minesweeper.console.input.FreeCommand
import minesweeper.console.input.MineCommand
import minesweeper.domain.AppRunner
import minesweeper.domain.entity.CellType
import minesweeper.domain.exceptions.StepOnMineException
import minesweeper.domain.services.GameService
import minesweeper.domain.utils.cellType
import minesweeper.domain.utils.showMinesOnScreen

class ConsoleRunner : AppRunner {
    private val gameService: GameService = Application.gameService
    override fun run() {
        val numberOfMines = readNumberOfMines()

        val game = gameService.createGame(9, 9, numberOfMines)

        while (true) {
            game.screen.print()
            val command = readInputCommand {
                if (it is FreeCommand && game.screen.cellType(it.coordinates) is CellType.Hint) {
                    printThereIsANumberHere()
                    false
                } else if (it is MineCommand && game.screen.cellType(it.coordinates) is CellType.Hint) {
                    printThereIsANumberHere()
                    false
                } else {
                    true
                }
            }
            try {
                if (command is MineCommand) {
                    gameService.playerMarksMine(game, command.coordinates)
                } else if (command is FreeCommand) {
                    gameService.playerExploreCell(game, command.coordinates)
                } else if (command is EndCommand) {
                    break
                }
            } catch (e: StepOnMineException) {
                game.showMinesOnScreen()
                game.screen.print()
                printStepOnMine()
                break
            }

            // Check if all mines are found
            if (gameService.areAllMinesFound(game)) {
                game.screen.print()
                printAllMinesFound()
                break
            }
        }
    }
}
