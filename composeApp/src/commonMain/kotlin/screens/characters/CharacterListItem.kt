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
import com.rickandmorty.graphql.CharactersQuery
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.CharacterStatus
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun CharacterListItem(character: CharactersQuery.Result, onClick: () -> Unit) {
    ListItem(

        headlineContent = { character.name?.let { Text(it) } }, modifier = Modifier.clickable {
            onClick()

        },
        supportingContent = {
            character.species?.let { Text(it) }
        },

        trailingContent = {
            AssistChip(
                label = { character.status?.let { Text(it) } },
                onClick = {},
                colors = when (character.status) {
                    CharacterStatus.ALIVE.name -> assistChipColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        labelColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )

                    CharacterStatus.DEAD.name -> assistChipColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        labelColor = MaterialTheme.colorScheme.onErrorContainer
                    )

                    CharacterStatus.UNKNOWN.name -> assistChipColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    else -> {
                        assistChipColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            )

        },
        leadingContent = {

            KamelImage(
                resource = asyncPainterResource(character.image!!),
                contentDescription = character.image,
                modifier = Modifier.size(50.dp, 50.dp).clip(RoundedCornerShape(50.dp)).border(
                    2.dp, Color.Gray,

                    )
            )
        }
    )
}

