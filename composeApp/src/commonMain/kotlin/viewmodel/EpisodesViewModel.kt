package viewmodel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import RickAndMortyApi

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Episode

data class EpisodesUIState (val episodes:List<Episode> = emptyList(), val status :Status=Status.LOADING){

}

class EpisodesViewModel(val api:RickAndMortyApi) : ViewModel() {
    private val _uiState= MutableStateFlow<EpisodesUIState>(EpisodesUIState())
    val uiState= _uiState.asStateFlow();
    
    suspend fun getEpisodes() {
        viewModelScope.launch {
            try {
                val response = api.getEpisodes();
                _uiState.update {
                    it.copy(episodes = response.results, status = Status.SUCCESS);
                }
            }
            catch (e:Exception){
                _uiState.update {
                    it.copy( status = Status.ERROR);
                    }
            }

        }
    }
}