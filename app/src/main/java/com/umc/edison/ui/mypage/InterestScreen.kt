package com.umc.edison.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.presentation.model.InterestCategory
import com.umc.edison.presentation.model.InterestModel
import com.umc.edison.presentation.model.KeywordModel
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestScreen(
    navHostController: NavHostController,
    category: InterestCategory
) {

    val interest = InterestModel(
        category = category, selectedKeywords = listOf(
            category.options[0],
            category.options[1],
        )
    )

    var selectedKeywords by remember {
        mutableStateOf(mutableSetOf<KeywordModel>().apply {
            addAll(
                interest.selectedKeywords
            )
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White000)
            .padding(start = 24.dp, top = 12.dp, end = 24.dp),
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
                text = "관심사 고르기",
                style = MaterialTheme.typography.titleLarge,
                color = Gray800,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    enabled = true,
                    state = rememberScrollState()
                )
                .padding(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = interest.category.question,
                    style = MaterialTheme.typography.displayLarge,
                    color = Gray800,
                    softWrap = true,
                )

                Text(
                    text = interest.category.questionTip,
                    style = MaterialTheme.typography.titleSmall,
                    color = Gray500,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Gray100)
                    .padding(horizontal = 24.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = interest.category.descriptionFirst,
                    style = MaterialTheme.typography.titleMedium,
                    color = Gray800
                )

                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    interest.selectedKeywords.forEach { keyword ->
                        KeywordChip(
                            keyword = keyword.name,
                            isSelected = true,
                            onClick = {
                                selectedKeywords = if (selectedKeywords.contains(keyword)) {
                                    selectedKeywords.toMutableSet().apply { remove(keyword) }
                                } else {
                                    selectedKeywords.toMutableSet().apply { add(keyword) }
                                }
                            }
                        )
                    }
                }

                interest.category.descriptionSecond?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium,
                        color = Gray800,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "선택하기",
                    style = MaterialTheme.typography.titleMedium,
                    color = Gray800,
                )

                // 선택 가능한 키워드 목록
                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    interest.category.options.forEach { keyword ->
                        KeywordChip(
                            keyword = keyword.name,
                            isSelected = selectedKeywords.contains(keyword),
                            onClick = {
                                selectedKeywords = if (selectedKeywords.contains(keyword)) {
                                    selectedKeywords.toMutableSet().apply { remove(keyword) }
                                } else {
                                    selectedKeywords.toMutableSet().apply { add(keyword) }
                                }
                            }
                        )
                    }
                }
            }

        }

    }
}
