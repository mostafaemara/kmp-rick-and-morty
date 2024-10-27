package screens.locations

import Status
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.koin.compose.koinInject
import screens.common.AppSearchBar
import screens.locationDetails.LocationDetailsScreen


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
        val filterListState = rememberLazyListState()
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

                Column(
                    modifier = Modifier.padding(
                        padding
                    )
                ) {
                    AppSearchBar(
                        searchHint = "Search Locations",
                        value = uiState.query,
                        onSearchClear = {
                            locationsViewModel.restSearch()

                        },
                        onValueChange = { it ->
                            locationsViewModel.updateSearchQuery(
                                query = it
                            )

                        }

                    )
                    LazyRow(

                        state = filterListState,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 12.dp)


                    ) {

                        item() {
                            FilterChip(

                                enabled = true,
                                selected = true,
                                label = { Text("Planet") },
                                onClick = {

                                },


                                )
                        }
                        item() {
                            FilterChip(

                                enabled = true,
                                selected = true,
                                label = { Text("Cluster") },
                                onClick = {

                                },


                                )
                        }
                        item() {
                            FilterChip(

                                enabled = true,
                                selected = true,
                                label = { Text("Space station") },
                                onClick = {

                                },


                                )
                        }
                        item() {
                            FilterChip(

                                enabled = true,
                                selected = true,
                                label = { Text("Microverse") },
                                onClick = {


                                },


                                )
                        }
                        item() {
                            FilterChip(

                                enabled = true,
                                selected = true,
                                label = { Text("Resort") },
                                onClick = {

                                },


                                )
                        }
                        item() {
                            FilterChip(

                                enabled = true,
                                selected = true,
                                label = { Text("TV") },
                                onClick = {

                                },


                                )
                        }
                        item {
                            FilterChip(

                                enabled = true,
                                selected = true,
                                label = { Text("Fantasy town") },
                                onClick = {

                                },


                                )
                        }
                        item {
                            FilterChip(

                                enabled = true,
                                selected = true,
                                label = { Text("Dream") },
                                onClick = {

                                },


                                )
                        }
                        item {
                            FilterChip(

                                enabled = true,
                                selected = true,
                                label = { Text("Customs") },
                                onClick = {

                                },


                                )
                        }
                        item {
                            FilterChip(

                                enabled = true,
                                selected = true,
                                label = { Text("Game") },
                                onClick = {

                                },


                                )
                        }
                    }
                    when (uiState.status) {

                        Status.SUCCESS -> Column(
                            modifier = Modifier.padding(padding)
                        ) {

                            LazyColumn(state = lazyListState) {
                                items(uiState.locations.size) { index ->
                                    LocationListItem(location = uiState.locations[index], onClick = {

                                        navigator?.push(LocationDetailsScreen(locationId = uiState.locations[index].id))
                                    })
                                }
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

                }


            },


            )
    }

}

