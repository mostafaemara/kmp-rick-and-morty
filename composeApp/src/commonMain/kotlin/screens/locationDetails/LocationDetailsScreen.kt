package screens.locationDetails


import CharacterListItem
import Status
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.rickandmorty.graphql.LocationQuery
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.koin.compose.koinInject
import screens.character.CharacterScreen

class LocationDetailsScreen(private val locationId: String) : Screen {


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {


        val viewModel = koinInject<LocationDetailsViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(viewModel) {
            viewModel.getLocationDetails(locationId)

        }

        ScreenContent(uiState = uiState)
    }

    private fun launchedEffect(viewModel: LocationDetailsViewModel, function: () -> Unit) {

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(uiState: LocationDetailsUIState) {

    val navigator = LocalNavigator.currentOrThrow;
    Scaffold(

        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigator.pop()
                        },
                        content = {
                            Icon(

                                Icons.AutoMirrored.Outlined.ArrowBackIos,
                                contentDescription = "Back to Locations"
                            )
                        }

                    )
                },
                title = { Text("Locations") })
        }, content = { it ->
            Box(modifier = Modifier.padding(it)) {
                when (uiState.status) {
                    Status.LOADING, Status.IDLE -> CircularProgressIndicator()
                    Status.SUCCESS -> {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Text(uiState.locationDetails?.name ?: "", style = MaterialTheme.typography.titleLarge)

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                AssistChip(
                                    leadingIcon = { Icon(Icons.Outlined.LocationOn, contentDescription = "") },
                                    onClick = {},
                                    label = {
                                        Text(uiState.locationDetails!!.type)
                                    }
                                )
                                AssistChip(
                                    leadingIcon = { Icon(Icons.Outlined.Layers, contentDescription = "") },
                                    onClick = {},
                                    label = {
                                        Text(uiState.locationDetails!!.dimension)
                                    }
                                )
                            }
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("Residents", style = MaterialTheme.typography.titleMedium)
                                LazyColumn {
                                    uiState.locationDetails?.residents.let { residents ->
                                        items(residents?.size ?: 0) {

                                                index ->

                                            val resident = residents?.get(index);
                                            CharacterListItem(
                                                name = resident?.name ?: "",
                                                image = resident?.image ?: "",
                                                onClick = {
                                                    navigator.push(CharacterScreen(resident?.id ?: ""))
                                                },
                                                species = resident?.species ?: "",
                                                status = resident?.status ?: "",


                                                )

                                        }
                                    }
                                }
                            }
                        }
                    }

                    Status.ERROR -> Text("Somthing Went Wrong")

                }
            }


        }
    )
}

@Preview
@Composable
private fun ScreenContentPreview() {

    ScreenContent(
        uiState = LocationDetailsUIState(
            LocationQuery.Location(
                name = "Earth",
                dimension = "Diemention",
                created = "ssssss",
                id = "ssss",
                residents = emptyList(),
                type = ""

            ), Status.SUCCESS
        )
    )


}