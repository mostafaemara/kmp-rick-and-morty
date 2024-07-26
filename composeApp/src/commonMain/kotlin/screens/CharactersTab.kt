import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.AssistChipDefaults.assistChipColors
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kmp_rick_and_morty.composeapp.generated.resources.Res
import kmp_rick_and_morty.composeapp.generated.resources.characters
import model.Character
import model.CharacterStatus
import screens.CharacterDetailsScreen
import viewmodel.Status


object CharactersTab : Tab {


    override val options: TabOptions
        @Composable
        get() {
            return remember {
                TabOptions(index = 0u, title = "Characters", icon = null)
            }
        }

    @Composable
    override fun Content() {

        val charactersViewModel = getViewModel(Unit, viewModelFactory {
            CharactersViewModel(
                api = RickAndMortyApi()

            )
        })
        val uiState by charactersViewModel.uiState.collectAsState()
        val listState = rememberLazyListState()
        val navigator = LocalNavigator.currentOrThrow.parent;
        LaunchedEffect(charactersViewModel) {
            charactersViewModel.getCharacters()
        }
        LaunchedEffect(listState) {
            snapshotFlow { listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size }.collect { visibleItemsCount ->
                if (visibleItemsCount >= uiState.characters.size) {
                    charactersViewModel.getNextCharacters();
                }
            }
        }
        Scaffold(

            content = { padding ->
                when (uiState.status) {
                    Status.SUCCESS -> Column {
                        LazyColumn(state = listState) {
                            itemsIndexed(uiState.characters) { index, character ->
                                CharacterListItem(character = character, onClick = {
                                    navigator?.push(CharacterDetailsScreen(characterId = character.id))
                                })
                            }

                        }
                    }

                    Status.IDLE, Status.LOADING -> Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                    Status.ERROR -> Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Text("Somthing Went Wrong")
                    }
                }


            },


            )
    }

}


@Composable
fun CharacterListItem(character: Character, onClick: () -> Unit) {
    ListItem(

        headlineContent = { Text(character.name) }, modifier = Modifier.clickable {
            onClick()

        },
        supportingContent = {
            Text(character.species)
        },

        trailingContent = {
            AssistChip(
                label = { Text(character.status.name) },
                onClick = {},
                colors = when (character.status) {
                    CharacterStatus.ALIVE -> assistChipColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        labelColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )

                    CharacterStatus.DEAD -> assistChipColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        labelColor = MaterialTheme.colorScheme.onErrorContainer
                    )

                    CharacterStatus.UNKNOWN -> assistChipColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            )

        },
        leadingContent = {

            KamelImage(
                resource = asyncPainterResource(character.image),
                contentDescription = character.image,
                modifier = Modifier.size(50.dp, 50.dp).clip(RoundedCornerShape(50.dp)).border(
                    2.dp, Color.Gray,

                    )
            )
        }
    )
}