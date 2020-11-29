package kg.turar.arykbaev.sokoban.model

import android.widget.Toast
import kg.turar.arykbaev.sokoban.utils.*
import kg.turar.arykbaev.sokoban.viewer.Viewer

class LevelServer : Level {
    private val viewer: Viewer
    private val listOfLevels: Array<Int>
    private var currentLevelIndex: Int

    constructor(viewer: Viewer) {
        this.viewer = viewer
        listOfLevels = arrayOf(LEVEL_1, LEVEL_2, LEVEL_3)
        currentLevelIndex = 0
    }

    override fun getLevel(levelNumber: Int): Array<IntArray> {
        currentLevelIndex = levelNumber - 1
        when (levelNumber) {
            LEVEL_1 -> return getDesktopFromServer(listOfLevels[0])
            LEVEL_2 -> return getDesktopFromServer(listOfLevels[1])
            LEVEL_3 -> return getDesktopFromServer(listOfLevels[2])
            else -> return ArrayHelper.getDefaultDesktop()
        }
    }

    private fun getDesktopFromServer(levelNumber: Int): Array<IntArray> {
        val connectToServer = ConnectToServer(levelNumber.toString())
        connectToServer.go()
        val letter = connectToServer.getLetter()
        if (letter.isEmpty()) {
            Toast.makeText(viewer, "Server problems", Toast.LENGTH_SHORT).show()
            return ArrayHelper.getDefaultDesktop()
        }
        return ArrayHelper.letterToArray(letter)
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
        return DIFFICULTY_HARD
    }
}