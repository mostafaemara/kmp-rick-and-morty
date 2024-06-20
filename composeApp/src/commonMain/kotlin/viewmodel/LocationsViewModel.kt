package viewmodel

import RickAndMortyApi
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.Location

data class LocationsUIState(val locations:List<Location> )

class LocationsViewModel (val api: RickAndMortyApi):ViewModel() {
    private val _uiState= MutableStateFlow<LocationsUIState>(LocationsUIState(emptyList()))
    val uiState=_uiState.asStateFlow()
    
    suspend fun getLocations(){
        val response= api.getLocations()
        _uiState.update { it.copy(locations = response.results) }
    }
    
}