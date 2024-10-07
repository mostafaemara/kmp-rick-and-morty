import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.rickandmorty.graphql.CharactersQuery
import com.rickandmorty.graphql.type.FilterCharacter
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


import org.koin.core.component.KoinComponent


enum class CharacterStatus {

    ALIVE,


    DEAD,


    UNKNOWN,
    UNSELECTED
}

enum class Gender {

    FEMALE,


    MALE,


    GENDERLESS,


    UNKOWN, UNSELECTED
}

data class CharactersUIState(
    val characters: List<CharactersQuery.Result?> = emptyList(),
    val status: Status = Status.LOADING,
    val nextStatus: Status = Status.IDLE,
    val selectedCharacterStatus: CharacterStatus? = null,
    val selectedGender: Gender? = null,

    val name: String = "",
    val isSearchEnabled: Boolean = false,


    )


class CharactersViewModel(private val graphQlClient: ApolloClient) : ViewModel(), KoinComponent {

    private var searchJob: Job? = null
    private val _uiState = MutableStateFlow(CharactersUIState())

    val uiState = _uiState.asStateFlow()

    private var _nextPage: Int? = null;

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


    fun selectStatusFilter(status: CharacterStatus?) {
        _uiState.update {
            it.copy(
                selectedCharacterStatus = if (status == it.selectedCharacterStatus) CharacterStatus.UNSELECTED else status,

                )
        }
        excuteFilter(

        );
    }

    fun selectGenderFilter(gender: Gender?) {


        _uiState.update {


            it.copy(
                selectedGender = if (it.selectedGender == gender) Gender.UNSELECTED else gender,

                )
        }
        excuteFilter(

        );
    }


    fun updateSearchName(name: String) {


        _uiState.update {
            it.copy(
                name = name,

                )
        }

        searchJob?.cancel();
        searchJob = viewModelScope.launch {
            delay(500)
            excuteFilter()
        }

    }

    private fun excuteFilter() {
        val status = _uiState.asStateFlow().value.selectedCharacterStatus;
        val gender = _uiState.asStateFlow().value.selectedGender;
        val name = _uiState.asStateFlow().value.name;
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(status = Status.LOADING)
                }
                val charactersResponse = graphQlClient.query(
                    CharactersQuery(
                        filter = Optional.present(
                            FilterCharacter(
                                name = if (name.isEmpty()) Optional.absent() else Optional.present(name),
                                status = if (status == null || status == CharacterStatus.UNSELECTED) Optional.absent() else Optional.present(
                                    status.name
                                ),
                                gender = if (gender == null || gender == Gender.UNSELECTED) Optional.absent() else Optional.present(
                                    gender.name
                                ),
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


    fun clearSearch() {
        _uiState.update {
            it.copy(

                name = "",

                )
        }

        excuteFilter(

        );

    }
}
