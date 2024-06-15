import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.Character

@Composable
fun CharactersPage() {

    val charactersViewModel = getViewModel(Unit, viewModelFactory {
        CharactersViewModel(
            RickAndMortyApi()
        )
    })
    val uiState by charactersViewModel.uiState.collectAsState()

    LaunchedEffect( charactersViewModel){
        charactersViewModel.getCharacters()
    }
    Scaffold(

        content = {padding->   LazyColumn {
        items(uiState.characters.size) {
            index -> CharacterListItem(character = uiState.characters[index])
        }
        }



        },



    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharacterListItem (character: Character){
    ListItem(
        text = { Text(character.name) },
        secondaryText = {
            Text(character.location.name)
        },
        icon = {

            KamelImage(
                resource = asyncPainterResource(character.image),
                contentDescription = character.image,
                modifier = Modifier.size(50.dp,50.dp).clip(RoundedCornerShape(50.dp)).border(2.dp,Color.Gray,

                ))
        }
    ) 
}