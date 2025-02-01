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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.presentation.mypage.AccountManagementViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.BackButtonTopBar
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray800

@Composable
fun AccountManagementScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: AccountManagementViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        updateShowBottomNav(false)
    }

    Scaffold(
        topBar = {
            BackButtonTopBar(
                title = "계정 관리",
                onBack = {
                    navHostController.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AccountManagementContent(viewModel)
        }
    }
}

@Composable
private fun AccountManagementContent(
    viewModel: AccountManagementViewModel
) {

    val uiState by viewModel.uiState.collectAsState()

    BaseContent(
        uiState = uiState,
        onDismiss = { viewModel.clearError() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 28.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = if (uiState.isLoggedIn) "소셜 계정이 연결되었습니다." else "소셜 계정 연결이 필요합니다.",
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

                if (uiState.isLoggedIn) {
                    Text(
                        text = uiState.user.email,
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

            if (uiState.isLoggedIn) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .background(Gray100)
                )

                Spacer(modifier = Modifier.height(18.dp))

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
                            .clickable { /* TODO: 로그아웃 기능 */ },
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "회원 탈퇴",
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray800,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                            .clickable { /* TODO: 회원 탈퇴 기능 */ },
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
