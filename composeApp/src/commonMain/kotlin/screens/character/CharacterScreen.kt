package screens.character


import Status
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import screens.common.EpisodeListItem
import screens.episode.EpisodeScreen


class CharacterScreen(private val characterId: String) : Screen {


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @Preview
    override fun Content() {
        val characterDetailsViewModel: CharacterViewModel =
            koinInject<CharacterViewModel
                >()


        val uiState by characterDetailsViewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow;


        LaunchedEffect(characterDetailsViewModel) {
            characterDetailsViewModel.getCharacterDetails(characterId)
        }
        Scaffold(topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.pop();
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },

                title = {

                    Text("Characters")

                })


        }) { it ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues = it)
                    .padding(horizontal = 16.dp)
            ) {
                when (uiState.characterStatus) {
                    Status.LOADING, Status.IDLE -> CircularProgressIndicator()
                    Status.SUCCESS -> Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize().verticalScroll(
                            rememberScrollState()
                        )
                    ) {
                        Text(
                            uiState.character?.name!!,
                            style = MaterialTheme.typography.headlineLarge
                        )

                        CharacterHeader(uiState.character!!)

                        Row {
                            Text(
                                "Eposides",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(Modifier.weight(1f))
                            if (uiState.character?.episode?.size!! > 5)
                                TextButton(
                                    content = {

                                        Text("See All")


                                    },
                                    onClick = {}
                                )

                        }

                        Column() {

                            uiState.character?.episode?.forEach {
                                EpisodeListItem(
                                    episode = it?.episode ?: "",
                                    airDate = it?.air_date ?: "",
                                    name = it?.name ?: "",
                                    onClick = {
                                        navigator.push(EpisodeScreen(episodeId = it?.id ?: ""))
                                    }

                                    )
                            }

                        }
                    }


                    Status.ERROR -> Text("Somthing Wents Wrongs")

                }
            }
        }
    }
}
