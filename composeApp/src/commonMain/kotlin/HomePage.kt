import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kmp_rick_and_morty.composeapp.generated.resources.Res
import kmp_rick_and_morty.composeapp.generated.resources.characters
import kmp_rick_and_morty.composeapp.generated.resources.episodes
import kmp_rick_and_morty.composeapp.generated.resources.locations

import org.jetbrains.compose.resources.StringResource

enum class HomeScreenRoutes(val title: StringResource) {
    characters(title = Res.string.characters), episode(title = Res.string.episodes), location(title = Res.string.locations)
}

@Composable
fun HomePage() {
    val navController = rememberNavController()
    Scaffold(      topBar ={ TopAppBar(title= { Text("Rick And Morty")})},
        bottomBar = {
            BottomNavigation(
    
                ) {
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Person,"") },
                        selected = navController.currentDestination?.route == HomeScreenRoutes.characters.name,
                        onClick = {
                            navController.navigate(
                                route = HomeScreenRoutes.characters.name
                                )
                        } ,
                        label = {Text("Characters")}
                    )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.LocationOn,"") },
                    
                        selected = navController.currentDestination?.route == HomeScreenRoutes.location.name ,
                        onClick = {
                            navController.navigate(
                                route = HomeScreenRoutes.location.name
                                )
                        } ,
                        label = {Text("Locationa")}
                        )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.PlayArrow,"") },
                        selected = navController.currentDestination?.route == HomeScreenRoutes.episode.name ,
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
                Text("Episodes")
            }
            composable(
                route = HomeScreenRoutes.location.name
            ) {
                Text("Locations")
            }
        }

    }
}