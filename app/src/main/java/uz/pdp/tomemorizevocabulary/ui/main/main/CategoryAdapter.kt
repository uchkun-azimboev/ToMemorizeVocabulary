package uz.pdp.tomemorizevocabulary.ui.main.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.databinding.ItemViewCategoryBinding
import uz.pdp.tomemorizevocabulary.data.local.entity.Category
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible

class CategoryAdapter :
    ListAdapter<Category, CategoryAdapter.LessonViewHolder>(LessonDiffCallBack()) {

    var itemClick: ((Category) -> Unit)? = null

    inner class LessonViewHolder(private val binding: ItemViewCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) = binding.apply {
            category.apply {
                backCard.setCardBackgroundColor(
                    ContextCompat.getColor(
                        root.context,
                        getColor(color)
                    )
                )
                tvTitle.text = title
                tvCreateDate.text = createDate
                tvDescription.text = description
                tvWordCount.text =
                    wordCount.toString().plus(" ").plus(root.context.getString(R.string.str_words))

                when (category.color) {
                    1 -> tvNew.visible()
                    in 2..3 -> tvProgress.visible()
                    else -> tvSuccessful.visible()
                }
            }

            root click { itemClick?.invoke(category) }
        }

        private fun getColor(color: Int): Int {
            return when (color) {
                1 -> R.color.pure_gray
                2 -> R.color.orange
                3 -> R.color.blue
                else -> R.color.green
            }
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