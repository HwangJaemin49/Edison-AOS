package com.umc.edison.ui.my_edison

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*

@Composable
fun TextStyleDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onOptionSelected: (String) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            text = { Text("볼드") },
            onClick = { onOptionSelected("옵션 1") }
        )
        DropdownMenuItem(
            text = { Text("이탤릭") },
            onClick = { onOptionSelected("옵션 2") }
        )
        DropdownMenuItem(
            text = { Text("밑줄") },
            onClick = { onOptionSelected("옵션 3") }
        )

        DropdownMenuItem(
            text = { Text("형광펜") },
            onClick = { onOptionSelected("옵션 3") }
        )
    }
}
