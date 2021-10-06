package minesweeper

import kotlin.random.Random

data class Coordinates(val x: Int, val y: Int)

sealed class CellType(val character: Char) {
    object Free : CellType('.')
    object Mine : CellType('X')
    class Hint(digit: Int) : CellType(digit.toString().first())
}

fun main() {
    val field = List<MutableList<CellType>>(9) {
        MutableList(9) {
            CellType.Free
        }
    }

    println("How many mines do you want on the field?")
    val minesNumber = readLine()!!.toInt()

    val mines = placeMines(field, minesNumber)
    plantMines(field, mines)
    hintMines(field, mines)
    printField(field)
}

/**
 * @deprecated
 */
fun List<MutableList<CellType>>.has(c: Coordinates): Boolean {
    return if (size > 0) {
        c.x >= 0 && c.y >= 0 && c.x < this[0].size && c.y < this.size
    } else {
        false
    }
}

fun List<MutableList<CellType>>.neighbors(c: Coordinates): List<Coordinates> =
    c.neighbors()
        .filter { it.x >= 0 && it.y >= 0 && it.x < this[0].size && it.y < this.size }
        .filter { it != c }

fun Coordinates.neighbors(): List<Coordinates> =
    (-1..1).flatMap { dx ->
        (-1..1).map { dy ->
            Coordinates(x + dx, y + dy)
        }
    }.filter { it != this }

fun hintMines(field: List<MutableList<CellType>>, mines: List<Coordinates>) {
    val hintPoints = mutableSetOf<Coordinates>()

    for (m in mines) {
        field.neighbors(m)
            .map {
                it
            }
            .filter { field[it.y][it.x] == CellType.Free }
            .forEach { hintPoints.add(it) }
    }

    hintPoints.forEach { cell ->
        val n = field.neighbors(cell).count { field[it.y][it.x] == CellType.Mine }
        field[cell.y][cell.x] = CellType.Hint(n)
    }
}

fun plantMines(field: List<MutableList<CellType>>, mines: List<Coordinates>) {
    for (m in mines) {
        field[m.y][m.x] = CellType.Mine
    }
}

fun placeMines(field: List<MutableList<CellType>>, number: Int): List<Coordinates> {
    val randomGenerator = Random.Default
    val width = field[0].size
    val height = field.size
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

fun printField(field: List<List<CellType>>, cellMargin: String = "") {
    field.reversed().forEach { row ->
        println(row.map { it.character }.joinToString(cellMargin))
    }
}
