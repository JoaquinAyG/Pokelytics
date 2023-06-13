package study.project.pokelytics.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import study.project.pokelytics.Navigator

abstract class ActivityBase<T : ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: T
    val navigator: Navigator by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getResourceLayout())
        bindViewModel()
        initializeView()
        subscribe()
        setTopBar()
    }

    private fun setTopBar() {}

    protected abstract fun getResourceLayout(): Int

    protected abstract fun initializeView()

    protected abstract fun bindViewModel()

    protected abstract fun subscribe()

    open fun showLoading(show: Boolean) {}

}
