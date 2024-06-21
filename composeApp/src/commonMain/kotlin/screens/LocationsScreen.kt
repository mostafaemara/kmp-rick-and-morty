import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.Character
import model.Location
import viewmodel.LocationsViewModel
import viewmodel.Status

@Composable
fun LocationScreen(){
    
    val locationsViewModel = getViewModel(Unit, viewModelFactory {
        LocationsViewModel(
            RickAndMortyApi()
        )
    })
    val uiState by locationsViewModel.uiState.collectAsState()

    LaunchedEffect( locationsViewModel){
        locationsViewModel.getLocations()
    }
    
    Scaffold(

        content = {padding-> when(uiState.status){

           Status.SUCCESS  -> LazyColumn {
               items(uiState.locations.size) {
                index -> LocationListItem(location = uiState.locations[index])
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
fun LocationListItem (location: Location){
    ListItem(
        text = { Text(location.name) },

        secondaryText = {
            location.dimension?.let { Text(it) }
        },
        trailing = {
            location.type?.let { Chip(onClick = {}, ){
                Text(it)
            } }
        }

    ) 
}