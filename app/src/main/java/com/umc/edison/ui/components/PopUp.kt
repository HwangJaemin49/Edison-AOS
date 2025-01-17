package com.umc.edison.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umc.edison.ui.theme.EdisonTheme
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = White000
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetPopUp(
    title: String,
    cancelText: String,
    confirmText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
) {
    BottomSheet(
        onDismiss = onDismiss,
        sheetState = sheetState,
    ) {
        BottomSheetPopUpContent(
            title = title,
            cancelText = cancelText,
            confirmText = confirmText,
            onDismiss = onDismiss,
            onConfirm = onConfirm
        )
    }
}

@Composable
private fun BottomSheetPopUpContent(
    title: String,
    cancelText: String,
    confirmText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(11.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = MaterialTheme.typography.displaySmall, color = Gray800)

        Row(
            modifier = Modifier.padding(vertical = 17.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MiddleCancelButton(
                text = cancelText,
                onClick = onDismiss,
                modifier = Modifier.weight(1f)
            )

            MiddleConfirmButton(
                text = confirmText,
                enabled = true,
                onClick = onConfirm,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetPreview() {
    EdisonTheme {
        BottomSheetPopUpContent(
            title = "텍스트 입력",
            cancelText = "텍스트 입력",
            confirmText = "텍스트 입력",
            onDismiss = {},
            onConfirm = {}
        )
    }
}