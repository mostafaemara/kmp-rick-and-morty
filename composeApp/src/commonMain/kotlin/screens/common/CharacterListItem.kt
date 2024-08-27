import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.material3.AssistChipDefaults.assistChipColors
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

