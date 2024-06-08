import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kmp_rick_and_morty.composeapp.generated.resources.Res
import kmp_rick_and_morty.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
val charactersViewModel = getViewModel(Unit, viewModelFactory {
    CharactersViewModel(
        RickAndMortyApi()
    )
})
        val uiState by charactersViewModel.uiState.collectAsState()
LaunchedEffect( charactersViewModel){
    charactersViewModel.getCharacters()
}
        CharactersPage(uiState )
    }
}