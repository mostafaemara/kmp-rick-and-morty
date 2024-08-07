import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import screens.EpisodesTab


object HomeScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @Preview
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
        onClick = { tabNavigator.current = tab }, label = {},
        icon = {
            Text(tab.options.title)
        })

}