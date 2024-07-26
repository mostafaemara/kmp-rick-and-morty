package di

import CharactersViewModel
import RickAndMortyApi
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

val commonModule : Module = module {
    single { RickAndMortyApi() }
    single { CharactersViewModel(
        RickAndMortyApi()
    ) }
    
    
}


fun injectDependancies(){
    startKoin {
        modules(commonModule)
    }
}