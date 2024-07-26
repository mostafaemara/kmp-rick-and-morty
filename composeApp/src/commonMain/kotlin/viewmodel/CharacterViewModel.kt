import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Character
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import viewmodel.Status

data class CharactersUIState (val characters:List<Character> = emptyList(), val status: Status= Status.LOADING ,val nextStatus: Status=Status.IDLE)



class CharactersViewModel(val api: RickAndMortyApi ):ViewModel(),KoinComponent{

    private val _uiState= MutableStateFlow(CharactersUIState())
    val uiState=_uiState.asStateFlow()

    var _nextPage:String? =null;
    fun getCharacters() {

   viewModelScope.launch {
            try {
                val charactersResponse=
api.getCharacter();

_nextPage=charactersResponse.info.next;
                _uiState.update {
                    it.copy(characters=charactersResponse.results, status = Status.SUCCESS)
                }


            } catch (e:Exception){
                _uiState.update { it.copy(
                    status = Status.ERROR
                ) }
            }
        }}

    fun getNextCharacters() {
        if(_nextPage==null) return
        viewModelScope.launch {
            try {  _uiState.update {
                it.copy( nextStatus = Status.LOADING)
                }
                val charactersResponse=
api.getCharacter(nextPage = _nextPage);

_nextPage=charactersResponse.info.next;
                _uiState.update {
                    it.copy(characters= it.characters + charactersResponse.results, nextStatus = Status.SUCCESS)
                }


            } catch (e:Exception){
                _uiState.update { it.copy(
                    nextStatus = Status.ERROR
                ) }
            }
        }
    }

 }
