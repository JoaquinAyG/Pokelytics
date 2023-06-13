package study.project.pokelytics.models

import study.project.pokelytics.api.model.Pokemon
import study.project.pokelytics.viewmodels.MoreInfoViewModel

interface PokemonInterface {
    fun onFavoriteClick(pokemon: Pokemon)
    fun onTeamClick(pokemon: Pokemon)
    fun onPokemonClick(pokemon: Pokemon)
    fun createMoreInfoViewModel(): MoreInfoViewModel
}