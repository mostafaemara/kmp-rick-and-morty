package screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.ui.tooling.preview.Preview


class CharacterDetailsScreen(private val characterId: Int) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @Preview
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow;
        Scaffold(topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.pop();
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },

                title = { Text("Rick And Morty") })
        }) { it ->
            Text(characterId.toString())
        }
    }
}
