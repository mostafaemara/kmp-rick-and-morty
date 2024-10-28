package screens.locations

import Status
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.rickandmorty.graphql.LocationsQuery
import com.rickandmorty.graphql.type.FilterLocation
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class LocationType(val value: String) {

    UNSELECTED(""),
    PLANET("Planet"),
    CLUSTER("Cluster"),
    SPACE_STATION("Space station"),
    MICROVERSE("Microverse"),
    RESORT("Resort"),
    TV("Tv"),
    FANTASY_TOWN("Fantasy town"),
    DREAM("Dream"),
    CUSTOMS("Customs"),
    GAME("Game");


}

data class LocationsUIState(
    val locations: List<LocationsQuery.Result> = emptyList(),
    val status: Status = Status.LOADING,
    val nextStatus: Status = Status.IDLE,
    val isSarchActive: Boolean = false,
    val query: String = "",
    val selectedType: LocationType = LocationType.UNSELECTED

)

class LocationsViewModel(private val graphqlApi: ApolloClient) : ViewModel() {
    private val _uiState = MutableStateFlow<LocationsUIState>(LocationsUIState())
    val uiState = _uiState.asStateFlow()
    private var _nextPage: Int? = 0
    private var searchJob: Job? = null
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

    fun updateSearchQuery(query: String) {
        if (query.isEmpty()) {


            _uiState.update {
                it.copy(
                    isSarchActive = false,
                    query = ""
                )
            }
            return

        }
        _uiState.update {
            it.copy(
                isSarchActive = true,
                query = query
            )
        }
        searchJob?.cancel();
        searchJob = viewModelScope.launch {
            delay(500)
            excuteFilters()
        }

    }

    fun selectLocationType(type: LocationType) {
        var selectedTpe = _uiState.asStateFlow().value.selectedType;
        selectedTpe = if (selectedTpe == type) {
            LocationType.UNSELECTED;
        } else {
            type;
        }
        _uiState.update {
            it.copy(
                selectedType = selectedTpe
            )
        }
        excuteFilters()


    }

    fun restSearch() {
        _uiState.update {
            it.copy(
                query = "",
                isSarchActive = false
            )
        }
        excuteFilters()
    }

    private fun excuteFilters() {
        viewModelScope.launch {
            try {
                val query = _uiState.asStateFlow().value.query;
                val selectedTpe = _uiState.asStateFlow().value.selectedType;

                _uiState.update {
                    it.copy(

                        status = Status.LOADING
                    )
                }
                var response = graphqlApi.query(
                    LocationsQuery(
                        filter = Optional.present(
                            FilterLocation(
                                name = if (query.isEmpty()) Optional.absent() else Optional.present(query),

                                type = if (selectedTpe == LocationType.UNSELECTED) Optional.absent() else Optional.present(
                                    selectedTpe.value
                                )
                            )
                        )

                    )
                ).execute()


                _uiState.update {
                    it.copy(

                        status = Status.SUCCESS,
                        locations = response.data?.locations?.results as List<LocationsQuery.Result>,
                    )

                }

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(

                        status = Status.ERROR,

                        )

                }
            }

        }
    }

}