package screens.common

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.Episode


@Composable
fun EpisodeListItem(name: String, airDate: String, episode: String, onClick: () -> Unit) {

    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineContent = { Text(name) },
        trailingContent = { Icon(Icons.AutoMirrored.Default.ArrowForwardIos, contentDescription = null) },
        supportingContent = {
            Text(episode)
        },

        )
}