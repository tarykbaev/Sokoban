package kg.turar.arykbaev.sokoban.viewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import kg.turar.arykbaev.sokoban.R
import kg.turar.arykbaev.sokoban.canvas.CanvasSokoban
import kg.turar.arykbaev.sokoban.controller.Controller
import kg.turar.arykbaev.sokoban.utils.DESKTOP
import kg.turar.arykbaev.sokoban.utils.DIFFICULTY
import kg.turar.arykbaev.sokoban.utils.Direction
import kg.turar.arykbaev.sokoban.utils.LEVEL


class Viewer : AppCompatActivity {

    private val controller: Controller
    private lateinit var canvas: CanvasSokoban
    private lateinit var dialog: AlertDialog

    constructor() {
        controller = Controller(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        canvas = CanvasSokoban(this, controller.getModel())
        canvas.setOnTouchListener(controller)
        setContentView(canvas)
        controller.initDetector(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.level_menu, menu)
        controller.initMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        controller.onOptionsItemSelectedHandler(item)
        return true
    }

    fun showWinDialog(){
        val builder = AlertDialog.Builder(this)
        val view = View.inflate(this, R.layout.congratulations_dialog_layout, null)
        builder.setView(view)
        val next = view.findViewById<Button>(R.id.next_level)
        val restart = view.findViewById<Button>(R.id.play_again)
        next.setOnClickListener(controller)
        restart.setOnClickListener(controller)
        dialog = builder.create()
        dialog.setCancelable(true)
        dialog.show()
    }

    fun showLastLevelWinDialog() {
        val builder = AlertDialog.Builder(this)
        val view = View.inflate(this, R.layout.last_level_dialog_layout, null)
        builder.setView(view)
        val restart = view.findViewById<Button>(R.id.play_again)
        restart.setOnClickListener(controller)
        dialog = builder.create()
        dialog.setCancelable(true)
        dialog.show()
    }

    fun dismissDialog() {
        dialog.dismiss()
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun updateDesktop() {
        canvas.updateDesktop()
    }

    fun update() {
        canvas.update()
    }

    fun updateGamer(direction: Direction) {
        canvas.updateGamer(direction)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
            val difficulty = savedInstanceState.getString(DIFFICULTY)
            val levelNumber = savedInstanceState.getInt(LEVEL)
            val letter = savedInstanceState.getString(DESKTOP)
            controller.setSavedDesktop(difficulty, levelNumber, letter)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(DIFFICULTY, controller.getDifficulty())
        outState.putInt(LEVEL, controller.getLevelNumber())
        outState.putString(DESKTOP, controller.getLetterDesktop())
    }

}