
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.Character
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
@Composable
fun CharactersPage(uiState: CharactersUIState) {


    Scaffold(
        topBar ={ TopAppBar(title= { Text("Rick And Morty")})},
        content = {padding->   LazyColumn {
        items(uiState.characters.size) {
            index -> CharacterListItem(character = uiState.characters[index])
        }
        }



        },

        bottomBar = {
          BottomNavigation(

            ) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Person,"") },
                    selected = true,
                    onClick = {} ,
                    label = {Text("Characters")}
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.LocationOn,"") },
                    selected = true,
                    onClick = {} ,
                    label = {Text("Locationa")}
                    )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.PlayArrow,"") },
                    selected = true,
                    onClick = {} ,
                    label = {Text("Episode")}
                    )
            }
        }

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