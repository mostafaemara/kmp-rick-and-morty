package screens.locationDetails

import Status
import com.apollographql.apollo.ApolloClient
import com.rickandmorty.graphql.LocationQuery
import com.rickandmorty.graphql.LocationQuery.Location
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LocationDetailsUIState(val locationDetails: Location?, val status: Status)
class LocationDetailsViewModel(val graphQlClient: ApolloClient) : ViewModel() {

    private val _uiState =
        MutableStateFlow<LocationDetailsUIState>(LocationDetailsUIState(null, Status.LOADING));

    val uiState = _uiState.asStateFlow();

    suspend fun getLocationDetails(id: String) {

        try {
            _uiState.update {
                it.copy(
                    status = Status.LOADING
                )
            }
            val response = graphQlClient.query(LocationQuery(locationId = id)).execute()
            if (response.hasErrors()) throw Exception(response.errors.toString())
            _uiState.update {
                it.copy(
                    locationDetails = response.data?.location,
                    status = Status.SUCCESS
                )
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(status = Status.ERROR)
            }

        }

    }

}