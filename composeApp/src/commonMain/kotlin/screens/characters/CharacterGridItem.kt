package screens.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun CharacterGridItem(name: String, imageUrl: String, onClick: () -> Unit) {

    Box(
        modifier = Modifier.fillMaxSize().clip(
            RoundedCornerShape(
                12.dp

            )

        ).border(
            width = 2.dp, color = Color.Gray
        ).clickable(onClick = onClick)
    ) {
        KamelImage(


            resource = asyncPainterResource(imageUrl),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth

        )
        Box(
            modifier = Modifier.background(color = Color.Black.copy(alpha = 0.5f)).align(
                Alignment.BottomCenter
            ).fillMaxWidth().height(40.dp)
        ) {
            Text(
                name, style = MaterialTheme.typography.titleSmall.copy(
                    color = Color.White
                ), modifier = Modifier.padding(8.dp)
            )
        }

    }
}