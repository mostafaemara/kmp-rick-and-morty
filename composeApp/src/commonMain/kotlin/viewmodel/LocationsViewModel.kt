package viewmodel

import RickAndMortyApi
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Location

data class LocationsUIState(val locations:List<Location> = emptyList(),val status: Status=Status.LOADING )

class LocationsViewModel (val api: RickAndMortyApi):ViewModel() {
    private val _uiState= MutableStateFlow<LocationsUIState>(LocationsUIState())
    val uiState=_uiState.asStateFlow()
    
     fun getLocations(){
        viewModelScope.launch {
            try {
                val response= api.getLocations()
                _uiState.update { it.copy(locations = response.results, status = Status.SUCCESS) }
            }
            catch (e:Exception){
                _uiState.update { it.copy( status = Status.ERROR) }
            }
        }

    }
    
}