package study.project.pokelytics.di

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import study.project.pokelytics.viewmodels.LoginViewModel
import study.project.pokelytics.viewmodels.MoreInfoViewModel
import study.project.pokelytics.viewmodels.NavigationViewModel
import study.project.pokelytics.viewmodels.PokemonListViewModel
import study.project.pokelytics.viewmodels.SignUpViewModel
import study.project.pokelytics.viewmodels.*

@ExperimentalCoroutinesApi
@FlowPreview
val viewModelModule = module {
    viewModel { PokemonListViewModel(get(), get()) }
    viewModel { MoreInfoViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { ResetPasswordViewModel(get())}
    viewModel { NavigationViewModel(get())}
    viewModel { BerryViewModel(get())}
    viewModel { MoveViewModel(get(), get())}
    viewModel { RegionViewModel(get())}
    viewModel { SplashViewModel(get())}
    viewModel { FavViewModel(get())}
    viewModel { InfoViewModel(get())}
}