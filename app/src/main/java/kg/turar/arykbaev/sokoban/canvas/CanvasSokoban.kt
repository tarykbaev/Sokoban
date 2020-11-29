package kg.turar.arykbaev.sokoban.canvas

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import kg.turar.arykbaev.sokoban.R
import kg.turar.arykbaev.sokoban.model.Model
import kg.turar.arykbaev.sokoban.utils.*
import kg.turar.arykbaev.sokoban.viewer.Viewer

class CanvasSokoban : View {
    private val model: Model
    private val viewer: Viewer
    private var desktop: Array<IntArray>
    private var rowMaxSize: Int
    private var columnMaxSize: Int
    private var gamer: Drawable
    private val wall: Drawable
    private val point: Drawable
    private val box: Drawable
    private val empty: Drawable
    private var isChanged: Boolean
    private var cellSize: Int
    private var x: Int
    private var y: Int
    private var startX: Int
    private var startY: Int

    constructor(viewer: Viewer, model: Model) : super(viewer) {
        this.model = model
        this.viewer = viewer
        setBackgroundColor(viewer.getColor(R.color.backColor))
        desktop = model.getDesktop()
        rowMaxSize = model.getRowMaxSize()
        columnMaxSize = model.getColumnMaxSize()
        isChanged = false
        cellSize = 0
        x = 0
        y = 0
        startX = 0
        startY = 0
        gamer = viewer.getDrawable(R.drawable.gamer)!!
        box = viewer.getDrawable(R.drawable.box)!!
        wall = viewer.getDrawable(R.drawable.wall)!!
        point = viewer.getDrawable(R.drawable.point)!!
        empty = viewer.getDrawable(R.drawable.empty)!!
    }

    fun updateGamer(direction: Direction) {
        when (direction) {
            Direction.LEFT -> gamer = viewer.getDrawable(R.drawable.gamer_left)!!
            Direction.RIGHT -> gamer = viewer.getDrawable(R.drawable.gamer_right)!!
            Direction.UP -> gamer = viewer.getDrawable(R.drawable.gamer_up)!!
            Direction.DOWN -> gamer = viewer.getDrawable(R.drawable.gamer)!!
            else -> gamer = viewer.getDrawable(R.drawable.gamer)!!
        }
    }

    fun updateDesktop() {
        resetValue()
        desktop = model.getDesktop()
    }

    private fun resetValue() {
        isChanged = false
        cellSize = 0
        x = 0
        y = 0
        startX = 0
        startY = 0
        rowMaxSize = model.getRowMaxSize()
        columnMaxSize = model.getColumnMaxSize()
    }

    override fun onDraw(canvas: Canvas) {
        if (!isChanged) {
            if (width < height) {
                setDesktopResolutionPortrait()
            } else {
                setDesktopResolutionLandscape()
            }
            isChanged = true
        }

        x = startX
        y = startY

        for (row in desktop) {
            for (i in row) {
                when (i) {
                    DESKTOP_GAMER -> {
                        gamer.setBounds(x, y, x + cellSize, y + cellSize)
                        gamer.draw(canvas)
                    }
                    DESKTOP_WALL -> {
                        wall.setBounds(x, y, x + cellSize, y + cellSize)
                        wall.draw(canvas)
                    }
                    DESKTOP_BOX -> {
                        box.setBounds(x, y, x + cellSize, y + cellSize)
                        box.draw(canvas)
                    }
                    DESKTOP_POINT -> {
                        point.setBounds(x, y, x + cellSize, y + cellSize)
                        point.draw(canvas)
                    }
                    DESKTOP_EMPTY -> {
                        empty.setBounds(x, y, x + cellSize, y + cellSize)
                        empty.draw(canvas)
                    }
                }
                x += cellSize
            }
            x = startX
            y += cellSize
        }
    }

    private fun setDesktopResolutionPortrait() {
        if (rowMaxSize != 0 && columnMaxSize != 0) {
            if (height < (width / columnMaxSize) * rowMaxSize) {
                cellSize = height / rowMaxSize
                startX = (width / 2) - cellSize * (columnMaxSize / 2)
                startY = 0
            } else {
                cellSize = width / columnMaxSize
                startX = 0
                startY = (height / 2) - cellSize * (rowMaxSize / 2)
            }
        }
    }

    private fun setDesktopResolutionLandscape() {
        if (rowMaxSize != 0 && columnMaxSize != 0) {
            if (width < (height / rowMaxSize) * columnMaxSize) {
                cellSize = width / columnMaxSize
                startX = 0
                startY = (height / 2) - cellSize * (rowMaxSize / 2)
            } else {
                cellSize = height / rowMaxSize
                startX = (width / 2) - cellSize * (columnMaxSize / 2)
                startY = 0
            }
        }
    }

    fun update() {
        invalidate()
    }
}