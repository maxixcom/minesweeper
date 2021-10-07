package minesweeper.console.input

import minesweeper.domain.entity.Coordinates

data class MineCommand(val coordinates: Coordinates) : InputCommand
