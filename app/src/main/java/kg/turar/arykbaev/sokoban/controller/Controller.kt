package kg.turar.arykbaev.sokoban.controller

import android.view.*
import kg.turar.arykbaev.sokoban.R
import kg.turar.arykbaev.sokoban.model.Model
import kg.turar.arykbaev.sokoban.utils.DIFFICULTY_EASY
import kg.turar.arykbaev.sokoban.utils.DIFFICULTY_HARD
import kg.turar.arykbaev.sokoban.utils.DIFFICULTY_MIDDLE
import kg.turar.arykbaev.sokoban.utils.Direction
import kg.turar.arykbaev.sokoban.viewer.Viewer

public class Controller : View.OnTouchListener, View.OnClickListener {
    private val model: Model
    private lateinit var gestureDetector: GestureDetector

    constructor(viewer: Viewer) {
        model = Model(viewer)
    }

    fun initDetector(viewer: Viewer) {
        gestureDetector = GestureDetector(viewer, GestureDetectorListener(this))
    }

    fun initMenu(menu: Menu) {
        model.initMenu(menu)
        model.setLevelListToMenu()
    }

    fun getModel(): Model {
        return model
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    fun swipeDown() {
        model.updateGamer(Direction.DOWN)
        model.move(Direction.DOWN)
    }

    fun swipeUp() {
        model.updateGamer(Direction.UP)
        model.move(Direction.UP)
    }

    fun swipeLeft() {
        model.updateGamer(Direction.LEFT)
        model.move(Direction.LEFT)
    }

    fun swipeRight() {
        model.updateGamer(Direction.RIGHT)
        model.move(Direction.RIGHT)
    }

    fun onOptionsItemSelectedHandler(item: MenuItem) {
        when (item.itemId) {
            R.id.menu_easy -> {
                model.setLevelByteCode()
                model.setDifficulty(DIFFICULTY_EASY)
            }
            R.id.menu_middle -> {
                model.setLevelFile()
                model.setDifficulty(DIFFICULTY_MIDDLE)
            }
            R.id.menu_hard -> {
                model.setLevelServer()
                model.setDifficulty(DIFFICULTY_HARD)
            }
            R.id.menu_refresh -> {
                model.restartLevel()
            }
            R.id.menu_level -> {
                return
            }
            R.id.menu_difficulty -> {
                return
            }
            else -> {
                model.setNewLevel(item.itemId)
                model.checkMenuItem(item.itemId)
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.next_level -> model.nextLevel()
            R.id.play_again -> model.restartLevel()
        }
        model.dismissDialog()
    }

    fun setSavedDesktop(difficulty: String?, levelNumber: Int, letterDesktop: String?) {
        model.setSavedDesktop(difficulty, levelNumber, letterDesktop)
    }

    fun getDifficulty(): String {
        return model.getDifficulty()
    }

    fun getLevelNumber(): Int {
        return model.getLevelNumber()
    }

    fun getLetterDesktop(): String {
        return model.getLetterDesktop()
    }
}