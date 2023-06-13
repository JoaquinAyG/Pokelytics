package study.project.pokelytics.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import study.project.pokelytics.api.model.Move
import study.project.pokelytics.api.model.PaginationRange
import study.project.pokelytics.usecases.GetMoveUseCase
import study.project.pokelytics.usecases.GetMovesFromListUseCase

class MoveViewModel(
    val getMoveUseCase: GetMoveUseCase,
    val getMovesFromListUseCase: GetMovesFromListUseCase
) : ViewModalBase() {

    private val mutableMoveList = MutableLiveData<List<Move>>()
    val moveList: LiveData<List<Move>>
        get() = mutableMoveList

    fun getMoveList(paginationRange: PaginationRange) {
        mutableState.postValue(ViewState.LOADING)
        viewModelScope.launch {
            getMoveUseCase(
                paginationRange,
                { moveList ->
                    mutableState.postValue(ViewState.SUCCESS)
                    mutableMoveList.postValue(moveList)
                }, {
                    mutableState.postValue(ViewState.ERROR)
                }
            )
        }
    }

    fun getMoveFromList(list: String) {
        mutableState.postValue(ViewState.LOADING)
        viewModelScope.launch {
            getMovesFromListUseCase(
                list,
                { moveList ->
                    mutableState.postValue(ViewState.SUCCESS)
                    mutableMoveList.postValue(moveList)
                }, {
                    mutableState.postValue(ViewState.ERROR)
                }
            )
        }
    }
}