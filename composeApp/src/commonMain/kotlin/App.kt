import androidx.compose.runtime.Composable
import androidx.navigation.NavType

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import di.injectDependancies
import kmp_rick_and_morty.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource
import org.koin.core.context.*

import org.koin.dsl.koinApplication

import screens.CharacterDetailsScreen


import theme.*


@Composable


fun App() {
    commonApp()


}

@Composable
fun commonApp() {
    AppTheme(content = {

        Navigator(HomeScreen)


    })
}


