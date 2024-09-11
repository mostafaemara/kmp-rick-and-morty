import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.koin.compose.koinInject
import screens.locationDetails.LocationDetailsScreen
import screens.locations.LocationListItem
import screens.locations.LocationsViewModel


object LocationsTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Filled.LocationOn);
            return remember {
                TabOptions(index = 1u, title = "Locations", icon = icon)
            }
        }

    @Composable
    override fun Content() {

        val locationsViewModel = koinInject<LocationsViewModel>()
        val uiState by locationsViewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow.parent;
        val lazyListState = rememberLazyListState()
        LaunchedEffect(locationsViewModel) {
            locationsViewModel.getLocations()
            snapshotFlow {
                lazyListState.firstVisibleItemIndex + lazyListState.layoutInfo.visibleItemsInfo.size
            }.collect { lastVisibleIndex ->
                if (lastVisibleIndex >= uiState.locations.size) {
                    locationsViewModel.getNextLocations()

                }
            }
        }

        Scaffold(

            content = { padding ->
                when (uiState.status) {

                    Status.SUCCESS -> LazyColumn(state = lazyListState) {
                        items(uiState.locations.size) { index ->
                            LocationListItem(location = uiState.locations[index], onClick = {

                                navigator?.push(LocationDetailsScreen(locationId = uiState.locations[index].id))
                            })
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

