package screens

import screens.locations.LocationsTab
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import kmp_rick_and_morty.composeapp.generated.resources.Res
import kmp_rick_and_morty.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import screens.characters.CharactersTab
import screens.episodes.EpisodesTab


object HomeScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable

    override fun Content() {
        TabNavigator(CharactersTab) {

            Scaffold(
                topBar = {

                    TopAppBar(


                        title = {

                            Image(

                                painter = painterResource(
                                    Res.drawable.logo
                                ),
                                contentDescription = "logo",
                                alignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth().height(40.dp)

                            )


                        },
                    )


                },
                bottomBar = {


                    NavigationBar {
                        TabNavigationItem(CharactersTab)
                        TabNavigationItem(LocationsTab)
                        TabNavigationItem(EpisodesTab)

                    }
                }

            ) { innerPadding ->
                Box(Modifier.padding(innerPadding)) {
                    CurrentTab()
                }


            }
        }
    }
}


@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {

    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        alwaysShowLabel = true,
        icon = {
            Icon(tab.options.icon!!, contentDescription = null)
        },
        label = {
            Text(text = tab.options.title)
        })

}

