package com.martin.medicationtracker.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.pow
import android.view.MotionEvent

class CarouselRecyclerView: RecyclerView {
    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int): super(context, attrs, defStyle)

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        post {
            (0 until childCount).forEach { position ->
                val child = getChildAt(position)
                val childCenterX = (child.left + child.right) / 2
                var scaleValue = getGaussianScale(childCenterX, 1f, 1f, 150.toDouble())
                scaleValue /= 2
                if (scaleValue < 0.8f) {
                    scaleValue = 0.8f
                }
                child.scaleX = scaleValue
                child.scaleY = scaleValue
            }
        }
    }

    private fun getGaussianScale(
        childCenterX: Int,
        minScaleOffset: Float,
        scaleFactor: Float,
        spreadFactor: Double
    ): Float {
        val recyclerCenterX = (left + right) / 2
        return (Math.E.pow(
            -(childCenterX - recyclerCenterX.toDouble()).pow(2.toDouble()) / (2 * Math.pow(
                spreadFactor,
                2.toDouble()
            ))
        ) * scaleFactor + minScaleOffset).toFloat()
    }

    private fun isLeftMost(dx: Float) =
        (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() == 1 && dx < 0

    private fun isRightMost(dx: Float): Boolean {
        val lm = layoutManager as LinearLayoutManager
        val position = lm.findFirstCompletelyVisibleItemPosition()
        return position == lm.itemCount-2 && dx > 0
    }

    private var latestX = 0f
    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                latestX = e.x
                return super.onTouchEvent(e)
            }

            MotionEvent.ACTION_MOVE -> {
                val dx = latestX-e.x
                if (isLeftMost(dx) || isRightMost(dx)) return false

                return super.onTouchEvent(e)
            }
        }

        return super.onTouchEvent(e)
    }
}