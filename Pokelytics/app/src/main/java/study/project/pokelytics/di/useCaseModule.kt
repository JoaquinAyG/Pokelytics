package study.project.pokelytics.di

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module
import study.project.pokelytics.usecases.GetBerryUseCase
import study.project.pokelytics.usecases.GetFavPokemonUseCase
import study.project.pokelytics.usecases.GetLocationUseCase
import study.project.pokelytics.usecases.GetMoveUseCase
import study.project.pokelytics.usecases.GetMovesFromListUseCase
import study.project.pokelytics.usecases.GetPokemonFromIdUseCase
import study.project.pokelytics.usecases.GetPokemonMoreInfoUseCase
import study.project.pokelytics.usecases.GetPokemonUseCase
import study.project.pokelytics.usecases.GetRegionUseCase
import study.project.pokelytics.usecases.ResetPasswordUseCase
import study.project.pokelytics.usecases.SaveUserPreferencesUseCase

@FlowPreview
@ExperimentalCoroutinesApi
val useCaseModule = module {
    factory { GetPokemonUseCase(get()) }
    factory { GetPokemonMoreInfoUseCase(get()) }
    factory { SaveUserPreferencesUseCase(get()) }
    factory { ResetPasswordUseCase(get()) }
    factory { GetBerryUseCase(get()) }
    factory { GetMoveUseCase(get()) }
    factory { GetLocationUseCase(get()) }
    factory { GetRegionUseCase(get()) }
    factory { GetFavPokemonUseCase(get()) }
    factory { GetPokemonFromIdUseCase(get()) }
    factory { GetMovesFromListUseCase(get()) }
}