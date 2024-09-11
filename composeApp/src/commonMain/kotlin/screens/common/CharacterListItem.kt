import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun CharacterListItem(name: String, species: String, status: String, image: String, onClick: () -> Unit) {
    ListItem(
        modifier = Modifier.clickable { onClick() },

        headlineContent = {


            Text(name)


        },
        supportingContent = {
            Text(
                status,

                )

        },
        overlineContent = {
            Text(species)
        },

        trailingContent = {
            Icon(
                Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Forward",
            )

        },
        leadingContent = {

            KamelImage(
                resource = asyncPainterResource(image),
                contentDescription = image,
                modifier = Modifier.size(50.dp, 50.dp).clip(RoundedCornerShape(50.dp)).border(
                    2.dp, Color.Gray,

                    )
            )
        }
    )
}

