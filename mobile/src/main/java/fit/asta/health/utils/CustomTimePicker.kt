package fit.asta.health.utils

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.TimePicker
import androidx.annotation.RequiresApi

/**
 * To fix touch event issues when it is used inside scrollview/nested scrollview.
 */
class CustomTimePicker : TimePicker {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            //Excluding the top of the view
            if (ev.y < height / 3.3f) return false

            if (ev.x < width / 5f || ev.x > width / 1.20f) return false

            parent?.requestDisallowInterceptTouchEvent(true)
        }

        return false
    }
}