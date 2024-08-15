package screens.characterDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddHome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable

fun InfoListTile(title: String, trailing: String, leading: @Composable () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth().padding(12.dp)
    ) {

        leading()
        Text(title, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.weight(1f))
        Text(trailing, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview
@Composable
fun InfoListTilePreview() {
    InfoListTile(
        title = "Title",
        trailing = "Trailing",
        leading = {
            Icon(Icons.Outlined.AddHome, contentDescription = null)
        }
    )
}