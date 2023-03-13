package uz.pdp.tomemorizevocabulary.ui.category.play


import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.databinding.FragmentPlayBinding
import uz.pdp.tomemorizevocabulary.utils.Constants
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.gone
import uz.pdp.tomemorizevocabulary.utils.Extensions.toast
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible
import uz.pdp.tomemorizevocabulary.utils.Resource
import uz.pdp.tomemorizevocabulary.viewmodel.WordViewModel

@AndroidEntryPoint
class PlayFragment : Fragment() {

    private var _bn: FragmentPlayBinding? = null
    private val bn: FragmentPlayBinding get() = _bn!!

    private val wordViewModel: WordViewModel by viewModels()

    private val rvAdapter = CardGameAdapter()

    private var itemCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _bn = FragmentPlayBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
    }

    override fun onStart() {
        super.onStart()
        wordViewModel.getRandomWords(arguments?.getString(Constants.CATEGORY) ?: "")
    }

    private fun initViews() = bn.apply {

        ivBack click { requireActivity().onBackPressed() }

        cardStackView.adapter = rvAdapter

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
                        toast(getString(R.string.str_i_know), 1)
                        rightView.setBackgroundResource(R.color.dark)
                    } else if (direction == Direction.Left) {
                        toast(getString(R.string.str_i_dont_know), 0)
                        leftView.setBackgroundResource(R.color.dark)
                    }
                }

                override fun onCardRewound() {

                }

                override fun onCardCanceled() {

                }

                override fun onCardAppeared(view: View?, position: Int) {
                    tvStats.text = getStats(position + 1, itemCount)
                    Log.d("PlayFragment", "onCardAppeared : $position")
                }

                override fun onCardDisappeared(view: View?, position: Int) {

                    wordViewModel.incrementAllCount(rvAdapter.currentList[position].id!!)

                    if (cardDirection == Direction.Right) {
                        wordViewModel.incrementSuccessCount(rvAdapter.currentList[position].id!!)
                    }

                    if (position == itemCount - 1) bn.tvInfo.visible()
                    Log.d("PlayFragment", "onCardDisappeared : $position")
                }

            }).apply {
                setCanScrollVertical(false)
                setMaxDegree(0.0f)
                setStackFrom(StackFrom.Bottom)
            }
    }

    private fun observer() {
        wordViewModel.randomWords.observe(viewLifecycleOwner) {
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