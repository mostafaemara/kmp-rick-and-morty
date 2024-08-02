package  screens.characters
import CharactersViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.*
import androidx.compose.material3.AssistChipDefaults.assistChipColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

                if (uiState.showBottomSheet) { val bottomPadding = WindowInsets.navigationBars.asPaddingValues()
                    .calculateBottomPadding().value.toInt() + 8
                    ModalBottomSheet(
                        modifier = Modifier.padding(bottom = 50.dp),

                        onDismissRequest = {
                        charactersViewModel.hideBottomSheetFilter()
                    }, sheetState = sheetState) {
                        FilterBottomSheet(
                            onFilterButtonClicked = {
                                charactersViewModel.applyFilter()
                            },
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
