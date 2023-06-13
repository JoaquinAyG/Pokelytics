package study.project.pokelytics.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import study.project.pokelytics.api.model.Pokemon
import study.project.pokelytics.usecases.GetPokemonFromIdUseCase

class InfoViewModel(
    val getPokemonFromIdUseCase: GetPokemonFromIdUseCase
): ViewModalBase() {

    private val mutablePokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> = mutablePokemon

    fun setPokemon(id: Int) {
        mutableState.postValue(ViewState.LOADING)
        viewModelScope.launch {
            getPokemonFromIdUseCase(
                id,
                {
                    mutablePokemon.postValue(it)
                    mutableState.postValue(ViewState.SUCCESS)
                }, {
                    mutableState.postValue(ViewState.ERROR)
                }
            )
        }
    }

}
