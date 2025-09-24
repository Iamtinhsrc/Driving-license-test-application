package com.example.driving_car_project

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingResource

class RecyclerViewItemCountIdlingResource(
    private val recyclerView: RecyclerView,
    private val expectedCount: Int
) : IdlingResource {

    private var callback: IdlingResource.ResourceCallback? = null

    override fun getName(): String = RecyclerViewItemCountIdlingResource::class.java.name

    override fun isIdleNow(): Boolean {
        val idle = (recyclerView.adapter?.itemCount ?: 0) >= expectedCount
        if (idle) {
            callback?.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.callback = callback
    }
}
