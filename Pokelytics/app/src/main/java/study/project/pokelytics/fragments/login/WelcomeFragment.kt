package study.project.pokelytics.fragments.login

import androidx.navigation.fragment.findNavController
import study.project.pokelytics.R
import study.project.pokelytics.databinding.FragmentWelcomeBinding
import study.project.pokelytics.fragments.FragmentBase


class WelcomeFragment : FragmentBase<FragmentWelcomeBinding>() {

    override fun getResourceLayout(): Int = R.layout.fragment_welcome

    override fun initializeView() {
        binding.btnNext.setOnClickListener{
            findNavController().navigate(R.id.welcomeFragmentToLoginSelectionFragment)
        }
    }

    override fun bindViewModel() {
        binding.apply {
            lifecycleOwner = this@WelcomeFragment
        }
    }

    override fun subscribe() {
    }

}