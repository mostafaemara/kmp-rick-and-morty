package di

import CharactersViewModel
import RickAndMortyApi

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import viewmodel.EpisodesViewModel
import viewmodel.LocationsViewModel

val appModule: Module = module {
    single<RickAndMortyApi> { RickAndMortyApi() }
    factory { CharactersViewModel(get()) }
    factory { LocationsViewModel(get()) }
    factory { EpisodesViewModel(get()) }

}


fun injectDependancies() {
    startKoin {
   
        modules(appModule)
    }
}