package screens.episodes

import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import model.Episode


@Composable
fun EpisodeListItem(episode: Episode) {
    ListItem(
        headlineContent = { Text(episode.episode) },
        trailingContent = { Text(episode.airDate) },
        supportingContent = {
            Text(episode.name)
        },

        )
}