package screens.locations

import Status
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.rickandmorty.graphql.LocationsQuery
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LocationsUIState(
    val locations: List<LocationsQuery.Result> = emptyList(),
    val status: Status = Status.LOADING,
    val nextStatus: Status = Status.IDLE
)

class LocationsViewModel(val graphqlApi: ApolloClient) : ViewModel() {
    private val _uiState = MutableStateFlow<LocationsUIState>(LocationsUIState())
    val uiState = _uiState.asStateFlow()
    var _nextPage: Int? = 0

    fun getLocations() {
        viewModelScope.launch {
            try {
                val response = graphqlApi.query(LocationsQuery()).execute()
                _nextPage = response.data?.locations?.info?.next
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

    fun getNextLocations() {
        if (_nextPage == null) return;
        _uiState.update {
            it.copy(
                nextStatus = Status.IDLE
            )
        }
        viewModelScope.launch {
            try {
                val response = graphqlApi.query(LocationsQuery(page = Optional.present(_nextPage))).execute()
                _nextPage = response.data?.locations?.info?.next
                if (response.hasErrors()) throw Exception(response.errors.toString())
                _uiState.update {
                    it.copy(
                        locations = it.locations + response.data?.locations?.results as List<LocationsQuery.Result>,
                        nextStatus = Status.SUCCESS
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(nextStatus = Status.ERROR) }
            }
        }

    }

}