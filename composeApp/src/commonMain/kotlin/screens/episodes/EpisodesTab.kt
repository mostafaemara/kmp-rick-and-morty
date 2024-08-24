package screens.episodes

import EpisodesViewModel
import Status.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.koin.compose.koinInject
import screens.episode.EpisodeScreen


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
        val episodesViewModel = koinInject<EpisodesViewModel>()
        val uiState by episodesViewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        LaunchedEffect(episodesViewModel) {
            episodesViewModel.getEpisodes()
        }
        Scaffold(

            content = { padding ->
                when (uiState.status) {

                    SUCCESS -> LazyColumn {
                        items(uiState.episodes.size) { index ->
                            EpisodeListItem(episode = uiState.episodes[index], onClick = {
                                navigator.parent?.push(EpisodeScreen(episodeId = uiState.episodes[index].id.toString()))

                            })
                        }
                    }

                    IDLE, LOADING -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                    ERROR -> Box(
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


