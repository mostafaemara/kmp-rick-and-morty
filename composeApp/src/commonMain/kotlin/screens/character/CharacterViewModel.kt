package screens.character

import Status
import com.apollographql.apollo.ApolloClient
import com.rickandmorty.graphql.CharacterQuery
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class CharacterState(
    val character: CharacterQuery.Character? = null,
    val characterStatus: Status = Status.IDLE
)

class CharacterViewModel(private val graphQlClient: ApolloClient) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterState())
    val uiState = _uiState.asStateFlow()


    fun getCharacterDetails(id: String) {
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        characterStatus = Status.LOADING
                    )
                }
                val response = graphQlClient.query(CharacterQuery(id)).execute()
                if (response.hasErrors()) throw Exception()
                _uiState.update {
                    it.copy(
                        character = response.data?.character,
                        characterStatus = Status.SUCCESS
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(

                        characterStatus = Status.ERROR
                    )
                }
            }

        }

    }

}


