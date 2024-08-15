package screens.locations

import Status
import com.apollographql.apollo.ApolloClient
import com.rickandmorty.graphql.LocationsQuery
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LocationsUIState(
    val locations: List<LocationsQuery.Result> = emptyList(),
    val status: Status = Status.LOADING
)

class LocationsViewModel(val graphqlApi: ApolloClient) : ViewModel() {
    private val _uiState = MutableStateFlow<LocationsUIState>(LocationsUIState())
    val uiState = _uiState.asStateFlow()

    fun getLocations() {
        viewModelScope.launch {
            try {
                val response = graphqlApi.query(LocationsQuery()).execute()
                if (response.hasErrors()) throw Exception(response.errors.toString())
                _uiState.update {
                    it.copy(
                        locations = response.data?.locations?.results as List<LocationsQuery.Result>,
                        status = Status.SUCCESS
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(status = Status.ERROR) }
            }
        }

    }

}