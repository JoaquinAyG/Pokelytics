package study.project.pokelytics.api.datasources

import kotlinx.coroutines.flow.Flow
import study.project.pokelytics.api.client.PokeApiClient
import study.project.pokelytics.api.model.Item
import study.project.pokelytics.api.model.PaginationRange

class BerryDataSource(
    val remote: PokeApiClient
) {

    fun getBerryFromPage(paginationRange: PaginationRange): Flow<List<Item>> =
        remote.getBerryList(paginationRange.from, paginationRange.count)

}