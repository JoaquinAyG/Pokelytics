package study.project.pokelytics.fragments.login

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import study.project.pokelytics.R
import study.project.pokelytics.databinding.FragmentResetPasswordBinding
import study.project.pokelytics.fragments.FragmentBase
import study.project.pokelytics.models.LoginCredentials
import study.project.pokelytics.viewmodels.ResetPasswordViewModel
import study.project.pokelytics.viewmodels.ViewState

class ResetPasswordFragment : FragmentBase<FragmentResetPasswordBinding>() {

    override fun getResourceLayout(): Int = R.layout.fragment_reset_password
    private val resetPasswordViewModel: ResetPasswordViewModel by viewModel()

    override fun initializeView() {
        binding.apply {
            btnReset.setOnClickListener{
                if (email.text.isEmpty()){
                    showError(etemail, resources.getString(R.string.emailRequired))
                }else if(!email.text.contains("@")){
                    showError(etemail, resources.getString(R.string.emailValid))
                }else {
                    val credentials = LoginCredentials(email.text.toString(), "", "")
                    resetPasswordViewModel.resetPassword(credentials)
                    Toast.makeText(requireContext(), resources.getString(R.string.checkMail), Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.resetPasswordFragmentToLogInFragment)
                }
            }
            btnCancel.setOnClickListener{
                findNavController().navigate(R.id.resetPasswordFragmentToLogInFragment)
            }
        }
    }

    override fun bindViewModel() {
        binding.apply {
            lifecycleOwner = this@ResetPasswordFragment
        }
    }

    override fun subscribe() {
        resetPasswordViewModel.state.observe(viewLifecycleOwner){
            when(it){
                ViewState.SUCCESS -> {
                    //Aqui no funciona
                    //Toast.makeText(requireContext(), resources.getString(R.string.checkMail), Toast.LENGTH_LONG).show()
                    //findNavController().navigate(R.id.resetPasswordFragmentToLogInFragment)
                }
                ViewState.ERROR ->{
                    showErrorResetPassword()
                }
                else -> {}
            }
        }
    }

    private fun showError(textInputLayout: TextInputLayout, error: String){
        textInputLayout.error = error
    }
    private fun showErrorResetPassword(){
        Toast.makeText(requireContext(), resources.getString(R.string.emailOrPassWrong), Toast.LENGTH_SHORT).show()
    }

    val callback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            findNavController().navigate(R.id.resetPasswordFragmentToLogInFragment)
        }
    }
}