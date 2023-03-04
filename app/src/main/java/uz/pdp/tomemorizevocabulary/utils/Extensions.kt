package uz.pdp.tomemorizevocabulary.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.model.photos.Photo


object Extensions {

    fun RequestManager.setup(photo: Photo, view: ImageView) {
        load(photo.src.medium).placeholder(ColorDrawable(Color.parseColor(photo.avg_color)))
            .into(view)
    }

    infix fun View.click(click: () -> Unit) {
        setOnClickListener {
            click()
        }
    }

    infix fun View.animatedClick(click: () -> Unit) {
        setOnClickListener {
            startAnimation(AnimationUtils.loadAnimation(context, R.anim.photo_item_click))
            click()
        }
    }

    @SuppressLint("ResourceAsColor")
    fun Fragment.toast(msg: String, color: Int = 0) {
        Snackbar.make(requireParentFragment().requireView(), msg, Snackbar.LENGTH_SHORT).apply {
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
                typeface =
                    ResourcesCompat.getFont(requireActivity().applicationContext, R.font.poppins)
                setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            }
            view.setBackgroundResource(if (color == 0) R.drawable.rounded_background_orange else R.drawable.rounded_background_green)
        }.show()
    }

    fun Fragment.hideSoftKeyboard(
        view: View? = requireActivity().currentFocus
    ) {
        view?.let {
            val inputMethodManager =
                ContextCompat.getSystemService(requireActivity(), InputMethodManager::class.java)!!
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }

        view?.clearFocus()
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