package uz.pdp.tomemorizevocabulary.ui.category.add

import android.app.Activity.RESULT_OK
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.databinding.FragmentAddWordBinding
import uz.pdp.tomemorizevocabulary.model.photos.Photo
import uz.pdp.tomemorizevocabulary.model.photos.ThreePhoto
import uz.pdp.tomemorizevocabulary.utils.Constants
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.gone
import uz.pdp.tomemorizevocabulary.utils.Extensions.toast
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible
import uz.pdp.tomemorizevocabulary.utils.Resource
import uz.pdp.tomemorizevocabulary.viewmodel.CategoryViewModel
import uz.pdp.tomemorizevocabulary.viewmodel.PhotoViewModel
import uz.pdp.tomemorizevocabulary.viewmodel.WordViewModel


@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.M)
class AddWordFragment : Fragment() {

    private val tag = "AddWordFragment"

    private var _bn: FragmentAddWordBinding? = null
    private val bn get() = _bn!!

    private val wordViewModel: WordViewModel by viewModels()
    private val photoViewModel: PhotoViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()

    private val rvAdapter = PhotoAdapter()

    private var pickedImage: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _bn = FragmentAddWordBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
    }


    private fun initViews() = bn.apply {

        rvPhotos.adapter = rvAdapter.apply {
            itemClick = { photo ->
                pickedImage = photo.src.medium
                Glide.with(this@AddWordFragment).load(pickedImage)
                    .into(ivPhoto)
            }
        }

        btnSearch click {

            toast(getString(R.string.str_sorry))

//            photoViewModel.getPhotos(1, etTitle.text.toString())
        }

        btnLocal click {
            pickPhoto()
        }

        btnCancel click {
            requireActivity().onBackPressed()
        }

        ivBack click {
            requireActivity().onBackPressed()
        }

        btnCreate click {
            createWord()
        }

        etType.apply {
            setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.item_dropdown_menu,
                    resources.getStringArray(R.array.part_of_speech)
                )
            )
            setDropDownBackgroundResource(R.color.pure_gray)
        }
    }

    private fun isValidWord(): Boolean {
        if (bn.etTitle.text!!.toString().trim().isEmpty()) return false
        if (bn.etType.text!!.toString().trim().isEmpty()) return false
        if (bn.etDescription.text!!.toString().trim().isEmpty()) return false
        return true
    }

    private fun createWord() {
        if (isValidWord()) {

            val title = arguments?.getString(Constants.CATEGORY)!!

            wordViewModel.insertWordToRoom(
                Word(
                    phrase = bn.etTitle.text.toString().lowercase(),
                    meaning = bn.etDescription.text.toString().lowercase(),
                    part = bn.etType.text.toString().lowercase(),
                    image = pickedImage,
                    categoryTitle = title
                )
            )

            categoryViewModel.incrementWordCount(title)

            findNavController().navigate(R.id.action_addWordFragment_to_lessonFragment, arguments)

        } else {
            toast(getString(R.string.str_make_sure))
        }
    }


    private fun pickPhoto() {
        FishBun.with(this)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(1)
            .setMinCount(1)
            .setActionBarColor(
                requireActivity().getColor(R.color.dark),
                requireActivity().getColor(R.color.dark)
            )
            .hasCameraInPickerPage(true)
            .startAlbumWithActivityResultCallback(photoLauncher)
    }

    private val photoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                pickedImage =
                    it.data!!.getParcelableArrayListExtra<Uri>(FishBun.INTENT_PATH)?.get(0)
                        .toString()
                Glide.with(this).load(pickedImage).into(bn.ivPhoto)
            }
        }


    private fun observer() {
        photoViewModel.photos.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    bn.progressbar.visible()
                    bn.rvPhotos.gone()
                }
                Resource.Status.SUCCESS -> {
                    bn.progressbar.gone()
                    bn.rvPhotos.visible()
                    rvAdapter.submitList(getThreePhotos(it.data?.photos))
                }
                Resource.Status.ERROR -> {
                    bn.progressbar.gone()
                    Log.d(tag, "${it.message}")
                }
            }
        }
    }

    private fun getThreePhotos(photos: ArrayList<Photo>?): MutableList<ThreePhoto>? {
        if (photos == null) return null
        val list = ArrayList<ThreePhoto>()
        var i = 0
        while (i < photos.size) {
            list.add(
                ThreePhoto(
                    photos[i],
                    if (i + 1 < photos.size) photos[i + 1] else null,
                    if (i + 2 < photos.size) photos[i + 2] else null
                )
            )
            i += 3
        }
        return list
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }
}