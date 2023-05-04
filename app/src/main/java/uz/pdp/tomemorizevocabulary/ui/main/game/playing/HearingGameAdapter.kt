package uz.pdp.tomemorizevocabulary.ui.main.game.playing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.databinding.ItemViewHearingGameBinding
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.gone
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible

class HearingGameAdapter :
    ListAdapter<Word, HearingGameAdapter.HearingGameViewHolder>(WordDiffCallBack()) {

    var ivVoiceClick: ((Word) -> Unit)? = null
    var btnStopClick: (() -> Unit)? = null
    var rightAnswer: (() -> Unit)? = null
    var wrongAnswer: (() -> Unit)? = null
    var nextWord: ((Int) -> Unit)? = null

    inner class HearingGameViewHolder(private val binding: ItemViewHearingGameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word, position: Int) = binding.apply {

            if (position == itemCount - 1) {
                btnNext.text = binding.root.context.getString(R.string.str_finish)
            }

            ivVoice click { ivVoiceClick?.invoke(word) }

            btnCheck click {
                if (!etAnswer.text.isNullOrEmpty()) {
                    val text = etAnswer.text.toString()
                    openAnswer(text, word)
                }
            }

            btnNotKnow click {
                openAnswer("", word)
            }

            btnStop click {
                btnStopClick?.invoke()
            }

            btnNext click {
                nextWord(position + 1)
            }

            ivVoiceClick?.invoke(word)
        }

        private fun openAnswer(text: String, word: Word) {
            val answer = word.phrase
            binding.apply {
                frameQuestion.gone()
                frameAnswer.visible()

                if (answer == text) {
                    rightAnswer?.invoke()
                    tvRight.visible()
                    tvWrong.gone()
                } else {
                    wrongAnswer?.invoke()
                    tvRight.gone()
                    tvWrong.visible()
                }

                tvCorrect.text = answer.plus("\n(${word.meaning})")
                tvYourAnswer.text = text
            }
        }

        private fun nextWord(position: Int) {
            if (position < itemCount) {
                nextWord?.invoke(position)
            } else {
                btnStopClick?.invoke()
            }
        }
    }

    private class WordDiffCallBack : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HearingGameViewHolder {
        return HearingGameViewHolder(
            ItemViewHearingGameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HearingGameViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}