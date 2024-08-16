package screens.locationDetails


import Status
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

import cafe.adriel.voyager.core.screen.Screen
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.koin.compose.koinInject

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
    Scaffold(

        topBar = { TopAppBar(title = { Text("Location Details") }) }, content = {
            Box(modifier = Modifier.padding(it)) {
                when (uiState.status) {
                    Status.LOADING, Status.IDLE -> CircularProgressIndicator()
                    Status.SUCCESS -> {
                        Column {
                            LocationDetailsHeader(
                                name = uiState.locationDetails?.name ?: "",
                                dimension = uiState.locationDetails?.dimension ?: "",
                                type = uiState.locationDetails?.type ?: ""
                            )
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

    ScreenContent(uiState = LocationDetailsUIState(null, Status.LOADING))


}