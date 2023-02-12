package uz.pdp.tomemorizevocabulary.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.tomemorizevocabulary.databinding.ItemViewCategoryBinding
import uz.pdp.tomemorizevocabulary.model.Category
import uz.pdp.tomemorizevocabulary.utils.Extensions.click

class CategoryAdapter :
    ListAdapter<Category, CategoryAdapter.LessonViewHolder>(LessonDiffCallBack()) {

    var itemClick: ((Category) -> Unit)? = null

    inner class LessonViewHolder(private val binding: ItemViewCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(category: Category) = binding.apply {
            category.apply {
                backCard.setCardBackgroundColor(ContextCompat.getColor(root.context, color))
                tvTitle.text = title
                tvCreateDate.text = createDate
                tvDescription.text = description
                tvWordCount.text = "$wordCount words"
            }

            root click { itemClick?.invoke(category) }
        }
    }

    private class LessonDiffCallBack : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder =
        LessonViewHolder(
            ItemViewCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}