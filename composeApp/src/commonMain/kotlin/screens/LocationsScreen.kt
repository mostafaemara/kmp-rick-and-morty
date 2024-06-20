import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

        content = {padding->   LazyColumn {
        items(uiState.locations.size) {
            index -> LocationListItem(location = uiState.locations[index])
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
            location?.type?.let { Text(it) }
        },

    ) 
}