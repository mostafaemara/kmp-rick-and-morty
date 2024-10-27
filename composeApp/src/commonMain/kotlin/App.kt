import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import di.appModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import screens.HomeScreen
import theme.AppTheme


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
        AppTheme(

            content = {

                Navigator(HomeScreen)


            })
    }

}


