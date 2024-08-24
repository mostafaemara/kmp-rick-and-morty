package screens.episodes

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.Episode


@Composable
fun EpisodeListItem(episode: Episode, onClick: () -> Unit) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineContent = { Text(episode.episode) },
        trailingContent = { Text(episode.airDate) },
        supportingContent = {
            Text(episode.name)
        },

        )
}