import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.ui.tooling.preview.Preview
import theme.*


@Composable
@Preview

fun App() {
    MaterialTheme(
        colors =  Colors(
            primary = primaryLight,
            onPrimary = onPrimaryLight,

            secondary = secondaryLight,
            onSecondary = onSecondaryLight,

            error = errorLight,
            onError = onErrorLight,

            background = backgroundLight,
            onBackground = onBackgroundLight,
            surface = surfaceLight, primaryVariant = primaryLightHighContrast , secondaryVariant = secondaryContainerLightHighContrast,
            isLight = true,

            onSurface = onSurfaceLight,

        )
    ) {

val charactersViewModel = getViewModel(Unit, viewModelFactory {
    CharactersViewModel(
        RickAndMortyApi()
    )
})
        val uiState by charactersViewModel.uiState.collectAsState()
LaunchedEffect( charactersViewModel){
    charactersViewModel.getCharacters()
}
       HomePage()
    }
}