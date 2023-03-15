package uz.pdp.tomemorizevocabulary.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.data.local.entity.User
import uz.pdp.tomemorizevocabulary.databinding.FragmentLoginBinding
import uz.pdp.tomemorizevocabulary.utils.Constants.USER_NONE
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.gone
import uz.pdp.tomemorizevocabulary.utils.Extensions.invisible
import uz.pdp.tomemorizevocabulary.utils.Extensions.toast
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible
import uz.pdp.tomemorizevocabulary.utils.Resource

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _bn: FragmentLoginBinding? = null
    private val bn get() = _bn!!

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentLoginBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
    }

    private fun initViews() = bn.apply {

        btnLetsGo click {
            if (isValidUser()) {
                loginViewModel.login(
                    User(
                        name = etName.text.toString().trim(),
                        username = etUsername.text.toString().trim()
                    )
                )
            }
        }
    }

    private fun isValidUsername(): Boolean {
        for (c in bn.etUsername.text.toString()) {
            if (!(c.isLetterOrDigit() || c == '_')) {
                return false
            }
        }
        return true
    }

    private fun isValidUser(): Boolean {
        if (bn.etName.text!!.toString().trim().isEmpty()
            || bn.etUsername.text!!.toString().trim().isEmpty()
        ) {
            toast(getString(R.string.str_make_sure))
            return false
        }
        if (!isValidUsername()) {
            toast(getString(R.string.str_incorrect_username))
            return false
        }

        return true
    }

    private fun observer() {
        loginViewModel.login.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    bn.progressbar.visible()
                    bn.btnLetsGo.invisible()
                }
                Resource.Status.SUCCESS -> {
                    bn.progressbar.gone()
                    openMainFragment()
                }
                Resource.Status.ERROR -> {
                    bn.progressbar.gone()
                    bn.btnLetsGo.visible()
                    toast(it.message!!)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (loginViewModel.getState() != USER_NONE) {
            openMainFragment()
        }
    }

    private fun openMainFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }

}