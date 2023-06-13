package study.project.pokelytics.viewmodels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import study.project.pokelytics.api.model.Pokemon
import study.project.pokelytics.event.LiveEvent
import study.project.pokelytics.event.MutableLiveEvent
import study.project.pokelytics.event.postEvent
import study.project.pokelytics.models.User
import study.project.pokelytics.usecases.GetPokemonMoreInfoUseCase

class MoreInfoViewModel(
    val getPokemonMoreInfoUseCase: GetPokemonMoreInfoUseCase
): ViewModalBase() {

    private val mutablePokemon = MutableLiveEvent<Pokemon>()
    val pokemon: LiveEvent<Pokemon>
        get() = mutablePokemon

    fun getPokemonExtraInfo(item: Pokemon) {
        mutableState.postValue(ViewState.LOADING)
        viewModelScope.launch {
            getPokemonMoreInfoUseCase(
                item,
                {
                    mutableState.postValue(ViewState.SUCCESS)
                    mutablePokemon.postEvent(it)
                }, {
                    mutableState.postValue(ViewState.ERROR)
                }
            )
        }
    }

    fun getIsFavorite(item: Pokemon): Boolean {
        return User.getInstance().isFavorite(item)
    }

    fun getIsTeam(item: Pokemon): Boolean {
        return User.getInstance().isTeam(item)
    }
}
