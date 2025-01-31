package com.umc.edison.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.umc.edison.R
import com.umc.edison.ui.theme.Gray200
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun EditProfileScreen(
    navHostController: NavHostController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 12.dp, end = 24.dp, bottom = 18.dp),
    ) {
        Text(
            text = "저장",
            modifier = Modifier
                .align(Alignment.End)
                .clickable(
                    onClick = {}
                ),
            style = MaterialTheme.typography.titleMedium,
            color = Gray800
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(24.dp)
        )

        EditProfileImage(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

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
                .fillMaxSize()
                .clip(CircleShape)
                .background(Gray300), // TODO: 임시 보여지는 용
        )

        AsyncImage(
            model = R.drawable.ic_camera,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
                .background(White000)
                .border(1.dp, Gray200, CircleShape),

        )
    }
}
