package study.project.pokelytics.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class ViewModalBase : ViewModel() {

    protected val mutableState = MutableLiveData<ViewState>()
    open val state: LiveData<ViewState>
        get() = mutableState

    init {
        mutableState.postValue(ViewState.IDLE)
    }

    fun refreshState(state: ViewState){
        mutableState.postValue(state)
    }

}