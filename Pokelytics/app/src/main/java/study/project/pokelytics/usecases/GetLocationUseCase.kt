package study.project.pokelytics.usecases

import kotlinx.coroutines.flow.Flow
import study.project.pokelytics.api.datasources.LocationDataSource
import study.project.pokelytics.api.model.Location
import study.project.pokelytics.api.model.NamedApiResource

class GetLocationUseCase(
    private val locationDataSource: LocationDataSource
) : FlowUseCase<Location, NamedApiResource>() {

    override suspend fun execute(params: NamedApiResource): Flow<Location> {
        return locationDataSource.getLocation(params)
    }
}