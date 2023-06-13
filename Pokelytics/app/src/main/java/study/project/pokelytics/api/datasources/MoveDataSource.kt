package study.project.pokelytics.api.datasources

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import study.project.pokelytics.api.client.PokeApiClient
import study.project.pokelytics.api.model.Move
import study.project.pokelytics.api.model.PaginationRange

class MoveDataSource(
    val remote: PokeApiClient
) {

    fun getMoveFromPage(paginationRange: PaginationRange): Flow<List<Move>> =
        remote.getMoveList(paginationRange.from, paginationRange.count)

    fun getMoveFromList(ids: String): Flow<List<Move>> = flow {
        val idList = ids.split(",")
        val moveList = mutableListOf<Move>()
        idList.forEach { id ->
            moveList.add(remote.getMove(id.toIntOrNull() ?: 0))
        }
        emit(moveList)
    }

}