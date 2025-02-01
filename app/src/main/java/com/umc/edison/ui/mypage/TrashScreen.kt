package com.umc.edison.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.ui.components.CheckBoxButton
import com.umc.edison.ui.components.MiddleCancelButton
import com.umc.edison.ui.components.MiddleConfirmButton
import com.umc.edison.ui.components.RadioButton
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun TrashScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit
) {

    LaunchedEffect(Unit) {
        updateShowBottomNav(false)
    }

    var selectedItems by remember { mutableStateOf(setOf<Int>()) }
    val items = List(10) { "오늘 한 새벽 통화" }
    val allSelected = selectedItems.size == items.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White000)
            .padding(horizontal = 24.dp, vertical = 12.dp)
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
                text = "휴지통",
                style = MaterialTheme.typography.titleLarge,
                color = Gray800,
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "삭제된 버블은 30일간 보관됩니다.",
                style = MaterialTheme.typography.titleSmall,
                color = Gray500
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .clickable {
                    selectedItems = if (allSelected) emptySet() else items.indices.toSet()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = allSelected,
                onClick = {
                    selectedItems = if (allSelected) emptySet() else items.indices.toSet()
                }
            )
            Text(
                text = "전체",
                style = MaterialTheme.typography.bodySmall,
                color = Gray800,
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "버블 선택",
                style = MaterialTheme.typography.bodySmall,
                color = Gray600
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items.indices.toList()) { index ->
                val isSelected = selectedItems.contains(index)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            selectedItems = if (isSelected) {
                                selectedItems - index
                            } else {
                                selectedItems + index
                            }
                        }
                        .background(if (isSelected) Gray100 else Color.Transparent)
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = items[index],
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray800,
                        modifier = Modifier.weight(1f)
                    )

                    CheckBoxButton(
                        selected = isSelected,
                        onClick = {
                            selectedItems = if (isSelected) {
                                selectedItems - index
                            } else {
                                selectedItems + index
                            }
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 17.dp),
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            MiddleCancelButton(
                text = "복원",
                onClick = { /* TODO */ },
                modifier = Modifier.weight(1f)
            )

            MiddleConfirmButton(
                text = "삭제",
                enabled = selectedItems.isNotEmpty(),
                onClick = { /* TODO */ },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
