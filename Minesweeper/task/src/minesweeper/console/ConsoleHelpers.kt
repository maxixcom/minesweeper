package minesweeper.console

import minesweeper.domain.entity.Board

fun readNumberOfMines(): Int {
    println("How many mines do you want on the field?")
    return readLine()!!.toInt()
}

fun Board.print(cellMargin: String = "") {
    data.reversed().forEach { row ->
        println(row.map { it.character }.joinToString(cellMargin))
    }
}
