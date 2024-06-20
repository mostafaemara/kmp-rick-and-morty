package viewmodel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import RickAndMortyApi

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.Episode

data class EpisodesUIState (val episodes:List<Episode>)

class EpisodesViewModel(val api:RickAndMortyApi) : ViewModel() {
    private val _uiState= MutableStateFlow<EpisodesUIState>(EpisodesUIState(emptyList()))
    val uiState= _uiState.asStateFlow();
    
    suspend fun getEpisodes() {
        val response = api.getEpisodes();
        _uiState.update {
            it.copy(episodes = response.results);
        }
    }
}