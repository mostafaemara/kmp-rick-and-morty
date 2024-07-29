import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kmp_rick_and_morty.composeapp.generated.resources.Res
import kmp_rick_and_morty.composeapp.generated.resources.locations
import model.Character
import model.Location
import org.koin.compose.koinInject
import viewmodel.LocationsViewModel
import viewmodel.Status


object LocationsTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            return remember {
                TabOptions(index = 1u, title = "Locations", icon = null)
            }
        }

    @Composable
    override fun Content() {

        val locationsViewModel = koinInject<LocationsViewModel>()
        val uiState by locationsViewModel.uiState.collectAsState()

        LaunchedEffect(locationsViewModel) {
            locationsViewModel.getLocations()
        }

        Scaffold(

            content = { padding ->
                when (uiState.status) {

                    Status.SUCCESS -> LazyColumn {
                        items(uiState.locations.size) { index ->
                            LocationListItem(location = uiState.locations[index])
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


@Composable
fun LocationListItem(location: Location) {
    ListItem(
        headlineContent = { Text(location.name) },

        supportingContent = {
            location.dimension?.let { Text(it) }
        },
        trailingContent = {
            location.type?.let { Text(location.type) }
        }

    )
}