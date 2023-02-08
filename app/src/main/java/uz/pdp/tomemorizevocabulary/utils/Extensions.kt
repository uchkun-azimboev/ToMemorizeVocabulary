package uz.pdp.tomemorizevocabulary.utils

import android.view.View


object Extensions {

    infix fun View.click(click: () -> Unit) {
        setOnClickListener {
            click()
        }
    }

    fun View.gone() {
        visibility = View.GONE
    }

    fun View.visible() {
        visibility = View.VISIBLE
    }

    fun View.invisible() {
        visibility = View.INVISIBLE
    }
}