package screens.episode


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.koinInject

class EpisodeScreen(private val episodeId: String) : Screen {


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {


        val viewModel = koinInject<EpisodeViewModel>()


        val uiState by
        viewModel.uiState.collectAsState()
        LaunchedEffect(viewModel) {
            viewModel.getEpsiode(episodeId)
        }

        val navigator = LocalNavigator.currentOrThrow
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text("Episodes")
                },
                navigationIcon = {
                    IconButton(
                        content = {
                            Icon(Icons.AutoMirrored.Filled.ArrowBackIos, contentDescription = "Back")
                        },
                        onClick = { navigator.pop() })
                }
            )
        }
        ) { padding ->
            Box(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (uiState.status) {
                    Status.IDLE, Status.LOADING -> CircularProgressIndicator()

                    Status.ERROR ->
                        Text("Somthing Went Wrong!")

                    Status.SUCCESS -> Column(
                        modifier = Modifier.fillMaxSize().padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(uiState.episode?.name ?: "", style = MaterialTheme.typography.headlineMedium)
                        Column {
                            Text("Info", style = MaterialTheme.typography.labelLarge)
                            ListItem(
                                leadingContent = {
                                    Icon(Icons.Outlined.Tv, contentDescription = "Back")
                                },
                                headlineContent = {
                                    Text("Episode")
                                },
                                trailingContent = {
                                    Text(uiState.episode?.episode ?: "")
                                }

                            )
                            ListItem(
                                leadingContent = {
                                    Icon(Icons.Outlined.DateRange, contentDescription = "Back")
                                },
                                headlineContent = {
                                    Text("Air Date")
                                },
                                trailingContent = {
                                    Text(uiState.episode?.air_date ?: "")
                                }

                            )
                        }
                    }

                }
            }

        }


    }


}