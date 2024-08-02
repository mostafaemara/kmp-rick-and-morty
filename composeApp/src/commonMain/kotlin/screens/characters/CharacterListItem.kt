package screens.characters

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults.assistChipColors
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.Character
import model.CharacterStatus
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun CharacterListItem(character: Character, onClick: () -> Unit) {
    ListItem(

        headlineContent = { Text(character.name) }, modifier = Modifier.clickable {
            onClick()

        },
        supportingContent = {
            Text(character.species)
        },

        trailingContent = {
            AssistChip(
                label = { Text(character.status.name) },
                onClick = {},
                colors = when (character.status) {
                    CharacterStatus.ALIVE -> assistChipColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        labelColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )

                    CharacterStatus.DEAD -> assistChipColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        labelColor = MaterialTheme.colorScheme.onErrorContainer
                    )

                    CharacterStatus.UNKNOWN -> assistChipColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            )

        },
        leadingContent = {

            KamelImage(
                resource = asyncPainterResource(character.image),
                contentDescription = character.image,
                modifier = Modifier.size(50.dp, 50.dp).clip(RoundedCornerShape(50.dp)).border(
                    2.dp, Color.Gray,

                    )
            )
        }
    )
}

