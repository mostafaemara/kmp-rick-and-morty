package di


import CharactersViewModel
import EpisodesViewModel

import com.apollographql.apollo.ApolloClient

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import screens.character.CharacterViewModel
import screens.episode.EpisodeViewModel
import screens.locationDetails.LocationDetailsViewModel
import screens.locations.LocationsViewModel

val appModule: Module = module {

    single { ApolloClient.Builder().serverUrl("https://rickandmortyapi.com/graphql").build() }
    factory { CharactersViewModel(get()) }
    factory { LocationsViewModel(get()) }
    factory { EpisodesViewModel(get()) }
    factory { CharacterViewModel(get()) }
    factory { LocationDetailsViewModel(get()) }
    factory { EpisodeViewModel(get()) }
}


fun injectDependancies() {
    startKoin {

        modules(appModule)
    }
}