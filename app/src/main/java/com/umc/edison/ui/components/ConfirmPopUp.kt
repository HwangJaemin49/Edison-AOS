package com.umc.edison.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.White000

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmBottomSheet(
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
fun ConfirmBottomSheetPopUp(
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
            confirmText = confirmText,
            onConfirm = onConfirm
        )
    }
}

@Composable
private fun BottomSheetPopUpContent(
    title: String,
    confirmText: String,
    onConfirm: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(256.dp)
            .padding(horizontal = 26.dp, vertical = 50.dp),
        verticalArrangement = Arrangement.spacedBy(11.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = MaterialTheme.typography.displaySmall, color = Gray800)
        Text(text = title, style = MaterialTheme.typography.bodySmall, color = Gray600)

        Row(
            modifier = Modifier.padding(vertical = 17.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
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
fun ConfirmBottomSheetPreview() {
    EdisonTheme {
        BottomSheetPopUpContent(
            title = "텍스트 입력",
            confirmText = "텍스트 입력",
            onConfirm = {}
        )

    }
}