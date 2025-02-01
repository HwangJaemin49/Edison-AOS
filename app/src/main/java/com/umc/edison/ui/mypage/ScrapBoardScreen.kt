package com.umc.edison.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun ScrapBoardScreen(
    navHostController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White000)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
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
                text = "스크랩 보드",
                style = MaterialTheme.typography.titleLarge,
                color = Gray800,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        ScrapBoardGrid(spaceSize = 24)
    }
}