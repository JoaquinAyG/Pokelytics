package study.project.pokelytics.api.datasources

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import study.project.pokelytics.api.client.PokeApiClient
import study.project.pokelytics.api.model.PaginationRange
import study.project.pokelytics.api.model.Pokemon
import study.project.pokelytics.api.model.PokemonExtraInfo

class PokemonDataSource(
    val remote: PokeApiClient
) {

    fun getPokemonFromPage(paginationRange: PaginationRange): Flow<List<Pokemon>> =
        remote.getPokemonList(paginationRange.from, paginationRange.count)

    fun getPokemonExtraInfo(params: Pokemon): Flow<Pokemon> = flow {
        val specie = remote.getPokemonSpecies(params.id)
        val newPokemon = params.copy(extraInfo = PokemonExtraInfo(species = specie))
        emit(newPokemon)
    }

    fun getPokemonFromId(ids: String): Flow<List<Pokemon>> = flow {
        val list = ids.split(",").map { it.toIntOrNull() ?: -1 }.map {
            remote.getPokemon(it)
        }
        emit(list)
    }

    fun getPokemonFromId(id: Int): Flow<Pokemon> = flow {
        emit(remote.getPokemon(id))
    }

}