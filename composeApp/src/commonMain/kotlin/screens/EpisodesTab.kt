package screens

import RickAndMortyApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kmp_rick_and_morty.composeapp.generated.resources.Res
import kmp_rick_and_morty.composeapp.generated.resources.episodes
import model.Episode
import model.Location
import viewmodel.EpisodesViewModel
import viewmodel.Status


object EpisodesTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            return remember {
                TabOptions(index = 2u, title = "Episodes", icon = null)
            }
        }

    @Composable
    override fun Content() {
        val episodesViewModel = getViewModel(Unit, viewModelFactory {
            EpisodesViewModel(
                RickAndMortyApi()
            )
        })
        val uiState by episodesViewModel.uiState.collectAsState()

        LaunchedEffect(episodesViewModel) {
            episodesViewModel.getEpisodes()
        }
        Scaffold(

            content = { padding ->
                when (uiState.status) {

                    Status.SUCCESS -> LazyColumn {
                        items(uiState.episodes.size) { index ->
                            EpisodeListItem(episode = uiState.episodes[index])
                        }
                    }

                    Status.IDLE, Status.LOADING -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                    Status.ERROR -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Somthing Went Wrong")
                    }
                }


            },


            )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeListItem(episode: Episode) {
    ListItem(
        headlineContent = { Text(episode.episode) },
        trailingContent = { Text(episode.airDate) },
        supportingContent = {
            Text(episode.name)
        },

        )
}