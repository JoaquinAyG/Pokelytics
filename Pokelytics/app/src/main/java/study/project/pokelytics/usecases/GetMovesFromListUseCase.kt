package study.project.pokelytics.usecases

import kotlinx.coroutines.flow.Flow
import study.project.pokelytics.api.datasources.MoveDataSource
import study.project.pokelytics.api.model.Move

class GetMovesFromListUseCase(
    private val moveDataSource: MoveDataSource
) : FlowUseCase<List<Move>, String>() {

    override suspend fun execute(params: String): Flow<List<Move>> {
        return moveDataSource.getMoveFromList(params)
    }
}