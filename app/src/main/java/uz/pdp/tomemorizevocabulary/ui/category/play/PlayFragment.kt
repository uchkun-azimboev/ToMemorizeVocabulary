package uz.pdp.tomemorizevocabulary.ui.category.play


import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.databinding.FragmentPlayBinding
import uz.pdp.tomemorizevocabulary.ui.TTSFragment
import uz.pdp.tomemorizevocabulary.utils.Constants
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.getScoreColor
import uz.pdp.tomemorizevocabulary.utils.Extensions.gone
import uz.pdp.tomemorizevocabulary.utils.Extensions.toast
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible
import uz.pdp.tomemorizevocabulary.utils.Resource
import uz.pdp.tomemorizevocabulary.utils.Utils.getScoreTitle
import java.util.*

@AndroidEntryPoint
class PlayFragment : TTSFragment() {

    private var _bn: FragmentPlayBinding? = null
    private val bn: FragmentPlayBinding get() = _bn!!

    private val playViewModel: PlayViewModel by viewModels()
    private val rvAdapter = CardGameAdapter()
    private var itemCount = 0
    private var success = 0
    private var categoryId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryId = arguments?.getInt(Constants.CATEGORY_ID) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _bn = FragmentPlayBinding.inflate(inflater, container, false)
        setUpTTS()
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
    }

    override fun onStart() {
        super.onStart()

        if (itemCount == 0) {
            playViewModel.getRandomWords(categoryId)
        }
    }

    private fun initViews() = bn.apply {

        ivBack click { requireActivity().onBackPressed() }

        btnOk click {
            findNavController().navigate(
                R.id.action_playFragment_to_lessonFragment,
                arguments
            )
        }

        cardStackView.adapter = rvAdapter.apply {
            soundClick = { speakText(it) }
        }

        cardStackView.layoutManager =
            CardStackLayoutManager(requireContext(), object : CardStackListener {

                var isDrag = false
                var cardDirection = Direction.Left

                @SuppressLint("ResourceAsColor")
                override fun onCardDragging(direction: Direction?, ratio: Float) {

                    cardDirection = if (direction == Direction.Right) {
                        Direction.Right
                    } else {
                        Direction.Left
                    }

                    if (ratio > 0.75f) {
                        isDrag = true
                        if (direction == Direction.Left) {
                            leftView.setBackgroundResource(R.drawable.gradient_fail)
                        } else if (direction == Direction.Right) {
                            rightView.setBackgroundResource(R.drawable.gradient_success)
                        }
                    } else if (isDrag) {
                        isDrag = false
                        leftView.setBackgroundResource(R.color.dark)
                        rightView.setBackgroundResource(R.color.dark)
                    }
                }

                override fun onCardSwiped(direction: Direction?) {
                    if (direction == Direction.Right) {
                        rightView.setBackgroundResource(R.color.dark)
                    } else if (direction == Direction.Left) {
                        leftView.setBackgroundResource(R.color.dark)
                    }
                }

                override fun onCardRewound() {

                }

                override fun onCardCanceled() {

                }

                override fun onCardAppeared(view: View?, position: Int) {
                    tvStats.text = getStats(position + 1, itemCount)
                }

                override fun onCardDisappeared(view: View?, position: Int) {

                    playViewModel.incrementAllCount(rvAdapter.currentList[position].id!!)

                    if (cardDirection == Direction.Right) {
                        success++
                        playViewModel.incrementSuccessCount(rvAdapter.currentList[position].id!!)
                    }

                    if (position == itemCount - 1) {
                        showResult()
                    }
                }

            }).apply {
                setCanScrollVertical(false)
                setMaxDegree(0.0f)
                setStackFrom(StackFrom.Bottom)
            }
    }

    private fun showResult() {
        val stats: Int = (success.toFloat() / itemCount * 100).toInt()
        setCategoryColor(stats)
        if (stats >= 75) playRightSound()
        else playWrongSound()
        bn.apply {
            flMain.gone()
            llResult.visible()
            tvScore.text = stats.toString().plus("%")
            tvScore.setTextColor(getScoreColor(stats))
            tvInfo.text = getString(getScoreTitle(stats))
            tvInfo.setTextColor(getScoreColor(stats))
        }
    }

    private fun setCategoryColor(stats: Int) {
        if (stats > 84) {
            playViewModel.incrementColor(categoryId)
        }
    }

    private fun observer() {
        playViewModel.randomWords.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    bn.progressbar.visible()
                }
                Resource.Status.SUCCESS -> {
                    itemCount = it.data?.size ?: 0
                    bn.tvStats.text = getStats(b = itemCount)
                    rvAdapter.submitList(it.data)
                    bn.progressbar.gone()
                }
                Resource.Status.ERROR -> {}
            }
        }
    }

    private fun getStats(a: Int = 1, b: Int): String {
        return "$a/$b"
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }
}