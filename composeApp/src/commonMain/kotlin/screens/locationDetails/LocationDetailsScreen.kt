package screens.locationDetails

import Status
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import org.koin.compose.koinInject

class LocationDetailsScreen(private val locationId: String) : Screen {


    @Composable
    override fun Content() {


        val viewModel = koinInject<LocationDetailsViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        LaunchedEffect(viewModel) {
            viewModel.getLocationDetails(locationId)

        }

        Scaffold() {
            when (uiState.status) {
                Status.LOADING, Status.IDLE -> CircularProgressIndicator()
                Status.SUCCESS -> Text(uiState.locationDetails?.name!!)
                Status.ERROR -> Text("Somthing Went Wrong")

            }

        }
    }

    private fun launchedEffect(viewModel: LocationDetailsViewModel, function: () -> Unit) {

    }
}