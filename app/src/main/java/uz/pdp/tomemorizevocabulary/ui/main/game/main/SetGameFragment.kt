package uz.pdp.tomemorizevocabulary.ui.main.game.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.databinding.FragmentSetGameBinding
import uz.pdp.tomemorizevocabulary.model.GameInfo
import uz.pdp.tomemorizevocabulary.ui.main.main.CategoryAdapter
import uz.pdp.tomemorizevocabulary.utils.Constants
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.gone
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible
import uz.pdp.tomemorizevocabulary.utils.Resource

@AndroidEntryPoint
class SetGameFragment : BottomSheetDialogFragment() {

    private var _bn: FragmentSetGameBinding? = null
    private val bn get() = _bn!!

    private val setGameViewModel: SetGameViewModel by viewModels()
    private val categoryAdapter = CategoryAdapter()
    private var categoryId = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentSetGameBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
    }

    private fun initViews() = bn.apply {

        btnCancel click { dismiss() }

        btnPlay click {
            if (categoryId != -1 || etGameType.text.isNotEmpty() && !etCount.text.isNullOrEmpty()) {
                playGame()
            }
        }

        rvCategory.adapter = categoryAdapter.apply {
            itemClick = {
                viewButtons.visible()
                categoryId = it.id
                submitList(arrayListOf(it))
            }
        }

        etGameType.apply {
            setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.item_dropdown_menu,
                    resources.getStringArray(R.array.game_types)
                )
            )
            setDropDownBackgroundResource(R.color.pure_gray)

            doOnTextChanged { text, _, _, _ ->
                if (text.toString() == Constants.IN_CATEGORY) {
                    viewCounter.gone()
                    viewButtons.gone()
                    viewCategory.visible()
                    setGameViewModel.getAllCategory()
                } else {
                    viewCategory.gone()
                    viewCounter.visible()
                    viewButtons.visible()
                }

            }
        }
    }

    private fun playGame() {
        val gameInfo = GameInfo(
            game = requireArguments().getString(Constants.GAME_TYPE)!!,
            categoryId = categoryId,
            gameType = bn.etGameType.text.toString()
        )
        if (!bn.etCount.text.isNullOrEmpty()) {
            gameInfo.wordCount = bn.etCount.text.toString().toInt()
        }
        val args = Bundle().apply {
            putSerializable(Constants.GAME_INFO, gameInfo)
        }
        findNavController().navigate(R.id.action_setGameFragment_to_hearingGameFragment, args)
    }

    private fun observer() {
        setGameViewModel.categories.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    bn.progressbar.visible()
                }

                Resource.Status.SUCCESS -> {
                    bn.progressbar.gone()
                    categoryAdapter.submitList(it.data)
                }

                Resource.Status.ERROR -> {
                    bn.progressbar.gone()
                    bn.viewButtons.gone()
                    bn.viewCategory.gone()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }

}