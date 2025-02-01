package com.umc.edison.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray700
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000
import kotlinx.coroutines.delay

@Composable
fun PopUpMulti(
    title: String,
    detail: String,
    hintText: String,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(364.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .shadow(16.dp)
            .background(White000)
            .border(1.dp, Gray100, RoundedCornerShape(16.dp))
            .padding(start = 24.dp, top = 32.dp, end = 24.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.displaySmall,
            color = Gray800,
            textAlign = TextAlign.Center
        )

        Text(
            text = detail,
            style = MaterialTheme.typography.bodySmall,
            color = Gray600,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(Gray100)
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = hintText,
                style = MaterialTheme.typography.bodyLarge,
                color = Gray600,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(Gray100)
                .clickable(onClick = onButtonClick)
                .padding(vertical = 12.dp, horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.bodySmall,
                color = Gray800,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ToastMessage(
    message: String,
    isVisible: Boolean,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(Gray700.copy(alpha = 0.7f), shape = RoundedCornerShape(20.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall,
                color = White000
            )
        }
    }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(2000)
            onDismiss()
        }
    }
}

@Composable
fun PopUpDecision(
    question: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(364.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(White000)
            .padding(start = 8.dp, top = 32.dp, end = 8.dp, bottom = 24.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.displaySmall,
                color = Gray800,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MiddleCancelButton(
                    text = negativeButtonText,
                    onClick = onNegativeClick,
                    modifier = Modifier.weight(1f)
                )

                MiddleConfirmButton(
                    text = positiveButtonText,
                    enabled = true,
                    onClick = onPositiveClick,
                    modifier = Modifier.weight(1f)
                )
            }
        }

    }
}

@Composable
fun PopUpMultiDecision(
    question: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onPositiveClick: (String) -> Unit,
    onNegativeClick: () -> Unit,
    placeholderText: String,
) {
    var textChanged by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .width(364.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(White000)
            .padding(start = 26.dp, top = 32.dp, end = 26.dp, bottom = 24.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.displaySmall,
                color = Gray800,
                textAlign = TextAlign.Center
            )

            TextField(
                value = textChanged,
                onValueChange = { textChanged = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Gray100)
                    .padding(horizontal = 8.dp),
                placeholder = {
                    Text(
                        text = placeholderText,
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray600,
                    )
                },
                textStyle = MaterialTheme.typography.bodySmall,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Gray800,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Gray800,
                    unfocusedTextColor = Gray800
                )
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MiddleCancelButton(
                    text = negativeButtonText,
                    onClick = onNegativeClick,
                    modifier = Modifier.weight(1f)
                )

                MiddleConfirmButton(
                    text = positiveButtonText,
                    enabled = true,
                    onClick = { onPositiveClick(textChanged) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

    }
}
