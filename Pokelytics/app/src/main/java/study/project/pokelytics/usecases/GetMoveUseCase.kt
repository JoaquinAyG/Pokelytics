package study.project.pokelytics.usecases

import kotlinx.coroutines.flow.Flow
import study.project.pokelytics.api.datasources.MoveDataSource
import study.project.pokelytics.api.model.Move
import study.project.pokelytics.api.model.PaginationRange

class GetMoveUseCase(
    private val moveDataSource: MoveDataSource
) : FlowUseCase<List<Move>, PaginationRange>() {

    override suspend fun execute(params: PaginationRange): Flow<List<Move>> {
        return moveDataSource.getMoveFromPage(params)
    }
}