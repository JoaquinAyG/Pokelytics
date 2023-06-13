package study.project.pokelytics.viewholders

import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import study.project.pokelytics.event.LiveEvent
import study.project.pokelytics.event.observeEvent

abstract class BaseViewHolder<in T>(containerView: View): RecyclerView.ViewHolder(containerView),
    LifecycleOwner {

    abstract fun bind(item: T)

    protected var lifecycleRegistry: LifecycleRegistry = createLifeCycle()

    private fun createLifeCycle(): LifecycleRegistry {
        val lifecycle = LifecycleRegistry(this)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        return lifecycle
    }

    @CallSuper
    open fun onAttached() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    @CallSuper
    open fun onDetached() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    @CallSuper
    open fun onRecycled() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        lifecycleRegistry = createLifeCycle()
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    protected inline fun <RT> LiveData<RT>.observeChanges(crossinline onChangesUpdated: (t: RT) -> Unit) {
        this.observe(this@BaseViewHolder) {
            it?.let(onChangesUpdated)
        }
    }

    protected inline fun <RT> LiveEvent<RT>.observeEventChanges(crossinline onChangesUpdated: (t: RT) -> Unit) {
        this.observeEvent(this@BaseViewHolder) {
            it?.let(onChangesUpdated)
        }
    }
}