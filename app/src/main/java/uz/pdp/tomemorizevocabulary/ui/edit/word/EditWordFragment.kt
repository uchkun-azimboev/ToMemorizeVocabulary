package uz.pdp.tomemorizevocabulary.ui.edit.word

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.databinding.FragmentEditWordBinding
import uz.pdp.tomemorizevocabulary.utils.Constants
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.toast

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.M)
class EditWordFragment : Fragment() {

    private var _bn: FragmentEditWordBinding? = null
    private val bn get() = _bn!!

    private val editWordViewModel: EditWordViewModel by viewModels()

    private lateinit var theWord: Word

    private var pickedImage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theWord = arguments?.getSerializable(getString(R.string.str_word)) as Word
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentEditWordBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = bn.apply {

        etTitle.setText(theWord.phrase)
        etDescription.setText(theWord.meaning)
        etType.setText(theWord.part)

        /*if (theWord.image != null)
            Glide.with(this@EditWordFragment).load(theWord.image).into(ivPhoto)*/

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

        ivBack click { requireActivity().onBackPressed() }
        btnCancel click { requireActivity().onBackPressed() }
        /*btnSearch click { toast(getString(R.string.str_sorry)) }
        btnLocal click { pickPhoto() }*/
        btnEdit click { editWord() }
    }

    private fun editWord() {
        if (isValidWord()) {

            theWord.phrase = bn.etTitle.text.toString().lowercase()
            theWord.meaning = bn.etDescription.text.toString().lowercase()
            theWord.part = bn.etType.text.toString().lowercase()
            theWord.image = pickedImage

            editWordViewModel.updateWord(theWord)

            findNavController().navigate(
                R.id.action_editWordFragment_to_lessonFragment,
                Bundle().apply {
                    putString(Constants.CATEGORY, theWord.categoryTitle)
                })

        } else {
            toast(getString(R.string.str_make_sure))
        }
    }

    private fun isValidWord(): Boolean {
        if (bn.etTitle.text!!.toString().trim().isEmpty()) return false
        if (bn.etType.text!!.toString().trim().isEmpty()) return false
        if (bn.etDescription.text!!.toString().trim().isEmpty()) return false
        return true
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
            if (it.resultCode == Activity.RESULT_OK) {
                pickedImage =
                    it.data!!.getParcelableArrayListExtra<Uri>(FishBun.INTENT_PATH)?.get(0)
                        .toString()
//                Glide.with(this).load(pickedImage).into(bn.ivPhoto)
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }
}