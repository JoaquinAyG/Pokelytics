package study.project.pokelytics.api.datasources

import kotlinx.coroutines.flow.Flow
import study.project.pokelytics.api.client.PokeApiClient
import study.project.pokelytics.api.model.Region
import study.project.pokelytics.api.model.PaginationRange

class RegionDataSource(
    val remote: PokeApiClient
) {

    fun getRegionFromPage(paginationRange: PaginationRange): Flow<List<Region>> =
        remote.getRegionList(paginationRange.from, paginationRange.count)

}