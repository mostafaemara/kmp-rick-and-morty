import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Character

data class CharactersUIState (val characters:List<Character>)



class CharactersViewModel(val api: RickAndMortyApi):ViewModel(){
    private val _uiState= MutableStateFlow(CharactersUIState(emptyList()))
    val uiState=_uiState.asStateFlow()
    
 fun getCharacters() {
     viewModelScope.launch {
         val charactersResponse=api.getCharacter();
         _uiState.update {
             it.copy(characters=charactersResponse.results)
         }
     }
 }
}