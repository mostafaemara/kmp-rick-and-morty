package screens

import RickAndMortyApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import model.Episode
import model.Location
import viewmodel.EpisodesViewModel
import viewmodel.Status

@Composable
fun EpisodesScreen(){

    val episodesViewModel = getViewModel(Unit, viewModelFactory {
        EpisodesViewModel(
            RickAndMortyApi()
        )
    })
    val uiState by episodesViewModel.uiState.collectAsState()

    LaunchedEffect( episodesViewModel){
        episodesViewModel.getEpisodes()
    }
    Scaffold(

        content = {padding-> when(uiState.status){

        Status.SUCCESS  -> LazyColumn {
            items(uiState.episodes.size) {
                index -> EpisodeListItem(episode = uiState.episodes[index])
            }
            }
            Status.IDLE,    Status.LOADING -> Box(
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EpisodeListItem (episode: Episode){
    ListItem(
        text = { Text(episode.episode) },
        trailing = {Text(episode.airDate)},
        secondaryText = {
            Text(episode.name)
        },

    )
}