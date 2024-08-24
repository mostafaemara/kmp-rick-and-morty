package screens.locationDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LocationDetailsHeader(dimension: String, type: String) {

    Column(modifier = Modifier.padding(12.dp)) {
        Text("Info", style = MaterialTheme.typography.labelLarge)
        Box(modifier = Modifier.padding(top = 12.dp))
        ListItem(
            modifier = Modifier.background(color = Color.Transparent),

            leadingContent = {
                Icon(
                    Icons.Outlined.Public,
                    contentDescription = null
                )
            },
            headlineContent = {

                Text("Dimension")
            },
            trailingContent = {
                Text(dimension)
            }
        )
        HorizontalDivider()
        ListItem(

            leadingContent = {
                Icon(
                    Icons.Outlined.Place,
                    contentDescription = null
                )
            },
            headlineContent = {

                Text("Type")
            },

            trailingContent = {
                Text(type)
            }
        )
    }
    

}


@Preview
@Composable
fun LocationDetailsHeaderPreview() {
    LocationDetailsHeader(dimension = "Diemension", type = "Type")
}