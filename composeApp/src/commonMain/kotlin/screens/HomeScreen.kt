import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kmp_rick_and_morty.composeapp.generated.resources.Res
import kmp_rick_and_morty.composeapp.generated.resources.characters
import kmp_rick_and_morty.composeapp.generated.resources.episodes
import kmp_rick_and_morty.composeapp.generated.resources.locations

import org.jetbrains.compose.resources.StringResource
import screens.EpisodesScreen

enum class HomeScreenRoutes(val title: StringResource) {
    characters(title = Res.string.characters), episode(title = Res.string.episodes), location(title = Res.string.locations)
}

@Composable
fun HomePage() {
    val navController = rememberNavController()
    Scaffold(      topBar ={ TopAppBar(title= { Text("Rick And Morty")})},
        bottomBar = {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            BottomNavigation(
    
                ) {
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Person,"") },
                        selected =  HomeScreenRoutes.characters.name==currentRoute,
                        onClick = {
                            navController.navigate(
                                route = HomeScreenRoutes.characters.name
                                )
                        } ,
                        label = {Text("Characters")}
                    )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.LocationOn,"") },
                    
                        selected =  HomeScreenRoutes.location.name ==   currentRoute ,
                        onClick = {
                            navController.navigate(
                                route = HomeScreenRoutes.location.name
                                )
                        } ,
                        label = {Text("Locationa")}
                        )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.PlayArrow,"") },
                        selected =  HomeScreenRoutes.episode.name==currentRoute ,
                        onClick = {
                            navController.navigate(
                                route = HomeScreenRoutes.episode.name
                            )
                        } ,
                        label = {Text("Episode")}
                        )
                }
            }

    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeScreenRoutes.characters.name,


            ) {
            composable(
                route = HomeScreenRoutes.characters.name
            ) {
                CharactersPage()
            }
            composable(
                route = HomeScreenRoutes.episode.name
            ) {
                EpisodesScreen()
            }
            composable(
                route = HomeScreenRoutes.location.name
            ) {
                LocationScreen()
            }
        }

    }
}