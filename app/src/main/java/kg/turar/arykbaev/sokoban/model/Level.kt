package kg.turar.arykbaev.sokoban.model

interface Level {
    fun getLevel(levelNumber: Int): Array<IntArray>
    fun nextLevel(): Array<IntArray>
    fun restartLevel(): Array<IntArray>
    fun getLevelsNumber(): Int
    fun setLevelNumber(levelNumber: Int)
    fun isLastLevel(): Boolean
    fun getCurrentLevelNumber(): Int
    fun getDifficulty(): String
}