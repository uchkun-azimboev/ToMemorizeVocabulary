package uz.pdp.tomemorizevocabulary.ui.main

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.tomemorizevocabulary.databinding.LessonItemViewBinding
import uz.pdp.tomemorizevocabulary.model.Lesson
import uz.pdp.tomemorizevocabulary.utils.Extensions.click

class LessonAdapter(var itemClick: (Lesson) -> Unit) :
    ListAdapter<Lesson, LessonAdapter.LessonViewHolder>(LessonDiffCallBack()) {

    inner class LessonViewHolder(private val binding: LessonItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(lesson: Lesson) = binding.apply {
            lesson.apply {
                backCard.setCardBackgroundColor(ContextCompat.getColor(root.context, color))
                tvTitle.text = title
                tvCreateDate.text = createDate
                tvDescription.text = description
                tvWordCount.text = "$wordCount words"
            }

            root click { itemClick.invoke(lesson) }
        }
    }

    private class LessonDiffCallBack : DiffUtil.ItemCallback<Lesson>() {
        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder =
        LessonViewHolder(
            LessonItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}