package screens.episode

import Status
import com.apollographql.apollo.ApolloClient
import com.rickandmorty.graphql.EpisodeQuery
import com.rickandmorty.graphql.EpisodeQuery.Episode
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class EpisodeState(val status: Status, val episode: Episode?)
class EpisodeViewModel(val graphQlClient: ApolloClient) : ViewModel() {
    private val _uiState = MutableStateFlow(
        EpisodeState(
            status = Status.IDLE,
            episode = null
        )
    )

    val uiState = _uiState.asStateFlow()


    suspend fun getEpsiode(id: String) {
        _uiState.update {
            it.copy(
                status = Status.LOADING
            )
        }

        val response = graphQlClient.query(EpisodeQuery(id)).execute()

        if (response.hasErrors()) {
            _uiState.update {
                it.copy(
                    status = Status.ERROR
                )
            }
            return
        }
        _uiState.update {
            it.copy(
                status = Status.SUCCESS,
                episode = response.data?.episode
            )
        }

    }
}