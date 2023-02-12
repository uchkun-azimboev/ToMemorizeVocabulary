package uz.pdp.tomemorizevocabulary.ui.category.add

import android.app.Activity.RESULT_OK
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.databinding.FragmentAddWordBinding
import uz.pdp.tomemorizevocabulary.model.Word
import uz.pdp.tomemorizevocabulary.model.photos.Photo
import uz.pdp.tomemorizevocabulary.model.photos.ThreePhoto
import uz.pdp.tomemorizevocabulary.utils.Constants
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.gone
import uz.pdp.tomemorizevocabulary.utils.Extensions.toast
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible
import uz.pdp.tomemorizevocabulary.utils.Resource
import uz.pdp.tomemorizevocabulary.viewmodel.WordViewModel


@AndroidEntryPoint
class AddWordFragment : Fragment() {

    private val tag = "AddWordFragment"

    private var _bn: FragmentAddWordBinding? = null
    private val bn get() = _bn!!

    private val viewModel: WordViewModel by viewModels()

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
            viewModel.getPhotos(1, etTitle.text.toString())
        }

        btnLocal click {
            pickUserPhoto()
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
    }

    private fun isValidWord(): Boolean {
        if (bn.etTitle.text!!.toString().trim().isEmpty()) return false
        if (bn.etType.text!!.toString().trim().isEmpty()) return false
        if (bn.etDescription.text!!.toString().trim().isEmpty()) return false
        return true
    }

    private fun createWord() {
        if (isValidWord()) {

            viewModel.insertWordToRoom(
                Word(
                    phrase = bn.etTitle.text.toString(),
                    meaning = bn.etDescription.text.toString(),
                    part = bn.etType.text.toString(),
                    image = pickedImage,
                    category = arguments?.getString(Constants.CATEGORY)
                )
            )

            findNavController().navigate(R.id.action_addWordFragment_to_lessonFragment, arguments)

        } else {
            toast(getString(R.string.str_make_sure))
        }
    }

    private fun pickUserPhoto() {
        FishBun.with(this)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(1)
            .setMinCount(1)
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
        viewModel.photos.observe(viewLifecycleOwner) {
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