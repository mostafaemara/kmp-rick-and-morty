package screens.characters


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.rickandmorty.graphql.CharactersQuery.Result as Character
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun CharacterGridItem(character: Character, onClick: () -> Unit) {

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )


    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(115.dp).clip(
                RoundedCornerShape(
                    8.dp

                )

            ).clickable(onClick = onClick)

        ) {
            KamelImage(


                resource = asyncPainterResource(character.image!!),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight().width(115.dp),
                contentScale = ContentScale.FillHeight,
                alignment = Alignment.CenterStart

            )

            Column(modifier = Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(character.name!!, style = MaterialTheme.typography.titleMedium)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Circle,
                        tint = when (character.status) {
                            "Alive" -> Color.Green

                            "Dead" -> Color.Red
                            else -> Color.Gray
                        },
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )

                    Text(character.status!!, style = MaterialTheme.typography.labelMedium)
                    VerticalDivider(modifier = Modifier.height(10.dp))
                    Text(character.species!!, style = MaterialTheme.typography.labelMedium)
                }
                Column {
                    Text("Last known location:", style = MaterialTheme.typography.labelSmall)

                    Text("Last known location: Citadel of Ricks", style = MaterialTheme.typography.bodySmall)
                }

//                Column {
//                    Text("First Seen:", style = MaterialTheme.typography.labelSmall)
//
//                    Text("The Ricklantis Mixup", style = MaterialTheme.typography.bodySmall)
//                }


            }


        }
    }
}

