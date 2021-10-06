package minesweeper

import kotlin.random.Random

enum class CellType(val character: Char) {
    Free('.'),
    Mine('X'),
}

fun main() {
    val field = List(9) {
        MutableList(9) {
            CellType.Free
        }
    }

    println("How many mines do you want on the field?")
    val minesNumber = readLine()!!.toInt()
    placeMines(field, minesNumber)
    printField(field)
}

fun placeMines(field: List<MutableList<CellType>>, number: Int) {
    val randomGenerator = Random.Default
    val width = field[0].size
    val height = field.size
    val n = number.coerceIn(0..width * height)
    var placed = 0
    while (placed != n) {
        val x = randomGenerator.nextInt(0, width)
        val y = randomGenerator.nextInt(0, height)
        if (field[y][x] == CellType.Free) {
            field[y][x] = CellType.Mine
            placed++
        }
    }
}

fun printField(field: List<List<CellType>>, cellMargin: String = "") {
    field.forEach { row ->
        println(row.map { it.character }.joinToString(cellMargin))
    }
}
