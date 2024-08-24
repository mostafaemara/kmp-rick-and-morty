package  screens.characters

import CharactersViewModel
import Status
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import screens.character.CharacterScreen


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
                                CharacterListItem(character = character!!, onClick = {
                                    navigator?.push(CharacterScreen(characterId = character.id!!))
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
                    val bottomPadding = WindowInsets.navigationBars.asPaddingValues()
                        .calculateBottomPadding().value.toInt() + 8
                    ModalBottomSheet(
                        modifier = Modifier.padding(bottom = 50.dp),

                        onDismissRequest = {
                            charactersViewModel.hideBottomSheetFilter()
                        }, sheetState = sheetState
                    ) {
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
