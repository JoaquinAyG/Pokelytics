package study.project.pokelytics.usecases

import kotlinx.coroutines.flow.Flow
import study.project.pokelytics.api.datasources.RegionDataSource
import study.project.pokelytics.api.model.Region
import study.project.pokelytics.api.model.PaginationRange

class GetRegionUseCase(
    private val regionDataSource: RegionDataSource
) : FlowUseCase<List<Region>, PaginationRange>() {

    override suspend fun execute(params: PaginationRange): Flow<List<Region>> {
        return regionDataSource.getRegionFromPage(params)
    }
}