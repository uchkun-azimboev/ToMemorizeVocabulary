package uz.pdp.tomemorizevocabulary.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.databinding.FragmentMainBinding
import uz.pdp.tomemorizevocabulary.data.local.entity.Category
import uz.pdp.tomemorizevocabulary.utils.Constants
import uz.pdp.tomemorizevocabulary.utils.Constants.USERNAME
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.gone
import uz.pdp.tomemorizevocabulary.utils.Extensions.toast
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible
import uz.pdp.tomemorizevocabulary.utils.Resource
import uz.pdp.tomemorizevocabulary.viewmodel.CategoryViewModel
import uz.pdp.tomemorizevocabulary.viewmodel.UserViewModel

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _bn: FragmentMainBinding? = null
    private val binding get() = _bn!!

    private val categoryViewModel: CategoryViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val categoryAdapter = CategoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _bn = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
    }

    override fun onStart() {
        super.onStart()
        categoryViewModel.getAllCategory()
        if (arguments != null) {
            userViewModel.getUser(arguments?.getString(USERNAME)!!)
        }
    }

    private fun initViews() = binding.apply {

        rvLessons.adapter = categoryAdapter.apply {
            itemClick = {
                openAddWordFragment(it)
            }
        }

        frameCreate click {
            findNavController().navigate(R.id.action_mainFragment_to_lessonCreateFragment)
        }

        frameSearch click {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
    }

    private fun setUpRv(list: List<Category>?) {
        binding.apply {
            progressbar.gone()
            if (list.isNullOrEmpty()) {
                tvNoneInfo.visible()
                lessonsLine.gone()
                return

            } else {
                lessonsLine.visible()
                tvNoneInfo.gone()
            }

            categoryAdapter.submitList(list)
        }
    }

    private fun observer() {
        categoryViewModel.categories.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressbar.visible()
                }
                Resource.Status.SUCCESS -> {
                    setUpRv(it.data)
                }
                Resource.Status.ERROR -> {
                    setUpRv(null)
                }
            }
        }

        userViewModel.user.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    val user = it.data!!
                    binding.apply {
                        tvHello.text =
                            getString(R.string.str_hello).plus(" ").plus(user.name).plus(" !")
                        tvNickname.text = user.username
                        tvNumAll.text = user.allCategories.toString()
                        tvNumCompleted.text = user.completed.toString()
                        tvNumToDo.text = (user.allCategories - user.completed).toString()
                    }
                }
                Resource.Status.ERROR -> {
                    toast(it.message!!)
                }
            }
        }
    }

    private fun openAddWordFragment(it: Category) {
        val args = Bundle()
        args.putString(Constants.CATEGORY, it.title)
        findNavController().navigate(R.id.action_mainFragment_to_lessonFragment, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bn = null
    }
}