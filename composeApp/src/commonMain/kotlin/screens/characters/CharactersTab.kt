package  screens.characters

import CharacterStatus
import CharactersViewModel
import Gender
import Status
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
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
            val icon = rememberVectorPainter(Icons.Filled.AccountCircle);
            return remember {
                TabOptions(index = 0u, title = "Characters", icon = icon)
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


            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,

            topBar = {

                TopAppBar(


                    title = {

                        Image(

                            painter = painterResource(
                                Res.drawable.logo
                            ),
                            contentDescription = "logo",
                            alignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth().height(40.dp)

                        )


                    },
                )


            },


            content = { padding ->


                Column(
                    modifier = Modifier.padding(padding)
                ) {
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                    ) {
                        TextField(

                            uiState.name, placeholder = {
                                Text("Search Character")
                            },

                            onValueChange = {
                                charactersViewModel.updateSearchName(it)
                            },

                            trailingIcon = {
                                if (uiState.name.isNotEmpty())
                                    IconButton(
                                        onClick = { charactersViewModel.clearSearch() },


                                        content = {

                                            Icon(
                                                imageVector = Icons.Outlined.Close,
                                                contentDescription = "clear Search",
                                            )
                                        }
                                    )
                            },
                            modifier = Modifier.fillMaxWidth()
                                .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)

                                .clip(
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
                    LazyRow(

                        state = filterListState,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 12.dp)


                    ) {

                        item() {
                            FilterChip(

                                enabled = true,
                                selected = uiState.selectedCharacterStatus == CharacterStatus.DEAD,
                                label = { Text("Dead") },
                                onClick = {
                                    charactersViewModel.selectStatusFilter(
                                        CharacterStatus.DEAD
                                    )
                                },


                                )
                        }
                        item() {
                            FilterChip(

                                enabled = true,
                                selected = uiState.selectedCharacterStatus == CharacterStatus.ALIVE,
                                label = { Text("Alive") },
                                onClick = {
                                    charactersViewModel.selectStatusFilter(
                                        CharacterStatus.ALIVE
                                    )
                                },


                                )
                        }
                        item() {
                            FilterChip(

                                enabled = true,
                                selected = uiState.selectedCharacterStatus == CharacterStatus.UNKNOWN,
                                label = { Text("Unkown Status") },
                                onClick = {
                                    charactersViewModel.selectStatusFilter(
                                        CharacterStatus.UNKNOWN
                                    )
                                },


                                )
                        }
                        item() {
                            FilterChip(

                                enabled = true,
                                selected = uiState.selectedGender == Gender.MALE,
                                label = { Text("Male") },
                                onClick = {
                                    charactersViewModel.selectGenderFilter(Gender.MALE)

                                },


                                )
                        }
                        item() {
                            FilterChip(

                                enabled = true,
                                selected = uiState.selectedGender == Gender.FEMALE,
                                label = { Text("Female") },
                                onClick = {
                                    charactersViewModel.selectGenderFilter(Gender.FEMALE)
                                },


                                )
                        }
                        item() {
                            FilterChip(

                                enabled = true,
                                selected = uiState.selectedGender == Gender.GENDERLESS,
                                label = { Text("Genderless") },
                                onClick = {
                                    charactersViewModel.selectGenderFilter(Gender.GENDERLESS)
                                },


                                )
                        }
                        item() {
                            FilterChip(

                                enabled = true,
                                selected = uiState.selectedGender == Gender.UNKOWN,
                                label = { Text("Unkown Gender") },
                                onClick = {
                                    charactersViewModel.selectGenderFilter(Gender.UNKOWN)
                                },


                                )
                        }
                    }
                    when (uiState.status) {
                        Status.SUCCESS -> LazyVerticalGrid(
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

                }


            },


            )
    }

}
