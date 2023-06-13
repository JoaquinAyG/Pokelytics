package study.project.pokelytics.api.datasources

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import study.project.pokelytics.api.client.PokeApiClient
import study.project.pokelytics.api.model.Location
import study.project.pokelytics.api.model.NamedApiResource
import study.project.pokelytics.models.LocationList

class LocationDataSource(
    val remote: PokeApiClient
) {

    fun getLocationFromPage(locationList: LocationList): Flow<List<Location>> =
        remote.getLocationList(locationList)

    fun getLocation(location: NamedApiResource): Flow<Location> = flow {
        emit(remote.getLocation(location.id))
    }

}