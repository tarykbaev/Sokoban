package kg.turar.arykbaev.sokoban.model

import kg.turar.arykbaev.sokoban.utils.*

class LevelByteCode : Level {
    private val listOfLevels: Array<Int>
    private var currentLevelIndex: Int

    constructor() {
        listOfLevels = arrayOf(LEVEL_1, LEVEL_2, LEVEL_3)
        currentLevelIndex = 0
    }

    private fun getFirstDesktop(): Array<IntArray> {
        val desktop = arrayOf(
            intArrayOf(2, 2, 2, 2, 2, 2),
            intArrayOf(2, 0, 0, 1, 0, 2),
            intArrayOf(2, 3, 2, 2, 2, 2),
            intArrayOf(2, 0, 2),
            intArrayOf(2, 0, 2),
            intArrayOf(2, 4, 2),
            intArrayOf(2, 2, 2)
        )
        return desktop
    }

    private fun getSecondDesktop(): Array<IntArray> {
        val desktop = arrayOf(
            intArrayOf(2, 2, 2, 2, 2, 2),
            intArrayOf(2, 1, 0, 0, 0, 2),
            intArrayOf(2, 0, 0, 0, 0, 2),
            intArrayOf(2, 0, 0, 3, 4, 2),
            intArrayOf(2, 0, 4, 3, 0, 2),
            intArrayOf(2, 2, 2, 2, 2, 2)
        )
        return desktop
    }

    private fun getThirdDesktop(): Array<IntArray> {
        val desktop = arrayOf(
            intArrayOf(2, 2, 2, 2, 2, 2),
            intArrayOf(2, 1, 0, 0, 0, 2, 2),
            intArrayOf(2, 0, 3, 3, 0, 0, 2),
            intArrayOf(2, 0, 2, 4, 0, 4, 2),
            intArrayOf(2, 0, 0, 0, 0, 0, 2),
            intArrayOf(2, 2, 2, 2, 2, 2, 2)
        )

        return desktop
    }

    override fun getLevel(levelNumber: Int): Array<IntArray> {
        currentLevelIndex = levelNumber - 1
        when (levelNumber) {
            LEVEL_1 -> return getFirstDesktop()
            LEVEL_2 -> return getSecondDesktop()
            LEVEL_3 -> return getThirdDesktop()
            else -> return ArrayHelper.getDefaultDesktop()
        }
    }

    override fun nextLevel(): Array<IntArray> {
        if (currentLevelIndex < listOfLevels.size - 1) {
            currentLevelIndex++
        }
        return getLevel(currentLevelIndex + 1)
    }

    override fun restartLevel(): Array<IntArray> {
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
        return listOfLevels[currentLevelIndex]
    }

    override fun getDifficulty(): String {
        return DIFFICULTY_EASY
    }
}