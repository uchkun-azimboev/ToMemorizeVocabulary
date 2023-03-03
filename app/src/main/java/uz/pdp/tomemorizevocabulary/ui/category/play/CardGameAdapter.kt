package uz.pdp.tomemorizevocabulary.ui.category.play

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.databinding.ItemViewCardBinding
import uz.pdp.tomemorizevocabulary.ui.category.WordAdapter
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.gone
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible


class CardGameAdapter :
    ListAdapter<Word, CardGameAdapter.CardGameViewHolder>(WordAdapter.WordDiffCallBack()) {

    class CardGameViewHolder(private val binding: ItemViewCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(word: Word) = binding.apply {

            word.apply {
                tvAnswer.text = phrase
                tvQuestion.text = meaning

                /* if (image != null) {
                     ivImage.visible()
                     tvQuestion.gone()
                     Glide.with(root.context).load(image).placeholder(R.drawable.img_placeholder)
                         .into(ivImage)
                 }*/
            }

            root click {
                if (questionLayout.visibility == View.VISIBLE) {
                    questionLayout.gone()
                    answerLayout.visible()
                } else {
                    questionLayout.visible()
                    answerLayout.gone()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardGameViewHolder {
        return CardGameViewHolder(
            ItemViewCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: CardGameViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}


