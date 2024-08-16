package screens.locationDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LocationDetailsHeader(name: String, dimension: String, type: String) {

    Column {
        Item(title = "Name", value = name, icon = {
            Icon(Icons.Outlined.Person, contentDescription = "")
        })
        Item(title = "Dimension", value = dimension, icon = {
            Icon(Icons.Outlined.Person, contentDescription = "")
        })
        Item(title = "Type", value = type, icon = {
            Icon(Icons.Outlined.Person, contentDescription = "")
        })
    }

}

@Composable
private fun Item(title: String, value: String, icon: @Composable () -> Unit) {

    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        Text(title)
        Text(value)
        icon()
    }

}

@Preview
@Composable
fun LocationDetailsHeaderPreview() {
    LocationDetailsHeader(name = "Earth", dimension = "Diemension", type = "Type")
}