package com.umc.edison.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umc.edison.ui.theme.EdisonTheme
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray400
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray700
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun BasicFullButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Gray800,
        contentColor = White000,
        disabledContainerColor = Gray300,
        disabledContentColor = Gray600,
    ),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = colors,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun MiddleCancelButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    onClick: () -> Unit
) {
    BasicFullButton(
        text = text,
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Gray100,
            contentColor = Gray600,
            disabledContainerColor = Gray100,
            disabledContentColor = Gray600,
        ),
        textStyle = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun MiddleConfirmButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    onClick: () -> Unit
) {
    BasicFullButton(
        text = text,
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Gray800,
            contentColor = Gray100,
            disabledContainerColor = Gray400,
            disabledContentColor = Gray700,
        ),
        textStyle = MaterialTheme.typography.titleMedium
    )
}

@Preview(showBackground = true)
@Composable
fun BasicFullButtonPreview() {
    EdisonTheme {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            BasicFullButton(
                text = "텍스트 입력",
                enabled = false,
                onClick = {}
            )
            
            Spacer(modifier = Modifier.padding(5.dp))

            BasicFullButton(
                text = "텍스트 입력",
                enabled = true,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BasicButtonPreview2() {
    EdisonTheme {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            BasicFullButton(
                text = "텍스트 입력",
                enabled = false,
                onClick = {},
                textStyle = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.padding(5.dp))

            BasicFullButton(
                text = "텍스트 입력",
                enabled = true,
                onClick = {},
                textStyle = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MiddleCancelButtonPreview() {
    EdisonTheme {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MiddleCancelButton(
                text = "텍스트 입력",
                enabled = true,
                onClick = {},
                modifier = Modifier.weight(1f)
            )

            MiddleCancelButton(
                text = "텍스트 입력",
                enabled = true,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MiddleConfirmButtonPreview() {
    EdisonTheme {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MiddleConfirmButton(
                text = "텍스트 입력",
                enabled = false,
                onClick = {},
                modifier = Modifier.weight(1f)
            )

            MiddleConfirmButton(
                text = "텍스트 입력",
                enabled = true,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }
    }
}
