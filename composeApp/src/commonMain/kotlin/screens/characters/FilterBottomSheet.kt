package screens.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.CharacterStatus
import model.Gender
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun FilterBottomSheet(
    selectedStatus: CharacterStatus?,
    onStatusClicked: (status: CharacterStatus?) -> Unit, name: String,
    onNameChange: (name: String) -> Unit,
    selectedGender: Gender?,
    onGenderChanged: (Gender?) -> Unit,
    isFilterButtonEnabled: Boolean,
    onFilterButtonClicked: () -> Unit

) {
    Column(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        TextField(modifier = Modifier.padding(16.dp).fillMaxWidth(),
            value = name,
            onValueChange = onNameChange,

            label = { Text("Filter By Name") })
        Text("Filter By Status")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(selected = selectedStatus == CharacterStatus.ALIVE, onClick = {
                onStatusClicked(CharacterStatus.ALIVE)
            }, label = { Text("Alive") })
            FilterChip(selected = selectedStatus == CharacterStatus.DEAD, onClick = {
                onStatusClicked(CharacterStatus.DEAD)
            }, label = { Text("Dead") })
            FilterChip(
                selected = selectedStatus == CharacterStatus.UNKNOWN,
                onClick = {
                    onStatusClicked(CharacterStatus.UNKNOWN)
                },
                label = { Text("Unkown") })
        }
        Text("Filter By Gender")
        FlowRow(

            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(selected = selectedGender == Gender.FEMALE, onClick = {
                onGenderChanged(Gender.FEMALE)
            }, label = { Text("Female") })
            FilterChip(selected = selectedGender == Gender.MALE, onClick = {
                onGenderChanged(Gender.MALE)
            }, label = { Text("Male") })
            FilterChip(selected = selectedGender == Gender.GENDERLESS, onClick = {
                onGenderChanged(Gender.GENDERLESS)
            }, label = { Text("Genderless") })
            FilterChip(selected = selectedGender == Gender.UNKOWN, onClick = {
                onGenderChanged(Gender.UNKOWN)
            }, label = { Text("Unkown") })
        }


        Button(onClick = { onFilterButtonClicked()}, enabled = isFilterButtonEnabled) {
            Text("Apply Filter")
        }
        Box(modifier = Modifier.padding(bottom = 50.dp))

        //TODO Filter by Status alive dead unkown
        //Todo Filter by Species
        //TODO Filter by gender
    }
}