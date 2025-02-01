package com.umc.edison.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.umc.edison.R
import com.umc.edison.ui.theme.*

@Composable
fun EditProfileScreen(
    navHostController: NavHostController,
) {
    var nickname by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White000)
            .padding(start = 24.dp, top = 12.dp, end = 24.dp, bottom = 18.dp),
    ) {
        Text(
            text = "저장",
            modifier = Modifier
                .align(Alignment.End)
                .clickable(onClick = {}),
            style = MaterialTheme.typography.titleMedium,
            color = Gray800
        )

        Spacer(modifier = Modifier.height(24.dp))

        EditProfileImage(modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(32.dp))

        EditProfileNameInput(nickname) { nickname = it }
    }
}

@Composable
private fun EditProfileImage(
    modifier: Modifier
) {
    Box(
        modifier = modifier.size(120.dp)
    ) {
        AsyncImage(
            model = R.drawable.ic_profile_default_small,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Box(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
                .background(White000)
                .border(1.dp, Gray200, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = R.drawable.ic_camera,
                contentDescription = "Profile Image",
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun EditProfileNameInput(
    nickname: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "닉네임",
                style = MaterialTheme.typography.bodyMedium,
                color = Gray800
            )
            Text(
                text = " *",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Red
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${nickname.text.length} / 20",
                style = MaterialTheme.typography.bodySmall,
                color = Gray600
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = nickname,
            onValueChange = { if (it.text.length <= 20) onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Gray100),
            placeholder = {
                Text(
                    text = "닉네임을 입력해주세요.",
                    style = MaterialTheme.typography.bodyMedium,
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
    }
}
