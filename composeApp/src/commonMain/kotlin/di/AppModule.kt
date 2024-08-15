package di

import CharacterDetailsViewModel
import CharactersViewModel
import EpisodesViewModel
import RickAndMortyApi
import com.apollographql.apollo.ApolloClient

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import screens.locationDetails.LocationDetailsViewModel
import screens.locations.LocationsViewModel

val appModule: Module = module {
    single<RickAndMortyApi> { RickAndMortyApi() }
    single { ApolloClient.Builder().serverUrl("https://rickandmortyapi.com/graphql").build() }
    factory { CharactersViewModel(get()) }
    factory { LocationsViewModel(get()) }
    factory { EpisodesViewModel(get()) }
    factory { CharacterDetailsViewModel(get()) }
    factory { LocationDetailsViewModel(get()) }
}


fun injectDependancies() {
    startKoin {

        modules(appModule)
    }
}