import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.*
import androidx.compose.material3.AssistChipDefaults.assistChipColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.Character
import model.CharacterStatus
import model.Gender
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin
import org.koin.compose.koinInject

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


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @Preview
    override fun Content() {

        val charactersViewModel: CharactersViewModel = koinInject<CharactersViewModel
            >()

        val uiState by charactersViewModel.uiState.collectAsState()
        val listState = rememberLazyListState()
        val navigator = LocalNavigator.currentOrThrow.parent;

        val sheetState = rememberModalBottomSheetState()

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
            topBar = {
                TopAppBar(title = { Text("Characters") }, actions = {
                    IconButton(onClick = {
                        charactersViewModel.showBottomSheetFilter()
                    }, content = {
                        Icon(Icons.Outlined.FilterList, contentDescription = "")
                    })
                })
            },


            content = { padding ->


                when (uiState.status) {
                    Status.SUCCESS ->

                        LazyColumn(state = listState, modifier = Modifier.padding(padding)) {
                            itemsIndexed(uiState.characters) { index, character ->
                                CharacterListItem(character = character, onClick = {
                                    navigator?.push(CharacterDetailsScreen(characterId = character.id))
                                })
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

                if (uiState.showBottomSheet) {
                    ModalBottomSheet(onDismissRequest = {
                        charactersViewModel.hideBottomSheetFilter()
                    }, sheetState = sheetState) {
                        FilterBottomSheet(
                            onFilterButtonClicked = {},
                            isFilterButtonEnabled = uiState.isFilterButtonEnabled,
                            name = uiState.name,
                            selectedGender = uiState.selectedGender,
                            onGenderChanged = {
                                charactersViewModel.selectGenderFilter(it)
                            }, onNameChange = {
                                charactersViewModel.updateNameFilter(it)
                            },
                            selectedStatus = uiState.selectedCharacterStatus, onStatusClicked = {
                                charactersViewModel.selectStatusFilter(it)
                            })
                    }

                }
            },


            )
    }

}

@Preview
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

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun FilterBottomSheet(
    selectedStatus: CharacterStatus?,
    onStatusClicked: (status: CharacterStatus?) -> Unit, name: String,
    onNameChange: (name: String) -> Unit,
    selectedGender: Gender?,
    onGenderChanged: (Gender?) -> Unit,
    isFilterButtonEnabled: Boolean,
    onFilterButtonClicked: () -> Unit

) {
    Column(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        TextField(modifier = Modifier.padding(16.dp).fillMaxWidth(),
            value = name,
            onValueChange = onNameChange,

            label = { Text("Filter By Name") })
        Text("Filter By Status")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(selected = selectedStatus == CharacterStatus.ALIVE, onClick = {
                onStatusClicked(CharacterStatus.ALIVE)
            }, label = { Text("Alive") })
            FilterChip(selected = selectedStatus == CharacterStatus.DEAD, onClick = {
                onStatusClicked(CharacterStatus.DEAD)
            }, label = { Text("Dead") })
            FilterChip(
                selected = selectedStatus == CharacterStatus.UNKNOWN,
                onClick = {
                    onStatusClicked(CharacterStatus.UNKNOWN)
                },
                label = { Text("Unkown") })
        }
        Text("Filter By Gender")
        FlowRow(

            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(selected = selectedGender == Gender.FEMALE, onClick = {
                onGenderChanged(Gender.FEMALE)
            }, label = { Text("Female") })
            FilterChip(selected = selectedGender == Gender.MALE, onClick = {
                onGenderChanged(Gender.MALE)
            }, label = { Text("Male") })
            FilterChip(selected = selectedGender == Gender.GENDERLESS, onClick = {
                onGenderChanged(Gender.GENDERLESS)
            }, label = { Text("Genderless") })
            FilterChip(selected = selectedGender == Gender.UNKOWN, onClick = {
                onGenderChanged(Gender.UNKOWN)
            }, label = { Text("Unkown") })
        }


        Button(onClick = onFilterButtonClicked, enabled = isFilterButtonEnabled) {
            Text("Apply Filter")
        }

        //TODO Filter by Status alive dead unkown
        //Todo Filter by Species
        //TODO Filter by gender
    }
}