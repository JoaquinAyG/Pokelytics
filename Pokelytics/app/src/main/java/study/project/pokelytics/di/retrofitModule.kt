package study.project.pokelytics.di

import org.koin.dsl.module
import study.project.pokelytics.api.client.PokeApiClient
import study.project.pokelytics.api.datasources.BerryDataSource
import study.project.pokelytics.api.datasources.LocationDataSource
import study.project.pokelytics.api.datasources.MoveDataSource
import study.project.pokelytics.api.datasources.PokemonDataSource
import study.project.pokelytics.api.datasources.RegionDataSource

val retrofitModule = module {
    factory { PokeApiClient() }
    factory { PokemonDataSource(get()) }
    factory { BerryDataSource(get()) }
    factory { MoveDataSource(get()) }
    factory { LocationDataSource(get()) }
    factory { RegionDataSource(get()) }
}