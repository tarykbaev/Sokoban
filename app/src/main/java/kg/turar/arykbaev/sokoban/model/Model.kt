package kg.turar.arykbaev.sokoban.model

import android.view.Menu
import kg.turar.arykbaev.sokoban.R
import kg.turar.arykbaev.sokoban.utils.*
import kg.turar.arykbaev.sokoban.viewer.Viewer

class Model {
    private val viewer: Viewer
    private var desktop: Array<IntArray>
    private var pointIndexX: IntArray
    private var pointIndexY: IntArray
    private var gamerPosIndexX: Int
    private var gamerPosIndexY: Int
    private var rowMaxSize: Int
    private var columnMaxSize: Int
    private var pointNumber: Int
    private var levelNumber: Int
    private var difficulty: String
    private var level: Level
    private lateinit var menu: Menu

    constructor(viewer: Viewer) {
        this.viewer = viewer
        gamerPosIndexX = 0
        gamerPosIndexY = 0
        rowMaxSize = 0
        columnMaxSize = 0
        pointNumber = 0
        levelNumber = LEVEL_1
        difficulty = DIFFICULTY_EASY
        pointIndexX = IntArray(0)
        pointIndexY = IntArray(0)
        level = LevelByteCode()
        desktop = level.getLevel(LEVEL_1)
        setGamerPositionAndCountPoint()
        setPointsPosition()
    }

    fun setSavedDesktop(difficulty: String?, levelNumber: Int, letterDesktop: String?) {
        this.difficulty = difficulty ?: DIFFICULTY_EASY
        this.levelNumber = levelNumber

        when (difficulty) {
            DIFFICULTY_EASY -> level = LevelByteCode()
            DIFFICULTY_MIDDLE -> level = LevelFile(viewer)
            DIFFICULTY_HARD -> level = LevelServer(viewer)
            else -> level = LevelByteCode()
        }

        desktop = if (letterDesktop != null) {
            ArrayHelper.letterToArray(letterDesktop)
        } else {
            level.getLevel(levelNumber)
        }

        level.setLevelNumber(levelNumber)

        updateDesktop()
    }

    private fun startLevel(levelNumber: Int) {
        desktop = level.getLevel(levelNumber)
        updateDesktop()
    }

    fun restartLevel() {
        desktop = level.restartLevel()
        updateDesktop()
    }

    fun nextLevel() {
        desktop = level.nextLevel()
        updateDesktop()
        checkMenuItem(level.getCurrentLevelNumber())
        viewer.setTitle("Level ${level.getCurrentLevelNumber()}")
    }

    private fun updateDesktop() {
        resetValues()
        setGamerPositionAndCountPoint()
        setPointsPosition()
        viewer.updateDesktop()
        viewer.update()
    }

    private fun resetValues() {
        gamerPosIndexX = 0
        gamerPosIndexY = 0
        rowMaxSize = 0
        columnMaxSize = 0
        pointNumber = 0
    }

    private fun setGamerPositionAndCountPoint() {
        for (i in desktop.indices) {
            for (j in desktop[i].indices) {
                if (desktop[i][j] == DESKTOP_GAMER) {
                    gamerPosIndexX = i
                    gamerPosIndexY = j
                }
                if (desktop[i][j] == DESKTOP_POINT) {
                    pointNumber++
                }
            }
            if (desktop[i].size > columnMaxSize) columnMaxSize = desktop[i].size
        }
        rowMaxSize = desktop.size
    }

    private fun setPointsPosition() {
        pointIndexX = IntArray(pointNumber)
        pointIndexY = IntArray(pointNumber)
        var index = 0
        for (i in desktop.indices) {
            for (j in desktop[i].indices) {
                if (desktop[i][j] == DESKTOP_POINT) {
                    pointIndexX[index] = i
                    pointIndexY[index] = j
                    index++
                }
            }
        }
    }

    fun move(direction: Direction) {
        if (isCollision(direction)) {
            moveBox(direction)
        }
        moveGamer(direction)
        checkPoint()
        winChecker()
        viewer.update()
    }

    private fun isCollision(direction: Direction): Boolean {
        when (direction) {
            Direction.LEFT  -> if (desktop[gamerPosIndexX][gamerPosIndexY - 1] == DESKTOP_BOX) return true
            Direction.UP    -> if (desktop[gamerPosIndexX - 1][gamerPosIndexY] == DESKTOP_BOX) return true
            Direction.RIGHT -> if (desktop[gamerPosIndexX][gamerPosIndexY + 1] == DESKTOP_BOX) return true
            Direction.DOWN  -> if (desktop[gamerPosIndexX + 1][gamerPosIndexY] == DESKTOP_BOX) return true
        }
        return false
    }

    private fun moveBox(direction: Direction) {
        when (direction) {
            Direction.LEFT  -> moveBoxToLeft()
            Direction.RIGHT -> moveBoxToRight()
            Direction.UP    -> moveBoxToUp()
            Direction.DOWN  -> moveBoxToDown()
        }
    }

    private fun moveGamer(direction: Direction) {
        when (direction) {
            Direction.LEFT  -> moveLeft()
            Direction.RIGHT -> moveRight()
            Direction.UP    -> moveUp()
            Direction.DOWN  -> moveDown()
        }
    }

    private fun checkPoint() {
        for (i in pointIndexX.indices) {
            if (desktop[pointIndexX[i]][pointIndexY[i]] == DESKTOP_EMPTY) {
                desktop[pointIndexX[i]][pointIndexY[i]] = DESKTOP_POINT
            }
        }
    }

    private fun winChecker() {
        for (i in pointIndexX.indices) {
            if (desktop[pointIndexX[i]][pointIndexY[i]] != DESKTOP_BOX) {
                return
            }
        }
        if (!level.isLastLevel()) {
            viewer.showWinDialog()
        } else {
            viewer.showLastLevelWinDialog()
        }
    }

    private fun moveLeft() {
        if (isNotWallOrBox(Direction.LEFT, gamerPosIndexX, gamerPosIndexY)) {
            desktop[gamerPosIndexX][gamerPosIndexY] = DESKTOP_EMPTY
            desktop[gamerPosIndexX][--gamerPosIndexY] = DESKTOP_GAMER
        }
    }

    private fun moveRight() {
        if (isNotWallOrBox(Direction.RIGHT, gamerPosIndexX, gamerPosIndexY)) {
            desktop[gamerPosIndexX][gamerPosIndexY] = DESKTOP_EMPTY
            desktop[gamerPosIndexX][++gamerPosIndexY] = DESKTOP_GAMER
        }
    }

    private fun moveUp() {
        if (isNotWallOrBox(Direction.UP, gamerPosIndexX, gamerPosIndexY)) {
            desktop[gamerPosIndexX][gamerPosIndexY] = DESKTOP_EMPTY
            desktop[--gamerPosIndexX][gamerPosIndexY] = DESKTOP_GAMER
        }
    }

    private fun moveDown() {
        if (isNotWallOrBox(Direction.DOWN, gamerPosIndexX, gamerPosIndexY)) {
            desktop[gamerPosIndexX][gamerPosIndexY] = DESKTOP_EMPTY
            desktop[++gamerPosIndexX][gamerPosIndexY] = DESKTOP_GAMER
        }
    }

    private fun moveBoxToLeft() {
        if (isNotWallOrBox(Direction.LEFT, gamerPosIndexX, gamerPosIndexY - 1)) {
            desktop[gamerPosIndexX][gamerPosIndexY - 1] = DESKTOP_EMPTY
            desktop[gamerPosIndexX][gamerPosIndexY - 2] = DESKTOP_BOX
        }
    }

    private fun moveBoxToRight() {
        if (isNotWallOrBox(Direction.RIGHT, gamerPosIndexX, gamerPosIndexY + 1)) {
            desktop[gamerPosIndexX][gamerPosIndexY + 1] = DESKTOP_EMPTY
            desktop[gamerPosIndexX][gamerPosIndexY + 2] = DESKTOP_BOX
        }
    }

    private fun moveBoxToUp() {
        if (isNotWallOrBox(Direction.UP, gamerPosIndexX - 1, gamerPosIndexY)) {
            desktop[gamerPosIndexX - 1][gamerPosIndexY] = DESKTOP_EMPTY
            desktop[gamerPosIndexX - 2][gamerPosIndexY] = DESKTOP_BOX
        }
    }

    private fun moveBoxToDown() {
        if (isNotWallOrBox(Direction.DOWN, gamerPosIndexX + 1, gamerPosIndexY)) {
            desktop[gamerPosIndexX + 1][gamerPosIndexY] = DESKTOP_EMPTY
            desktop[gamerPosIndexX + 2][gamerPosIndexY] = DESKTOP_BOX
        }
    }

    private fun isNotWallOrBox(direction: Direction, x: Int, y: Int): Boolean {
        when (direction) {
            Direction.LEFT  -> if (y < 1 || desktop[x][y - 1] == DESKTOP_WALL || desktop[x][y - 1] == DESKTOP_BOX) return false
            Direction.UP    -> if (x < 1 || desktop[x - 1][y] == DESKTOP_WALL || desktop[x - 1][y] == DESKTOP_BOX) return false
            Direction.RIGHT -> if (y > columnMaxSize - 3 || desktop[x][y + 1] == DESKTOP_WALL || desktop[x][y + 1] == DESKTOP_BOX) return false
            Direction.DOWN  -> if (x > rowMaxSize - 3 || desktop[x + 1][y] == DESKTOP_WALL || desktop[x + 1][y] == DESKTOP_BOX) return false
        }
        return true
    }

    fun getDesktop(): Array<IntArray> {
        return desktop
    }

    fun getColumnMaxSize(): Int {
        return columnMaxSize
    }

    fun getRowMaxSize(): Int {
        return rowMaxSize
    }

    fun setLevelByteCode() {
        level = LevelByteCode()
        startLevel(LEVEL_1)
        levelNumber = LEVEL_1
        setLevelListToMenu()
    }

    fun setLevelFile() {
        level = LevelFile(viewer)
        startLevel(LEVEL_1)
        levelNumber = LEVEL_1
        setLevelListToMenu()
    }

    fun setLevelServer() {
        level = LevelServer(viewer)
        startLevel(LEVEL_1)
        levelNumber = LEVEL_1
        setLevelListToMenu()
    }

    fun initMenu(menu: Menu) {
        this.menu = menu
    }

    fun setLevelListToMenu() {
        val subLevelMenu = menu.findItem(R.id.menu_level).subMenu
        subLevelMenu.clear()
        for (i in 1..level.getLevelsNumber()) {
            subLevelMenu.add(0, i, Menu.NONE, "${level.getDifficulty()} level $i")
        }
        subLevelMenu.setGroupCheckable(0, true, true)
        setCheckDifficultyItem()
        checkMenuItem(levelNumber)
        viewer.setTitle("Level $levelNumber")
    }

    private fun setCheckDifficultyItem() {
        when (difficulty) {
            DIFFICULTY_EASY -> checkMenuItem(R.id.menu_easy)
            DIFFICULTY_MIDDLE -> checkMenuItem(R.id.menu_middle)
            DIFFICULTY_HARD -> checkMenuItem(R.id.menu_hard)
            else -> checkMenuItem(R.id.menu_easy)
        }
    }

    fun setDifficulty(difficulty: String) {
        this.difficulty = difficulty
        this.levelNumber = LEVEL_1
    }

    fun setNewLevel(itemId: Int) {
        startLevel(itemId)
        viewer.setTitle("Level ${level.getCurrentLevelNumber()}")
    }

    fun checkMenuItem(id: Int) {
        val item = menu.findItem(id)
        item.isChecked = true
    }

    fun dismissDialog() {
        viewer.dismissDialog()
    }

    fun updateGamer(direction: Direction) {
        viewer.updateGamer(direction)
    }

    fun getDifficulty(): String {
        return level.getDifficulty()
    }

    fun getLevelNumber(): Int {
        return level.getCurrentLevelNumber()
    }

    fun getLetterDesktop(): String {
        return ArrayHelper.arrayToLetter(desktop)
    }
}