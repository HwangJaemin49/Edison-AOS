package com.umc.edison.ui.artboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umc.edison.R
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray400
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray800

@Composable
fun ArtLetterSearchScreen() {
    val searchQuery = remember { mutableStateOf("") }

    Scaffold(
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            // 검색창
            SearchBar(searchQuery)

            Spacer(modifier = Modifier.height(48.dp))

            // 스크롤 가능한 추천 키워드 & 카테고리
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item { KeywordSection() }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item { CategorySection() }
            }
        }
    }
}

@Composable
fun SearchBar(searchQuery: MutableState<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        BasicTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            singleLine = true,
            textStyle = MaterialTheme.typography.bodySmall.copy(color = Gray800),
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray300, shape = RoundedCornerShape(100.dp))
                .padding(horizontal = 16.dp),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_topbar_search),
                        contentDescription = "검색 아이콘",
                        tint = Gray800,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(contentAlignment = Alignment.CenterStart) {
                        if (searchQuery.value.isEmpty()) {
                            Text(
                                style = MaterialTheme.typography.bodySmall,
                                text = "찰나의 영감을 검색해보세요",
                                color = Gray600,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            SearchChip(text = "디자이너")
            Spacer(modifier = Modifier.width(8.dp))
            SearchChip(text = "시간")
        }
    }
}


// 오늘 당신을 자극할 키워드
@Composable
fun KeywordSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            Icon(
                painter = painterResource(id = R.drawable.ic_keyword),
                contentDescription = "키워드 이모티콘",
                tint = Gray800,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("오늘 당신을 자극할 키워드", style = MaterialTheme.typography.titleMedium, color = Gray800)
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Chip(text = "한강")
            Spacer(modifier = Modifier.width(8.dp))
            Chip(text = "마라톤")
            Spacer(modifier = Modifier.width(8.dp))
            Chip(text = "영상")
        }
    }
}

// 아트레터 추천 카테고리
@Composable
fun CategorySection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            Icon(
                painter = painterResource(id = R.drawable.ic_artletter_tag),
                contentDescription = "태그 이모티콘",
                tint = Gray800,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("아트레터 추천 카테고리", style = MaterialTheme.typography.titleMedium, color = Gray800)
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Chip(text = "예술")
            Spacer(modifier = Modifier.width(8.dp))
            Chip(text = "문학")
            Spacer(modifier = Modifier.width(8.dp))
            Chip(text = "언어")
        }
    }
}


// 검색기록
@Composable
fun SearchChip(text: String) {
    Box(
        modifier = Modifier
            .background(Gray400.copy(alpha = 0.2f), shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "삭제 아이콘",
                tint = Gray800,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text,
                style = MaterialTheme.typography.bodySmall.copy(color = Gray800))
        }

    }
}


// 태그
@Composable
fun Chip(text: String) {
    Box(
        modifier = Modifier
            .background(Gray400.copy(alpha = 0.2f), shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(text = text,
            style = MaterialTheme.typography.bodySmall.copy(color = Gray800))
    }
}

