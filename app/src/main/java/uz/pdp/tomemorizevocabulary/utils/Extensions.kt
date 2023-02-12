package uz.pdp.tomemorizevocabulary.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
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
            startAnimation(AnimationUtils.loadAnimation(context, R.anim.item_click))
            click()
        }
    }

    fun Fragment.toast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
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