package study.project.pokelytics.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import study.project.pokelytics.api.model.Region
import study.project.pokelytics.api.model.PaginationRange
import study.project.pokelytics.usecases.GetRegionUseCase

class RegionViewModel(
    val getRegionUseCase: GetRegionUseCase
) : ViewModalBase() {

    private val mutableRegionList = MutableLiveData<List<Region>>()
    val regionList: LiveData<List<Region>>
        get() = mutableRegionList

    fun getRegionList(paginationRange: PaginationRange) {
        mutableState.postValue(ViewState.LOADING)
        viewModelScope.launch {
            getRegionUseCase(
                paginationRange,
                { regionList ->
                    mutableState.postValue(ViewState.SUCCESS)
                    mutableRegionList.postValue(regionList)
                }, {
                    mutableState.postValue(ViewState.ERROR)
                }
            )
        }
    }
}