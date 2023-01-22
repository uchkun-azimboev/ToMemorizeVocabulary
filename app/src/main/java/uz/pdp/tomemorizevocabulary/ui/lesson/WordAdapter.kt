package uz.pdp.tomemorizevocabulary.ui.lesson

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
import com.bumptech.glide.Glide
import uz.pdp.tomemorizevocabulary.databinding.LessonItemViewBinding
import uz.pdp.tomemorizevocabulary.databinding.WordItemViewBinding
import uz.pdp.tomemorizevocabulary.model.Lesson
import uz.pdp.tomemorizevocabulary.model.Word
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.gone

class WordAdapter : ListAdapter<Word, WordAdapter.WordViewHolder>(WordDiffCallBack()) {

    inner class WordViewHolder(private val binding: WordItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(word: Word) = binding.apply {
            word.apply {
                if (image == null) {
                    tvLetter.text = phrase[0].uppercase()
                    ivImage.setImageResource(color)
                } else {
                    tvLetter.gone()
                    ivImage.setImageResource(image!!)
                }

                tvWord.text = phrase
                tvTrans.text = meaning
                tvStats.text = "$successCount/$allCount"
            }
        }
    }

    private class WordDiffCallBack : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean = oldItem == newItem
    }

    override fun submitList(list: MutableList<Word>?) {
        super.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder =
        WordViewHolder(
            WordItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}