package screens.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Biotech
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.rickandmorty.graphql.CharacterQuery
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource


@Composable
fun CharacterHeader(character: CharacterQuery.Character) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            KamelImage(

                resource = asyncPainterResource(character.image!!),
                contentDescription = character.image,
                modifier = Modifier.height(150.dp).width(150.dp),
                contentScale = ContentScale.FillWidth
            )
            Column {
                InfoListTile(
                    title = "Status",
                    trailing = character.status!!,
                    leading = {
                        Box(
                            modifier = Modifier.size(24.dp).clip(CircleShape)
                                .background(color = Color.Green)
                        )
                    }
                )
                HorizontalDivider()
                InfoListTile(
                    title = "Specie", trailing = character.species!!,
                    leading = {
                        Icon(
                            Icons.Outlined.Biotech,
                            contentDescription = "species",
                        )
                    }
                )
                HorizontalDivider()
                InfoListTile(
                    title = "Gender",
                    trailing = character.gender!!,
                    leading = {
                        Icon(
                            Icons.Outlined.Person, contentDescription = "gender"

                        )
                    }
                )


            }
        }
        InfoListTile(
            title = "Origin",
            trailing = character.origin?.name!!,
            leading = {
                Icon(
                    Icons.Outlined.Place, contentDescription = "gender"

                )
            }
        )

        InfoListTile(
            title = "Location",
            trailing = character.location?.name!!,
            leading = {
                Icon(
                    Icons.Outlined.MyLocation, contentDescription = "gender"

                )
            }
        )
    }
}

