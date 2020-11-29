package kg.turar.arykbaev.sokoban.model

import kg.turar.arykbaev.sokoban.utils.*
import kg.turar.arykbaev.sokoban.viewer.Viewer
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader

class LevelFile : Level {
    private val listOfLevels: Array<String>
    private val viewer: Viewer
    private var currentLevelIndex: Int

    constructor(viewer: Viewer) {
        this.viewer = viewer
        listOfLevels = arrayOf("level1.sok", "level2.sok", "level3.sok")
        currentLevelIndex = 0
    }

    override fun getLevel(levelNumber: Int): Array<IntArray> {
        currentLevelIndex = levelNumber - 1

        return when (levelNumber) {
            LEVEL_1 -> getDesktopFromFile(listOfLevels[0])
            LEVEL_2 -> getDesktopFromFile(listOfLevels[1])
            LEVEL_3 -> getDesktopFromFile(listOfLevels[2])
            else -> ArrayHelper.getDefaultDesktop()
        }
    }

    private fun getDesktopFromFile(fileName: String): Array<IntArray> {
        var letterFromFile = ""

        try {
            val assetManager = viewer.assets
            val inputStream = assetManager.open("levels/$fileName")
            val input = BufferedReader(InputStreamReader(inputStream))

            while (true) {
                val unicode = input.read()
                val symbol = unicode.toChar()
                if (symbol in '0'..'9' || symbol == '\n') {
                    letterFromFile += symbol
                }

                if (unicode == -1) {
                    break
                }
            }
        } catch (fne: FileNotFoundException) {
            println(fne)
        } catch (ioe: IOException) {
            println(ioe)
        }

        return ArrayHelper.letterToArray(letterFromFile)
    }

    override fun nextLevel(): Array<IntArray> {
        if (currentLevelIndex < listOfLevels.size - 1) {
            currentLevelIndex++
        }
        return getLevel(currentLevelIndex + 1)
    }

    override fun getLevelsNumber(): Int {
        return listOfLevels.size
    }

    override fun setLevelNumber(levelNumber: Int) {
        currentLevelIndex = levelNumber - 1
    }

    override fun isLastLevel(): Boolean {
        return listOfLevels.size - 1 <= currentLevelIndex
    }

    override fun getCurrentLevelNumber(): Int {
        return currentLevelIndex + 1
    }

    override fun restartLevel(): Array<IntArray> {
        return getLevel(currentLevelIndex + 1)
    }

    override fun getDifficulty(): String {
        return DIFFICULTY_MIDDLE
    }
}