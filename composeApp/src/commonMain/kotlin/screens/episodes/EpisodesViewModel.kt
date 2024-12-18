import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.rickandmorty.graphql.EpisodesQuery
import com.rickandmorty.graphql.type.FilterEpisode
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.rickandmorty.graphql.EpisodesQuery.Result as Episode


data class EpisodesUIState(
    val episodes: List<Episode?>? = emptyList(),
    val status: Status = Status.LOADING,
    val nextStatus: Status = Status.IDLE,
    val query: String = "",
    val isSearchActive: Boolean = false
) {

}

class EpisodesViewModel(val graphql: ApolloClient) : ViewModel() {
    private val _uiState = MutableStateFlow<EpisodesUIState>(EpisodesUIState())
    val uiState = _uiState.asStateFlow();
    var _nextPage: Int? = null
    private var searchJob: Job? = null
    suspend fun getEpisodes() {
        viewModelScope.launch {
            try {
                val response = graphql.query(EpisodesQuery()).execute()
                if (response.hasErrors()) throw Exception(response.errors.toString())
                _nextPage = response.data?.episodes?.info?.next
                _uiState.update {
                    it.copy(episodes = response?.data?.episodes?.results, status = Status.SUCCESS);
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(status = Status.ERROR);
                }
            }

        }
    }


    suspend fun getNextEpisodes() {
        if (_nextPage == null) return;
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        nextStatus = Status.LOADING
                    )
                }
                val response = graphql.query(
                    EpisodesQuery(
                        page = Optional.present(_nextPage)
                    )
                ).execute()
                if (response.hasErrors()) throw Exception(response.errors.toString())
                _nextPage = response.data?.episodes?.info?.next
                _uiState.update {
                    it.copy(
                        episodes = it.episodes?.plus((response?.data?.episodes?.results ?: emptyList<Episode>())),
                        nextStatus = Status.SUCCESS
                    );
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(nextStatus = Status.ERROR);
                }
            }

        }
    }

    fun updateSearchQuery(query: String) {

        _uiState.update {

            it.copy(
                query = query, isSearchActive = query.isNotEmpty()

            )
        }

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(500)
            excuteFilters()
        }
    }

    private fun excuteFilters() {

        val query = _uiState.asStateFlow().value.query;


        _uiState.update {
            it.copy(status = Status.LOADING);
        }
        viewModelScope.launch {
            try {
                val response = graphql.query(
                    EpisodesQuery(
                        filter = Optional.present(
                            FilterEpisode(
                                name = if (query.isEmpty()) Optional.absent() else Optional.present(query)
                            )
                        )

                    )
                ).execute()
                if (response.hasErrors()) throw Exception(response.errors.toString())
                _uiState.update {
                    it.copy(episodes = response.data?.episodes?.results, status = Status.SUCCESS);
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(status = Status.ERROR);
                }
            }

        }
    }

    fun clearSearch() {
        _uiState.update {
            it.copy(
                isSearchActive = false,
                query = ""
            )
        }
        excuteFilters()
    }
}