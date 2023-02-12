package uz.pdp.tomemorizevocabulary.ui.category

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.pdp.tomemorizevocabulary.databinding.ItemViewWordBinding
import uz.pdp.tomemorizevocabulary.model.Word
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible

class WordAdapter : ListAdapter<Word, WordAdapter.WordViewHolder>(WordDiffCallBack()) {

    inner class WordViewHolder(private val binding: ItemViewWordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(word: Word) = binding.apply {
            word.apply {

                if (image != null)
                    Glide.with(root).load(image).into(ivImage)
                else
                    tvLetter.apply {
                        visible()
                        text = phrase[0].toString().uppercase()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder =
        WordViewHolder(
            ItemViewWordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}