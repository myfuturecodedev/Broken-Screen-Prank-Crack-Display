package com.futurecode.crackdisplayprank.utils


import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.min

/**
 * 15-Year Developer Standard: Dynamically scales down and fades out side elements
 * depending on their relative distance to the horizontal viewport center point.
 */
class CarouselLayoutManager(context: Context) : LinearLayoutManager(context, HORIZONTAL, false) {

    private val shrinkAmount = 0.35f  // Scales down side cards to 65% of center dimension
    private val shrinkDistance = 0.9f
    private val alphaAmount = 0.70f   // Fades side cards opacity down to 30%

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        scaleAndFadeChildren()
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
        if (orientation == HORIZONTAL) {
            scaleAndFadeChildren()
        }
        return scrolled
    }

    private fun scaleAndFadeChildren() {
        val midpoint = width / 2.0f
        val d0 = 0.0f
        val d1 = shrinkDistance * midpoint
        val s0 = 1.0f
        val s1 = 1.0f - shrinkAmount
        val a0 = 1.0f
        val a1 = 1.0f - alphaAmount

        for (i in 0 until childCount) {
            val child = getChildAt(i) ?: continue
            val childMidpoint = (getDecoratedRight(child) + getDecoratedLeft(child)) / 2.0f
            val distance = min(d1, abs(midpoint - childMidpoint))

            val scale = s0 + (s1 - s0) * (distance - d0) / (d1 - d0)
            val alpha = a0 + (a1 - a0) * (distance - d0) / (d1 - d0)

            child.scaleX = scale
            child.scaleY = scale
            child.alpha = alpha
        }
    }
}