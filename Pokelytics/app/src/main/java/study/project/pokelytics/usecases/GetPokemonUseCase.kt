package study.project.pokelytics.usecases

import kotlinx.coroutines.flow.Flow
import study.project.pokelytics.api.model.PaginationRange
import study.project.pokelytics.api.datasources.PokemonDataSource
import study.project.pokelytics.api.model.Pokemon

open class GetPokemonUseCase(
    private val pokemonDataSource: PokemonDataSource
) : FlowUseCase<List<Pokemon>, PaginationRange>() {

    override suspend fun execute(params: PaginationRange): Flow<List<Pokemon>> {
        return pokemonDataSource.getPokemonFromPage(params)
    }
}