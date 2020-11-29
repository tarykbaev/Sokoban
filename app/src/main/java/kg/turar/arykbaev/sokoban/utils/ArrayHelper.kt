package kg.turar.arykbaev.sokoban.utils

class ArrayHelper {
    companion object {
        fun letterToArray(newLetter: String): Array<IntArray> {
            val letter = removeUnnecessaryNewLine(newLetter)
            var row = 1
            for (element in letter) {
                if (element == '\n') {
                    row += 1
                }
            }

            val array = Array(row) { IntArray(0) }
            var column = 0
            var index = 0

            for (i in letter.indices) {
                val symbol = letter[i]
                if (symbol == '\n') {
                    array[index] = IntArray(column)
                    index += 1
                    column = 0
                } else {
                    column += 1
                }
            }
            array[index] = IntArray(column)

            var arrayIndexX = 0
            var arrayIndexY = 0

            for (element in letter) {
                if (element == '\n') {
                    arrayIndexX += 1
                    arrayIndexY = 0
                } else {
                    array[arrayIndexX][arrayIndexY] = Character.getNumericValue(element)
                    arrayIndexY += 1
                }
            }
            return array
        }

        fun getDefaultDesktop(): Array<IntArray> {
            val desktop = arrayOf(
                intArrayOf(2, 2, 2, 2, 2, 2, 2, 2),
                intArrayOf(2, 4, 3, 0, 0, 0, 0, 2),
                intArrayOf(2, 0, 0, 0, 0, 0, 0, 2),
                intArrayOf(2, 0, 0, 0, 0, 0, 0, 2),
                intArrayOf(2, 0, 0, 0, 1, 0, 0, 2),
                intArrayOf(2, 0, 0, 0, 0, 0, 0, 2),
                intArrayOf(2, 0, 0, 0, 0, 0, 0, 2),
                intArrayOf(2, 0, 0, 0, 0, 0, 0, 2),
                intArrayOf(2, 0, 0, 0, 0, 0, 0, 2),
                intArrayOf(2, 0, 0, 0, 0, 0, 0, 2),
                intArrayOf(2, 2, 2, 2, 2, 2, 2, 2)
            )
            return desktop
        }

        fun arrayToLetter(desktop: Array<IntArray>): String {
            var letter = ""
            for (row in desktop) {
                for (i in row) {
                    letter += i.toString()
                }
                letter += '\n'
            }
            return letter
        }

        private fun removeUnnecessaryNewLine(letter: String): String {
            var newLetter = ""
            var isNumber = false

            for (i in letter.indices) {
                if (isNumber && letter[i] == '\n' && i < letter.lastIndex - 1) {
                    newLetter += letter[i]
                }
                if (letter[i] != '\n') {
                    newLetter += letter[i]
                    isNumber = true
                } else {
                    isNumber = false
                }
            }
            return newLetter
        }
    }
}