package minesweeper.console

import minesweeper.app.services.GameServiceImpl
import minesweeper.domain.services.GameService

object Application {
    val gameService: GameService = GameServiceImpl()
}