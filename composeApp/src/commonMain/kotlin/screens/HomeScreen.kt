import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kmp_rick_and_morty.composeapp.generated.resources.*

import org.jetbrains.compose.resources.StringResource
import screens.CharacterDetailsScreen
import screens.EpisodesScreen

enum class HomeScreenRoutes(val title: StringResource, val route: String) {
    characters(title = Res.string.characters,    route = "characters"), episode(title = Res.string.episodes,    route = "episodes"), location(
        title = Res.string.locations,
        route = "locations"
    ),
    characterDetails(title = Res.string.characterDetails,route="characterDetails");
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage() {
    val navController = rememberNavController()
    Scaffold(topBar = { TopAppBar(title = { Text("Rick And Morty") }) },
        bottomBar = {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            NavigationBar(

            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, "") },
                    selected = HomeScreenRoutes.characters.route == currentRoute,
                    onClick = {
                        navController.navigate(
                            route = HomeScreenRoutes.characters.route
                        )
                    },
                    label = { Text("Characters") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.LocationOn, "") },

                    selected = HomeScreenRoutes.location.route == currentRoute,
                    onClick = {
                        navController.navigate(
                            route = HomeScreenRoutes.location.route
                        )
                    },
                    label = { Text("Locationa") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.PlayArrow, "") },
                    selected = HomeScreenRoutes.episode.route == currentRoute,
                    onClick = {
                        navController.navigate(
                            route = HomeScreenRoutes.episode.route
                        )
                    },
                    label = { Text("Episode") }
                )
            }
        }

    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeScreenRoutes.characters.route,


            ) {
            composable(
                route = HomeScreenRoutes.characters.route
            ) {
                CharactersScreen( navController = navController)
            }
            composable(
                route = HomeScreenRoutes.episode.route
            ) {
                EpisodesScreen()
            }
            composable(
                route = HomeScreenRoutes.location.route
            ) {
                LocationScreen()
            }

            composable(
                route = HomeScreenRoutes.characterDetails.route+"/{characterId}", arguments = listOf(navArgument("characterId"){type=
                    NavType.IntType})
            ) {backStackEntry ->
                val characterId = backStackEntry.arguments?.getInt("characterId")

                characterId?.let { CharacterDetailsScreen(characterId = it) }

            }
        }

    }
}