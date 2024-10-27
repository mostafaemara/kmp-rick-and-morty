package screens.locations

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rickandmorty.graphql.LocationsQuery


@Composable
fun LocationListItem(location: LocationsQuery.Result, onClick: () -> Unit) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineContent = { Text(location.name ?: "") },

        trailingContent = {
            Icon(Icons.AutoMirrored.Outlined.ArrowForwardIos, contentDescription = "")
        },
        supportingContent = {
            location.type?.let { Text(location.type) }
        },

        )
}