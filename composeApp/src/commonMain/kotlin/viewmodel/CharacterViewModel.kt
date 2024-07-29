import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Character
import model.CharacterStatus
import model.Gender
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import viewmodel.Status

data class CharactersUIState(
    val characters: List<Character> = emptyList(),
    val status: Status = Status.LOADING,
    val nextStatus: Status = Status.IDLE,
    val selectedCharacterStatus: CharacterStatus? = null,
    val selectedGender: Gender? = null,
    val showBottomSheet: Boolean = false,
    val name: String = "",
    val isFilterButtonEnabled: Boolean = false

)


class CharactersViewModel(val api: RickAndMortyApi) : ViewModel(), KoinComponent {


    private val _uiState = MutableStateFlow(CharactersUIState())

    val uiState = _uiState.asStateFlow()

    var _nextPage: String? = null;
    fun getCharacters() {

        viewModelScope.launch {
            try {
                val charactersResponse =
                    api.getCharacter();

                _nextPage = charactersResponse.info.next;
                _uiState.update {
                    it.copy(characters = charactersResponse.results, status = Status.SUCCESS)
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
                val charactersResponse =
                    api.getCharacter(nextPage = _nextPage);

                _nextPage = charactersResponse.info.next;
                _uiState.update {
                    it.copy(characters = it.characters + charactersResponse.results, nextStatus = Status.SUCCESS)
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
