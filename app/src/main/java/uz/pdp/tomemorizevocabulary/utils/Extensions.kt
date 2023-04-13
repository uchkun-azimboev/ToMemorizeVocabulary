package uz.pdp.tomemorizevocabulary.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.saadahmedsoft.popupdialog.PopupDialog
import com.saadahmedsoft.popupdialog.Styles
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
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

    fun Fragment.getScoreColor(stats: Int): Int {
        return ContextCompat.getColor(
            requireContext(),
            when (stats) {
                in 0..54 -> R.color.gray
                in 55..70 -> R.color.orange
                in 71..85 -> R.color.blue
                else -> R.color.green
            }
        )
    }

    fun Fragment.makeDialog(title: String, msg: String, click: () -> Unit) {
        PopupDialog.getInstance(requireContext())
            .setStyle(Styles.IOS)
            .setHeading(title)
            .setDescription(msg)
            .setCancelable(false)
            .setPositiveButtonText(getString(R.string.str_allow))
            .showDialog(object : OnDialogButtonClickListener() {
                override fun onPositiveClicked(dialog: Dialog?) {
                    click.invoke()
                    dialog?.dismiss()
                }
            })
    }

    @SuppressLint("ResourceAsColor")
    fun Fragment.toast(msg: String, color: Int = 0) {
        Snackbar.make(requireParentFragment().requireView(), msg, Snackbar.LENGTH_SHORT).apply {
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
                typeface =
                    ResourcesCompat.getFont(requireActivity().applicationContext, R.font.poppins)
                setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            }
            view.setBackgroundResource(R.drawable.rounded_background_blue)
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

    fun RecyclerView.setItemTouchHelper(swipeLeft: (Int) -> Unit, swipeRight: (Int) -> Unit) {
        val callback: ItemTouchHelper.SimpleCallback =
            object :
                ItemTouchHelper.SimpleCallback(
                    0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
                ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    val position = viewHolder.bindingAdapterPosition
                    adapter?.notifyItemChanged(position)

                    if (direction == ItemTouchHelper.RIGHT) {
                        swipeRight.invoke(position)
                    } else {
                        swipeLeft.invoke(position)
                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {

                    /*  val green = ContextCompat.getColor(context, R.color.green)
                      val orange = ContextCompat.getColor(context, R.color.orange)*/
                    val gray = ContextCompat.getColor(context, R.color.gray)


                    RecyclerViewSwipeDecorator.Builder(
                        c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                    )
                        .addSwipeLeftBackgroundColor(gray)
                        .addSwipeRightBackgroundColor(gray)
                        .addSwipeLeftActionIcon(R.drawable.ic_delete)
                        .addSwipeRightActionIcon(R.drawable.ic_edit)
                        .addSwipeLeftLabel(context.getString(R.string.str_delete).uppercase())
                        .addSwipeRightLabel(context.getString(R.string.str_edit).uppercase())
                        .setSwipeLeftLabelColor(Color.WHITE)
                        .setSwipeRightLabelColor(Color.WHITE)
                        .setSwipeLeftActionIconTint(Color.WHITE)
                        .setSwipeRightActionIconTint(Color.WHITE)
                        .addCornerRadius(TypedValue.COMPLEX_UNIT_DIP, 10)
                        .create()
                        .decorate()

                    super.onChildDraw(
                        c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                    )
                }
            }

        ItemTouchHelper(callback).attachToRecyclerView(this)
    }
}