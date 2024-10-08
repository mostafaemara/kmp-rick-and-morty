package screens.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Biotech
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.AssistChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.rickandmorty.graphql.CharacterQuery
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun CharacterHeader(
    image:String,
    status:String,
    species:String,
    gender:String,
    orifin:String,
    location:String

) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            KamelImage(

                resource = asyncPainterResource(image),
                contentDescription = image,
                modifier = Modifier.height(150.dp).width(150.dp),
                contentScale = ContentScale.FillWidth
            )
            Column {
                FlowRow(
               modifier=Modifier.padding(4.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    AssistChip(
                        onClick = {},
                        label= {Text(status!!)}


                    )
                    AssistChip(
                        onClick = {},
                        label= {Text(status!!)}


                    )
                    AssistChip(
                        onClick = {},
                        label= {Text(status!!)}


                    )

                }
                InfoListTile(
                    title = "Status",
                    trailing = status!!,
                    leading = {
                        Box(
                            modifier = Modifier.size(24.dp).clip(CircleShape)
                                .background(color = Color.Green)
                        )
                    }
                )
                HorizontalDivider()
                InfoListTile(
                    title = "Specie", trailing =species!!,
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
                    trailing = gender,
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
            trailing =orifin,
            leading = {
                Icon(
                    Icons.Outlined.Place, contentDescription = "gender"

                )
            }
        )

        InfoListTile(
            title = "Location",
            trailing = location,
            leading = {
                Icon(
                    Icons.Outlined.MyLocation, contentDescription = "gender"

                )
            }
        )
    }
}

@Composable
@Preview
fun CharacterHeaderPreview(){

    CharacterHeader(

        status = "",
image="",
orifin="",
gender = "Male",
species = "",
location = "")


}
