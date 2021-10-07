package minesweeper.console

import minesweeper.domain.entity.Board
import minesweeper.domain.entity.Coordinates

fun readNumberOfMines(): Int {
    println("How many mines do you want on the field?")
    return readLine()!!.toInt()
}

fun readPlayerInput(validator: (c: Coordinates) -> Boolean): Coordinates {
    val regex = "^\\s*(?<x>\\d+)\\s+(?<y>\\d+)\\s*$".toRegex()
    while (true) {
        println("Set/delete mine marks (x and y coordinates):")
        val coordinates = regex.matchEntire(readLine()!!)?.let {
            Coordinates(
                x = it.groups["x"]!!.value.toInt() - 1,
                y = it.groups["y"]!!.value.toInt() - 1,
            )
        }
        if (coordinates != null) {
            if (validator(coordinates)) {
                return coordinates
            }
        } else {
            println("Invalid input")
        }
    }
}

fun printAllMinesFound() {
    println("Congratulations! You found all the mines!")
}

fun Board.print() {
    println(List(width) { it + 1 }.joinToString(separator = "", prefix = " │", postfix = "│"))
    println(List(width) { "—" }.joinToString("", prefix = "—│", postfix = "│"))
    data.forEachIndexed { indexRow, row ->
        println(
            row.map { cell ->
                cell.character
            }.joinToString("", prefix = "${indexRow + 1}│", postfix = "│")
        )
    }
    println(List(width) { "—" }.joinToString("", prefix = "—│", postfix = "│"))
}
