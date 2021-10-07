package minesweeper.app.services

import minesweeper.domain.entity.Board
import minesweeper.domain.entity.CellType
import minesweeper.domain.entity.Coordinates
import minesweeper.domain.entity.Game
import minesweeper.domain.services.GameService
import minesweeper.domain.utils.cellType
import minesweeper.domain.utils.hideMines
import minesweeper.domain.utils.hintMines
import minesweeper.domain.utils.plant
import kotlin.random.Random

class GameServiceImpl : GameService {
    override fun createGame(width: Int, height: Int, numberOfMines: Int): Game {
        val game = Game(
            board = Board(width, height),
            mines = this.createMines(width, height, numberOfMines),
        )
        game.hintMines()
        game.hideMines()

        return game
    }

    override fun playerMarksMine(game: Game, coordinate: Coordinates) {
        when (game.board.cellType(coordinate)) {
            is CellType.Mark -> {
                game.marks.remove(coordinate)
                game.board.plant(coordinate, CellType.Empty)
            }
            is CellType.Empty -> {
                game.marks.add(coordinate)
                game.board.plant(coordinate, CellType.Mark)
            }
            else -> {
            }
        }
    }

    override fun areAllMinesFound(game: Game): Boolean =
        game.mines.size == game.marks.size && game.mines.containsAll(game.marks)

    private fun createMines(width: Int, height: Int, number: Int): List<Coordinates> {
        val randomGenerator = Random.Default
        val n = number.coerceIn(0..width * height)
        var placed = 0
        val mines = mutableListOf<Coordinates>()
        while (placed != n) {
            val coordinate = Coordinates(
                x = randomGenerator.nextInt(0, width),
                y = randomGenerator.nextInt(0, height)
            )
            if (coordinate in mines) {
                continue
            }
            mines.add(coordinate)
            placed++
        }
        return mines
    }
}
