package study.project.pokelytics.usecases

import kotlinx.coroutines.flow.Flow
import study.project.pokelytics.api.datasources.BerryDataSource
import study.project.pokelytics.api.model.Item
import study.project.pokelytics.api.model.PaginationRange

class GetBerryUseCase(
    private val berryDataSource: BerryDataSource
) : FlowUseCase<List<Item>, PaginationRange>() {

    override suspend fun execute(params: PaginationRange): Flow<List<Item>> {
        return berryDataSource.getBerryFromPage(params)
    }
}