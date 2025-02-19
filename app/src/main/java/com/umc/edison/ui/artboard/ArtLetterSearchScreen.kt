package com.umc.edison.ui.artboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.presentation.artletter.ArtLetterSearchViewModel
import com.umc.edison.presentation.model.ArtLetterPreviewModel
import com.umc.edison.ui.components.ArtLetterCard
import com.umc.edison.ui.components.GridLayout
import com.umc.edison.ui.components.SearchBar
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray400
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray800

@Composable
fun ArtLetterSearchScreen(
    navHostController: NavHostController,
    viewModel: ArtLetterSearchViewModel = hiltViewModel()
) {
    val searchQuery = remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()
    val isSearchActivated = remember { mutableStateOf(false) }

    Scaffold(
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
            ){
                // 검색창
                SearchBar(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    onSearch = {
                        isSearchActivated.value = true
                        viewModel.searchArtLetters(searchQuery.value, "default") // 기본 정렬 사용
                    },
                    placeholder = "찰나의 영감을 검색해보세요"
                )
            }

            if (searchHistory.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .horizontalScroll(rememberScrollState()) // 가로 스크롤 가능
                ) {
                    searchHistory.forEach { history ->
                        SearchChip(
                            text = history,
                            onDelete = { viewModel.removeSearchHistory(history) } // 검색어 삭제
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // 스크롤 가능한 추천 키워드 & 카테고리
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(horizontal = 16.dp)
//            ) {
//                item { KeywordSection() }
//                item { Spacer(modifier = Modifier.height(16.dp)) }
//                item { CategorySection() }
//            }


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                when {
                    !isSearchActivated.value -> {
                        item { KeywordSection() }
                        item { Spacer(modifier = Modifier.height(16.dp)) }
                        item { CategorySection() }
                    }

                    uiState.artLetters.isEmpty() -> {}

                    else -> {
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                ResultChip(text = "#${searchQuery.value}")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "에 관련된 아트레터",
                                    color = Gray800,
                                    style = MaterialTheme.typography.displayMedium
                                )
                            }}

                        item {Spacer(modifier = Modifier.height(16.dp))}

                        item {GridLayout(
                            columns = 2,
                            items = uiState.artLetters,
                        ) { artLetter ->
                            ArtLetterCard(
                                artLetter = artLetter as ArtLetterPreviewModel,
                                onArtLetterClick = { selectedArtLetter ->
                                    navHostController.navigate(
                                        NavRoute.ArtLetterDetail.createRoute(selectedArtLetter.artLetterId)
                                    )
                                },
                                onBookmarkClick = { artLetter ->
                                    viewModel.postArtLetterScrap(artLetter.artLetterId)
                                }
                            )
                        }
                        }
                    }
                }
            }
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
fun SearchChip(text: String, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .background(Gray300.copy(alpha = 0.2f), shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = "삭제 아이콘",
            tint = Gray800,
            modifier = Modifier
                .size(16.dp)
                .clickable { onDelete() }
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, style = MaterialTheme.typography.bodySmall.copy(color = Gray800))
    }
}


// 태그
@Composable
fun Chip(text: String) {
    Box(
        modifier = Modifier
            .background(Gray300.copy(alpha = 0.2f), shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(text = text,
            style = MaterialTheme.typography.bodySmall.copy(color = Gray800))
    }
}

@Composable
fun ResultChip(text: String) {
    Box(
        modifier = Modifier
            .background(Gray300.copy(alpha = 0.2f), shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(text = text,
            style = MaterialTheme.typography.displaySmall.copy(color = Gray800))
    }
}

