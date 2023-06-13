package study.project.pokelytics.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import study.project.pokelytics.api.model.PaginationRange
import study.project.pokelytics.api.model.Pokemon
import study.project.pokelytics.models.User
import study.project.pokelytics.usecases.GetFavPokemonUseCase
import study.project.pokelytics.usecases.GetPokemonUseCase

class PokemonListViewModel(
    val getPokemonUseCase: GetPokemonUseCase,
    val getListPokemonUseCase: GetFavPokemonUseCase
) : ViewModalBase() {

    private val mutablePokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>>
        get() = mutablePokemons

    fun getPokemonList(paginationRange: PaginationRange) {
        mutableState.postValue(ViewState.LOADING)
        viewModelScope.launch {
            getPokemonUseCase(
                paginationRange,
                { pokemonList ->
                    mutableState.postValue(ViewState.SUCCESS)
                    mutablePokemons.postValue(pokemonList)
                }, {
                    mutableState.postValue(ViewState.ERROR)
                }
            )
        }
    }

    fun getFavPokemon() {
        mutableState.postValue(ViewState.LOADING)
        viewModelScope.launch {
            getListPokemonUseCase(
                User.getInstance().favouriteList,
                { pokemonList ->
                    mutableState.postValue(ViewState.SUCCESS)
                    mutablePokemons.postValue(pokemonList)
                }, {
                    mutableState.postValue(ViewState.ERROR)
                }
            )
        }
    }

    fun getTeamPokemon() {
        mutableState.postValue(ViewState.LOADING)
        viewModelScope.launch {
            getListPokemonUseCase(
                User.getInstance().team,
                { pokemonList ->
                    mutableState.postValue(ViewState.SUCCESS)
                    mutablePokemons.postValue(pokemonList)
                }, {
                    mutableState.postValue(ViewState.ERROR)
                }
            )
        }
    }
}