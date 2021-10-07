package minesweeper.console

import minesweeper.console.input.EndCommand
import minesweeper.console.input.FreeCommand
import minesweeper.console.input.InputCommand
import minesweeper.console.input.MineCommand
import minesweeper.domain.entity.Board
import minesweeper.domain.entity.Coordinates

fun readNumberOfMines(): Int {
    println("How many mines do you want on the field?")
    return readLine()!!.toInt()
}

fun readInputCommand(validator: (inputCommand: InputCommand) -> Boolean = { true }): InputCommand {
    val regex = "^(?<x>\\d+)\\s+(?<y>\\d+)\\s+(?<action>free|mine)$".toRegex()
    while (true) {
        println("Set/unset mines marks or claim a cell as free:")
        val input = readLine()!!.trim()
        if (input == "end") {
            return EndCommand
        }
        val command = regex.matchEntire(input)?.let {
            val c = Coordinates(
                x = it.groups["x"]!!.value.toInt() - 1,
                y = it.groups["y"]!!.value.toInt() - 1,
            )
            if (it.groups["action"]!!.value == "free") {
                FreeCommand(c)
            } else {
                MineCommand(c)
            }
        }
        if (command != null) {
            if (validator(command)) {
                return command
            }
        } else {
            println("Invalid input")
        }
    }
}

fun printAllMinesFound() {
    println("Congratulations! You found all the mines!")
}

fun printStepOnMine() {
    println("You stepped on a mine and failed!")
}

fun printThereIsANumberHere() {
    println("There is a number here!")
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
