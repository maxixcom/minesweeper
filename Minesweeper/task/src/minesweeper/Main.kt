package minesweeper

import minesweeper.console.ConsoleRunner
import minesweeper.domain.AppRunner

fun main() {
    val appRunner: AppRunner = ConsoleRunner()
    appRunner.run()
}
