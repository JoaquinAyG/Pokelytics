package study.project.pokelytics.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import study.project.pokelytics.api.model.Item
import study.project.pokelytics.api.model.PaginationRange
import study.project.pokelytics.usecases.GetBerryUseCase

class BerryViewModel(
    val getBerryUseCase: GetBerryUseCase
) : ViewModalBase() {

    private val mutableBerryList = MutableLiveData<List<Item>>()
    val berryList: LiveData<List<Item>>
        get() = mutableBerryList

    fun getBerryList(paginationRange: PaginationRange) {
        mutableState.postValue(ViewState.LOADING)
        viewModelScope.launch {
            getBerryUseCase(
                paginationRange,
                { itemList ->
                    mutableState.postValue(ViewState.SUCCESS)
                    mutableBerryList.postValue(itemList)
                }, {
                    mutableState.postValue(ViewState.ERROR)
                }
            )
        }
    }
}