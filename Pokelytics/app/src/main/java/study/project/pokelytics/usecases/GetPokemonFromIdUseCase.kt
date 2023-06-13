package study.project.pokelytics.usecases

import kotlinx.coroutines.flow.Flow
import study.project.pokelytics.api.datasources.PokemonDataSource
import study.project.pokelytics.api.model.Pokemon

open class GetPokemonFromIdUseCase(
    private val pokemonDataSource: PokemonDataSource
) : FlowUseCase<Pokemon, Int>() {

    override suspend fun execute(params: Int): Flow<Pokemon> {
        return pokemonDataSource.getPokemonFromId(params)
    }
}