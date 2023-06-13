package study.project.pokelytics.usecases

import kotlinx.coroutines.flow.Flow
import study.project.pokelytics.api.datasources.PokemonDataSource
import study.project.pokelytics.api.model.Pokemon

class GetPokemonMoreInfoUseCase(
    private val pokemonDataSource: PokemonDataSource
) : FlowUseCase<Pokemon, Pokemon>() {

    override suspend fun execute(params: Pokemon): Flow<Pokemon> {
        return pokemonDataSource.getPokemonExtraInfo(params)
    }

}
