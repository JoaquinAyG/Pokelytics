package study.project.pokelytics.fragments.login

import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import study.project.pokelytics.R
import study.project.pokelytics.activities.ActivityBase
import study.project.pokelytics.databinding.FragmentLoginSelectionBinding
import study.project.pokelytics.fragments.FragmentBase
import study.project.pokelytics.models.User

class LoginSelectionFragment : FragmentBase<FragmentLoginSelectionBinding>() {

    override fun getResourceLayout(): Int = R.layout.fragment_login_selection

    override fun initializeView() {
        binding.apply {
            btnSignUp.setOnClickListener{
                findNavController().navigate(R.id.loginSelectionFragmentToSignUpFragment)
            }
            btnLogin.setOnClickListener{
                findNavController().navigate(R.id.loginSelectionFragmentToLogInFragment)
            }
            btnNext.setOnClickListener{
                (activity as ActivityBase<*>).navigator.goToMain(User.getDefaultUser())
                activity?.finish()
            }
        }
    }

    override fun bindViewModel() {
        binding.apply {
            lifecycleOwner = this@LoginSelectionFragment
        }
    }

    override fun subscribe() {
    }

    val callback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            findNavController().navigate(R.id.loginSelectionFragmentToWelcomeFragment)
        }
    }


}