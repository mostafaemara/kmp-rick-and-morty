package screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun AppSearchBar(
    searchHint: String,
    value: String,
    onValueChange: (String) -> Unit,
    onSearchClear: () -> Unit
) {
    Box(
        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
    ) {
        TextField(

            value, placeholder = {
                Text(searchHint)
            },

            onValueChange = onValueChange,

            trailingIcon = {
                if (value.isNotEmpty())
                    IconButton(
                        onClick = onSearchClear,


                        content = {

                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "clear Search",
                            )
                        }
                    )
            },
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)

                .clip(
                    shape = RoundedCornerShape(
                        size = 50.dp
                    )
                ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,

                )

        )
    }
}