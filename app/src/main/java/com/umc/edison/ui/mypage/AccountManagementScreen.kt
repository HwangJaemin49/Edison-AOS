package com.umc.edison.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun AccountManagementScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit
) {

    LaunchedEffect(Unit) {
        updateShowBottomNav(false)
    }

    val isConnected by remember { mutableStateOf(false) }
    val email by remember { mutableStateOf("edison@gmail.com") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White000)
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navHostController.popBackStack() },
                modifier = Modifier.size(18.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_left),
                    contentDescription = "뒤로가기",
                    tint = Gray800
                )
            }

            Text(
                text = "계정 관리",
                style = MaterialTheme.typography.titleLarge,
                color = Gray800,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = if (isConnected) "소셜 계정이 연결되었습니다." else "소셜 계정 연결이 필요합니다.",
                style = MaterialTheme.typography.labelLarge,
                color = Gray600,
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Google",
                style = MaterialTheme.typography.bodySmall,
                color = Gray800,
                modifier = Modifier.weight(1f)
            )

            if (isConnected) {
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray600
                )

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Gray100)
                        .clickable { /* TODO: 업데이트 기능 */ }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "업데이트",
                        style = MaterialTheme.typography.titleSmall,
                        color = Gray800
                    )
                }
            } else {
                Text(
                    text = "연결 없음",
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray600
                )

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Gray100)
                        .clickable { /* TODO: 업데이트 기능 */ }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "로그인하기",
                        style = MaterialTheme.typography.titleSmall,
                        color = Gray800
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .background(Gray100)
        )

        Spacer(modifier = Modifier.height(18.dp))

        if (isConnected) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "로그아웃",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .clickable { /* TODO: 로그아웃 기능 */ }
                )

                Text(
                    text = "회원 탈퇴",
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray800,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .clickable { /* TODO: 회원 탈퇴 기능 */ }
                )
            }
        }

    }
}
