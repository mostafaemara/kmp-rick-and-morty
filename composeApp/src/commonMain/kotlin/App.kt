import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import di.appModule
import di.injectDependancies
import kmp_rick_and_morty.composeapp.generated.resources.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.core.context.*
import theme.*


@Composable


fun App() {


    commonApp()


}

@Composable
@Preview
fun commonApp() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        AppTheme(content = {

            Navigator(HomeScreen)


        })
    }

}


