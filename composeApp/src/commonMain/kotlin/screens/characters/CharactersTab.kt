package  screens.characters

import CharactersViewModel
import Status
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
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
import kmp_rick_and_morty.composeapp.generated.resources.Res
import kmp_rick_and_morty.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
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

    override fun Content() {

        val charactersViewModel: CharactersViewModel = koinInject<CharactersViewModel
            >()

        val uiState by charactersViewModel.uiState.collectAsState()
        val gridState = rememberLazyGridState()
        val filterListState = rememberLazyListState()
        val navigator = LocalNavigator.currentOrThrow.parent;

        val sheetState = rememberModalBottomSheetState()

        LaunchedEffect(charactersViewModel) {
            charactersViewModel.getCharacters()
        }
        LaunchedEffect(gridState) {
            snapshotFlow { gridState.firstVisibleItemIndex + gridState.layoutInfo.visibleItemsInfo.size }.collect { visibleItemsCount ->
                if (visibleItemsCount >= uiState.characters.size) {
                    charactersViewModel.getNextCharacters();
                }
            }
        }

        Scaffold(
            topBar = {
                Column {
                    TopAppBar(


                        title = {

                            Image(

                                painter = painterResource(
                                    Res.drawable.logo
                                ),
                                contentDescription = "logo",
                                alignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth()

                            )
                        },
                    )
                    TextField(
                        "Search Characters",

                        onValueChange = {},
                        trailingIcon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search") },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp, start = 12.dp, end = 12.dp).clip(
                            shape = RoundedCornerShape(
                                size = 50.dp
                            )
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,

                            )

                    )

                }

            },


            content = { padding ->


                when (uiState.status) {
                    Status.SUCCESS ->
                        Column(modifier = Modifier.padding(padding)) {
                            LazyRow(

                                state = filterListState,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(horizontal = 12.dp)


                            ) {

                                item() {
                                    FilterChip(

                                        enabled = true,
                                        selected = false,
                                        label = { Text("Dead") },
                                        onClick = {},


                                        )
                                }
                                item() {
                                    FilterChip(

                                        enabled = true,
                                        selected = true,
                                        label = { Text("Alive") },
                                        onClick = {},
                                        leadingIcon = {
                                            Icon(Icons.Default.Done, contentDescription = "")
                                        }

                                    )
                                }
                                item() {
                                    FilterChip(

                                        enabled = true,
                                        selected = false,
                                        label = { Text("Unkown") },
                                        onClick = {},


                                        )
                                }
                                item() {
                                    FilterChip(

                                        enabled = true,
                                        selected = false,
                                        label = { Text("Male") },
                                        onClick = {},


                                        )
                                }
                                item() {
                                    FilterChip(

                                        enabled = true,
                                        selected = false,
                                        label = { Text("Female") },
                                        onClick = {},


                                        )
                                }
                                item() {
                                    FilterChip(

                                        enabled = true,
                                        selected = false,
                                        label = { Text("Genderless") },
                                        onClick = {},


                                        )
                                }
                            }
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(minSize = 400.dp),


                                state = gridState, modifier = Modifier.padding(horizontal = 12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(uiState.characters) { character ->
                                    CharacterGridItem(
                                        character = character!!,

                                        onClick = {
                                            navigator?.push(CharacterScreen(characterId = character!!.id!!))
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
