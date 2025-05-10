package com.umc.edison.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.presentation.login.MakeNickNameViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.BasicFullButton
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray800

@Composable
fun MakeNickNameScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: MakeNickNameViewModel = hiltViewModel(),
) {
    val baseState by viewModel.baseState.collectAsState()
    var textState by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        updateShowBottomNav(false)
    }

    BaseContent(
        baseState = baseState,
        clearToastMessage = { viewModel.clearToastMessage() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                text = "에디슨에서 사용할\n닉네임을 설정해주세요.",
                color = Gray800,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(top = 67.dp, bottom = 24.dp)
            )

            TextField(
                value = textState,
                onValueChange = { if (it.length <= 20) textState = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Gray100),
                placeholder = {
                    Text(
                        text = textState.ifEmpty { "닉네임을 입력해주세요. (최대 20자)" },
                        style = MaterialTheme.typography.titleMedium,
                        color = Gray600,
                    )
                },
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Gray800,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            BasicFullButton(
                text = "다음으로",
                enabled = textState.isNotEmpty(),
                modifier = Modifier,
                onClick = {
                    viewModel.makeNickName(textState, navHostController)
                },
            )
        }
    }
}
