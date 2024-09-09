import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import screens.episodes.EpisodesTab
import screens.characters.CharactersTab


object HomeScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable

    override fun Content() {
        TabNavigator(CharactersTab) {

            Scaffold(
                bottomBar = {


                    NavigationBar(

                    ) {
                        TabNavigationItem(CharactersTab)
                        TabNavigationItem(LocationsTab)
                        TabNavigationItem(EpisodesTab)

                    }
                }

            ) { innerPadding ->
                CurrentTab()


            }
        }
    }
}


@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {

    val tabNavigator = LocalTabNavigator.current;

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

