package screens.character

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Biotech
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun CharacterHeader(
    image: String,
    status: String,
    species: String,
    gender: String,
    orifin: String,
    location: String

) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            KamelImage(

                resource = asyncPainterResource(image),
                contentDescription = image,
                modifier = Modifier.height(215.dp).width(215.dp),
                contentScale = ContentScale.FillWidth
            )
            Column {
                FlowRow(
                    modifier = Modifier.padding().fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),

                    ) {
                    AssistChip(
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Circle,
                                tint = when (status) {
                                    "Alive" -> Color.Green

                                    "Dead" -> Color.Red
                                    else -> Color.Gray
                                },
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        onClick = {},
                        label = { Text(status) }


                    )
                    AssistChip(
                        leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                        onClick = {},
                        label = { Text(gender) }


                    )
                    AssistChip(
                        leadingIcon = { Icon(Icons.Outlined.Biotech, contentDescription = null) },
                        onClick = {},
                        label = { Text(species) }


                    )

                }
                Text("Origin:", style = MaterialTheme.typography.labelSmall)
                Text(
                    orifin,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                )
                Box(modifier = Modifier.height(10.dp))
                Text("Last known location:", style = MaterialTheme.typography.labelSmall)
                Text(
                    location,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                )

            }
        }


    }
}

@Composable
@Preview
fun CharacterHeaderPreview() {

    CharacterHeader(

        status = "Dead",
        image = "https://lh5.googleusercontent.com/proxy/t08n2HuxPfw8OpbutGWjekHAgxfPFv-pZZ5_-uTfhEGK8B5Lp-VN4VjrdxKtr8acgJA93S14m9NdELzjafFfy13b68pQ7zzDiAmn4Xg8LvsTw1jogn_7wStYeOx7ojx5h63Gliw",
        orifin = "The Ricklantis Mixup",
        gender = "Male",
        species = "Human",
        location = "Citadel of Ricks"
    )


}
