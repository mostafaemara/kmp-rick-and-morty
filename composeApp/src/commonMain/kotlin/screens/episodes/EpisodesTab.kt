package screens.episodes

import EpisodesViewModel
import Status.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import screens.common.EpisodeListItem
import screens.episode.EpisodeScreen


object EpisodesTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Filled.Tv);
            return remember {

                TabOptions(
                    index = 2u,
                    title = "Episodes",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val episodesViewModel = koinInject<EpisodesViewModel>()
        val uiState by episodesViewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val listState = rememberLazyListState()
        LaunchedEffect(episodesViewModel) {
            episodesViewModel.getEpisodes()
            snapshotFlow {
                listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size
            }.collect { lastVisisbleIndex ->
                if (lastVisisbleIndex >= uiState.episodes?.size ?: 0) {
                    episodesViewModel.getNextEpisodes()
                }
            }
        }
        Scaffold(

            content = { padding ->
                when (uiState.status) {

                    SUCCESS -> LazyColumn(
                        state = listState
                    ) {
                        items(uiState.episodes?.size ?: 0) { index ->
                            EpisodeListItem(
                                episode = uiState.episodes?.get(index)?.episode ?: "",
                                name = uiState.episodes?.get(index)?.name ?: "",
                                airDate = uiState.episodes?.get(index)?.air_date ?: "",
                                onClick = {
                                    navigator.parent?.push(
                                        EpisodeScreen(
                                            episodeId = uiState.episodes?.get(index)?.id ?: ""
                                        )
                                    )

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


