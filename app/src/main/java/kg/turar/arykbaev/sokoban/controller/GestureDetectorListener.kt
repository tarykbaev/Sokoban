package kg.turar.arykbaev.sokoban.controller

import android.view.GestureDetector
import android.view.MotionEvent
import kg.turar.arykbaev.sokoban.utils.SWIPE_MIN_DISTANCE
import kg.turar.arykbaev.sokoban.utils.SWIPE_THRESHOLD_VELOCITY
import kotlin.math.abs

class GestureDetectorListener : GestureDetector.SimpleOnGestureListener {

    private val controller: Controller

    constructor(controller: Controller) {
        this.controller = controller
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onFling(
        event1: MotionEvent,
        event2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        val valueX: Float = event2.x - event1.x
        val valueY: Float = event2.y - event1.y

        if (abs(valueX) > abs(valueY)) {
            if (abs(valueX) > SWIPE_MIN_DISTANCE && abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                if (valueX > 0) {
                    swipeRight()
                } else {
                    swipeLeft()
                }
            }
        } else if (abs(valueY) > SWIPE_MIN_DISTANCE && abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            if (valueY > 0) {
                swipeDown()
            } else {
                swipeUp()
            }
        }
        return super.onFling(event1, event2, velocityX, velocityY)
    }

    private fun swipeDown() {
        controller.swipeDown()
    }

    private fun swipeUp() {
        controller.swipeUp()
    }

    private fun swipeLeft() {
        controller.swipeLeft()
    }

    private fun swipeRight() {
        controller.swipeRight()
    }
}