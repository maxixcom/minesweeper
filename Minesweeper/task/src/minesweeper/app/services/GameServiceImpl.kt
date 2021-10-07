package minesweeper.app.services

import minesweeper.domain.entity.Board
import minesweeper.domain.entity.CellType
import minesweeper.domain.entity.Coordinates
import minesweeper.domain.services.GameService
import minesweeper.domain.utils.cellType
import minesweeper.domain.utils.plant
import minesweeper.domain.utils.validCellNeighbors
import kotlin.random.Random

class GameServiceImpl : GameService {
    override fun createBoard(width: Int, height: Int, numberOfMines: Int): Board {
        val board = Board(width, height)
        val mines = this.createMines(board, numberOfMines)
        this.plantMines(board, mines)
        this.hintMines(board, mines)
        return board
    }

    private fun createMines(board: Board, number: Int): List<Coordinates> {
        val randomGenerator = Random.Default
        val n = number.coerceIn(0..board.width * board.height)
        var placed = 0
        val mines = mutableListOf<Coordinates>()
        while (placed != n) {
            val coordinate = Coordinates(
                x = randomGenerator.nextInt(0, board.width),
                y = randomGenerator.nextInt(0, board.height)
            )
            if (coordinate in mines) {
                continue
            }
            mines.add(coordinate)
            placed++
        }
        return mines
    }

    private fun plantMines(board: Board, mines: List<Coordinates>) =
        mines.forEach { board.plant(it, CellType.Mine) }

    private fun hintMines(board: Board, mines: List<Coordinates>) {
        val hintPoints = mutableSetOf<Coordinates>()

        for (m in mines) {
            board.validCellNeighbors(m)
                .map {
                    it
                }
                .filter { board.cellType(it) == CellType.Free }
                .forEach { hintPoints.add(it) }
        }

        hintPoints.forEach { cell ->
            val n = board.validCellNeighbors(cell).count { board.cellType(it) == CellType.Mine }
            board.plant(cell, CellType.Hint(n))
        }
    }
}
