package study.project.pokelytics.di

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module
import study.project.pokelytics.api.datasources.PokemonDataSource
import study.project.pokelytics.firebase.FirebaseHelper

@FlowPreview
@ExperimentalCoroutinesApi
val repositoryModule = module {
    factory { FirebaseHelper(get(), get()) }
}