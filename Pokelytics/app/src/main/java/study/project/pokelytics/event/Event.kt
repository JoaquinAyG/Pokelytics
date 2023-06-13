package study.project.pokelytics.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>
typealias EmptyMutableLiveEvent = MutableLiveData<Event<Unit>>
typealias LiveEvent<T> = LiveData<Event<T>>
typealias EmptyLiveEvent = LiveData<Event<Unit>>

fun <T> MutableLiveEvent<out T>.postEvent(t: T) {
    this.postValue(Event<T>(t))
}

open class Event<out T>(private val content: T) {

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}

fun <T> LiveEvent<T>.observeEvent(owner: LifecycleOwner, observer: Observer<in T>) {
    this.observe(owner, EventObserver { it?.let { observer.onChanged(it) } })
}

fun <T> LiveEvent<T>.observeEventAndUnsubscribe(owner: LifecycleOwner, observer: Observer<in T>) {
    this.observe(owner, EventObserver {
        it?.let {
            observer.onChanged(it)
            removeObservers(owner)
        }
    })
}