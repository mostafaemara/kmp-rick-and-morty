import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.rickandmorty.graphql.CharactersQuery
import com.rickandmorty.graphql.type.FilterCharacter
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.CharacterStatus
import model.Gender
import org.koin.core.component.KoinComponent


data class CharactersUIState(
    val characters: List<CharactersQuery.Result?> = emptyList(),
    val status: Status = Status.LOADING,
    val nextStatus: Status = Status.IDLE,
    val selectedCharacterStatus: CharacterStatus? = null,
    val selectedGender: Gender? = null,
    val showBottomSheet: Boolean = false,
    val name: String = "",
    val isFilterButtonEnabled: Boolean = false

)


class CharactersViewModel(val graphQlClient: ApolloClient) : ViewModel(), KoinComponent {


    private val _uiState = MutableStateFlow(CharactersUIState())

    val uiState = _uiState.asStateFlow()

    var _nextPage: Int? = null;

    fun getCharacters() {

        viewModelScope.launch {
            try {
                val response = graphQlClient.query(CharactersQuery()).execute()
                _nextPage = response.data?.characters?.info?.next;
                val status = if (response.hasErrors()) Status.ERROR else Status.SUCCESS
                _uiState.update {
                    it.copy(
                        characters = response.data?.characters?.results
                            ?: emptyList<CharactersQuery.Result>(), status = status
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        status = Status.ERROR
                    )
                }
            }
        }
    }

    fun getNextCharacters() {
        if (_nextPage == null) return
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(nextStatus = Status.LOADING)
                }
                val response =
                    graphQlClient.query(CharactersQuery(page = Optional.present(_nextPage)))
                        .execute()

                _nextPage = response.data?.characters?.info?.next;
                val nextCharacters =
                    response.data?.characters?.results ?: emptyList<CharactersQuery.Result>()
                _uiState.update {
                    it.copy(
                        characters = it.characters + nextCharacters,
                        nextStatus = Status.SUCCESS
                    )
                }


            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        nextStatus = Status.ERROR
                    )
                }
            }
        }
    }

    fun showBottomSheetFilter() {
        _uiState.update {
            it.copy(
                showBottomSheet = true
            )
        }
    }

    fun hideBottomSheetFilter() {
        _uiState.update {
            it.copy(
                showBottomSheet = false
            )
        }
    }

    fun selectStatusFilter(status: CharacterStatus?) {
        _uiState.update {
            it.copy(
                selectedCharacterStatus = status,
                isFilterButtonEnabled = status != null
            )
        }
    }

    fun selectGenderFilter(gender: Gender?) {
        _uiState.update {
            it.copy(
                selectedGender = gender,
                isFilterButtonEnabled = gender != null
            )
        }
    }

    fun updateNameFilter(name: String) {
        _uiState.update {
            it.copy(
                name = name,
                isFilterButtonEnabled = name.isNotEmpty()
            )
        }
    }

    fun applyFilter() {

        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(status = Status.LOADING)
                }
                val charactersResponse = graphQlClient.query(
                    CharactersQuery(
                        filter = Optional.present(
                            FilterCharacter(
                                name = Optional.present(_uiState.asStateFlow().value.name),
                                status = Optional.present(_uiState.asStateFlow().value.selectedCharacterStatus?.name),
                                gender = Optional.present(_uiState.asStateFlow().value.selectedGender?.name),
                            )
                        )
                    )
                ).execute()
                if (charactersResponse.hasErrors()) throw Exception()

                _nextPage = charactersResponse.data?.characters?.info?.next;
                _uiState.update {
                    it.copy(
                        characters = charactersResponse.data?.characters?.results ?: emptyList(),
                        status = Status.SUCCESS
                    )
                }


            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        status = Status.ERROR
                    )
                }
            }
        }
    }

    fun restFilter() {
        _uiState.update {
            it.copy(
                isFilterButtonEnabled = false,
                name = "",
                selectedCharacterStatus = null,
                selectedGender = null
            )
        }
    }
}
