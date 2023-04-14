package uz.pdp.tomemorizevocabulary.ui.category.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.databinding.ItemViewWordBinding
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.isMinus
import uz.pdp.tomemorizevocabulary.utils.Extensions.isNotMinus
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible

class WordAdapter : ListAdapter<Word, WordAdapter.WordViewHolder>(WordDiffCallBack()) {

    var soundClick: ((String) -> Unit)? = null

    inner class WordViewHolder(private val binding: ItemViewWordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(word: Word, soundClick: ((String) -> Unit)?) = binding.apply {
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
                tvType.text = part

                if (!example.isNullOrEmpty()) {

                    llExample.visible()

                    var text = example!!

                    var start = text.indexOf("*")
                    var finish = text.lastIndexOf("*") - 1

                    if (start.isMinus() || finish.isMinus() || start == finish) {
                        start = text.indexOf(phrase)
                        finish = start + phrase.length
                    } else {
                        text = text.replace("*", "")
                    }

                    if (start.isNotMinus() && finish.isNotMinus()) {
                        val spannable = SpannableString(text)
                        spannable.setSpan(
                            StyleSpan(Typeface.BOLD),
                            start,
                            finish,
                            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                        spannable.setSpan(
                            ForegroundColorSpan(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.yellow
                                )
                            ),
                            start,
                            finish,
                            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                        tvExample.text = spannable
                    } else {
                        tvExample.text = example
                    }
                }
            }

            ivVoice click {
                soundClick?.invoke(word.phrase)
            }
        }
    }

    class WordDiffCallBack : DiffUtil.ItemCallback<Word>() {
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
        holder.bind(getItem(position), soundClick)
    }
}