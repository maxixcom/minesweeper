package minesweeper.app.services

import minesweeper.domain.entity.Board
import minesweeper.domain.entity.CellType
import minesweeper.domain.entity.Coordinates
import minesweeper.domain.entity.Game
import minesweeper.domain.exceptions.StepOnMineException
import minesweeper.domain.services.GameService
import minesweeper.domain.utils.cellNeighbors
import minesweeper.domain.utils.cellType
import minesweeper.domain.utils.hintMines
import minesweeper.domain.utils.plant
import minesweeper.domain.utils.plantMines
import minesweeper.domain.utils.showOnScreen
import kotlin.random.Random

class GameServiceImpl : GameService {
    override fun createGame(width: Int, height: Int, numberOfMines: Int): Game {
        val game = Game(
            board = Board(width, height),
            mines = this.createMines(width, height, numberOfMines),
            screen = Board(width, height, CellType.Hidden)
        )

        game.plantMines()
        game.hintMines()

        return game
    }

    override fun playerMarksMine(game: Game, coordinates: Coordinates) {
        when (game.screen.cellType(coordinates)) {
            is CellType.Mark -> {
                game.marks.remove(coordinates)
                game.screen.plant(coordinates, CellType.Hidden)
            }
            is CellType.Hidden -> {
                game.marks.add(coordinates)
                game.screen.plant(coordinates, CellType.Mark)
            }
            else -> {
            }
        }
    }

    override fun playerExploreCell(game: Game, coordinates: Coordinates) {
        when {
            game.screen.cellType(coordinates) is CellType.Empty -> return
            game.screen.cellType(coordinates) is CellType.Hint -> return
            game.board.cellType(coordinates) is CellType.Mine -> throw StepOnMineException()
        }

        // flood fill screen
        val stack = mutableListOf<Coordinates>()
        val visited = mutableListOf<Coordinates>()

        stack.add(coordinates)
        do {

            val c = stack.removeAt(stack.size - 1)
            game.showOnScreen(c)
            visited.add(c)

            val neighbors = game.board.cellNeighbors(c)
            for (n in neighbors) {
                if (game.board.cellType(n) is CellType.Empty && !visited.contains(n)) {
                    stack.add(n)
                } else if (game.board.cellType(n) is CellType.Hint) {
                    game.showOnScreen(n)
                }
            }
        } while (stack.isNotEmpty())
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
