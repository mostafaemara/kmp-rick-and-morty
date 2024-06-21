import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.Character
import viewmodel.Status

@Composable
fun CharactersPage() {

    val charactersViewModel = getViewModel(Unit, viewModelFactory {
        CharactersViewModel(
            RickAndMortyApi()
        )
    })
    val uiState by charactersViewModel.uiState.collectAsState()
val listState= rememberLazyListState()
    LaunchedEffect( charactersViewModel){
        charactersViewModel.getCharacters()
    }
    LaunchedEffect( listState){
       snapshotFlow { listState.firstVisibleItemIndex+listState.layoutInfo.visibleItemsInfo.size }.collect{
           visibleItemsCount-> if(visibleItemsCount>=uiState.characters.size){
               charactersViewModel.getNextCharacters();
           }
       }
    }
    Scaffold(

        content = {padding->  when(uiState.status){
            Status.SUCCESS-> Column {
                LazyColumn(state = listState) {
                    itemsIndexed(uiState.characters) { index, character ->
                        CharacterListItem(character = character)
                        }

                }
            }

            Status.IDLE,    Status.LOADING -> Box(
                modifier = Modifier.fillMaxSize()
                , contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            Status.ERROR -> Box(
                modifier = Modifier.fillMaxSize()
                , contentAlignment = Alignment.Center
            ) { Text("Somthing Went Wrong")
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