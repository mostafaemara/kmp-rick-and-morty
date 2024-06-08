import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.Character

@Composable
fun CharactersPage(uiState: CharactersUIState) {
    LazyColumn {
        items(uiState.characters.size) {
            index -> CharacterListItem(character = uiState.characters[index])
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharacterListItem (character: Character){
    ListItem(
        text = { Text(character.name) },
        icon = {
            KamelImage(
                resource = asyncPainterResource(character.image),
                contentDescription = character.image
            )
        }
    ) 
}