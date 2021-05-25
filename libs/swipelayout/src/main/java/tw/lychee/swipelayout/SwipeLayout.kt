package tw.lychee.swipelayout

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper

class SwipeLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var surfaceView: View
    private lateinit var behindView: View
    private val dragHelper: ViewDragHelper
    private var dragable = true
    var isExpand = true
        private set

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount != 2) {
            throw Exception("SwipeLayout needs two children")
        }
        surfaceView = getChildAt(0) // RelativeLayout
        behindView = getChildAt(1) // TextView
    }

    override fun computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    init {
        val ta = context.obtainStyledAttributes(
            attrs, R.styleable.SwipeLayout, 0, 0
        )
        dragable = ta.getBoolean(R.styleable.SwipeLayout_dragable, true)
        ta.recycle()

        dragHelper = ViewDragHelper.create(this, 1.0f, DragHelperCallback())
    }

    internal inner class DragHelperCallback : ViewDragHelper.Callback() {
        override fun tryCaptureView(
            child: View,
            pointerId: Int
        ): Boolean {
            return surfaceView === child
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return surfaceView.measuredWidth
        }

        override fun clampViewPositionHorizontal(
            child: View,
            left: Int,
            dx: Int
        ): Int {
            val leftBound = -behindView.measuredWidth
            return Math.min(Math.max(leftBound, left), 0)
        }

        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
            behindView.offsetLeftAndRight(dx)
            invalidate()
        }

        //xvel = X velocity
        //yvel = Y velocity
        override fun onViewReleased(
            releasedChild: View,
            xvel: Float,
            yvel: Float
        ) {

            if (releasedChild === surfaceView) {
                if (xvel < -XVAL_THRESHOLD) {
                    expand()
                } else if (xvel > XVAL_THRESHOLD) {
                    collapse()
                } else {
                    val half = -behindView.measuredWidth / 2
                    if (surfaceView.left < half) {
                        expand()
                    } else {
                        collapse()
                    }
                }
            }
        }
    }

    fun expand() {
        if (dragHelper.smoothSlideViewTo(
                surfaceView, -behindView.measuredWidth, 0
            )
        ) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
        isExpand = true
    }

    fun collapse() {
        if (dragHelper.smoothSlideViewTo(surfaceView, 0, 0)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
        isExpand = false
    }

    override fun isEnabled(): Boolean {
        return dragable
    }

    override fun setEnabled(b: Boolean) {
        dragable = b
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        if (!dragable) return false
        dragHelper.processTouchEvent(e)
        return true
    }

    companion object {
        const val TAG = "SwipeLayout"
        private const val XVAL_THRESHOLD = 800
    }
}