package minesweeper.console.input

import minesweeper.domain.entity.Coordinates

data class FreeCommand(val coordinates: Coordinates) : InputCommand
