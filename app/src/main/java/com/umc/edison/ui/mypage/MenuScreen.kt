package com.umc.edison.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun MenuScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        updateShowBottomNav(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White000)
            .padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(start = 24.dp, end = 24.dp),
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
                text = "설정",
                style = MaterialTheme.typography.titleLarge,
                color = Gray800,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(12.dp)
            .background(Gray100))

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingItem(title = "휴지통") {
                navHostController.navigate(NavRoute.Trash.route)
            }

            SettingItem(title = "계정 관리") {
                navHostController.navigate(NavRoute.AccountManagement.route)
            }
        }
    }
}

@Composable
private fun SettingItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = Gray800,
            modifier = Modifier.weight(1f)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = "더보기",
            tint = Gray800,
            modifier = Modifier.size(18.dp)
        )
    }
}
