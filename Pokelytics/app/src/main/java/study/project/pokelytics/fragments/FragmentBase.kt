package study.project.pokelytics.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class FragmentBase<T : ViewDataBinding> : Fragment() {

    private var _binding: T? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            getResourceLayout(),
            container,
            false
        )
        initializeView()
        bindViewModel()
        subscribe()
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun bindViewModel()

    abstract fun initializeView()

    abstract fun getResourceLayout(): Int

    abstract fun subscribe()

}