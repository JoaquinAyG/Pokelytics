package study.project.pokelytics.usecases

import kotlinx.coroutines.flow.Flow
import study.project.pokelytics.api.datasources.PokemonDataSource
import study.project.pokelytics.api.model.Pokemon

open class GetFavPokemonUseCase(
    private val pokemonDataSource: PokemonDataSource
) : FlowUseCase<List<Pokemon>, String>() {

    override suspend fun execute(params: String): Flow<List<Pokemon>> {
        return pokemonDataSource.getPokemonFromId(params)
    }
}