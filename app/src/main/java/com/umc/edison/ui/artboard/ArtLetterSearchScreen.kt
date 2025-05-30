package com.umc.edison.ui.artboard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.presentation.artletter.ArtLetterSearchState
import com.umc.edison.presentation.artletter.ArtLetterSearchViewModel
import com.umc.edison.presentation.model.ArtLetterKeyWordModel
import com.umc.edison.presentation.model.ArtLetterPreviewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.ArtLetterCard
import com.umc.edison.ui.components.GridLayout
import com.umc.edison.ui.components.PopUpDecision
import com.umc.edison.ui.components.SearchBar
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray400
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray700
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun ArtLetterSearchScreen(
    navHostController: NavHostController,
    viewModel: ArtLetterSearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val baseState by viewModel.baseState.collectAsState()
    var showPopup by remember { mutableStateOf(false) }

    BackHandler {
        if (uiState.query.isNotEmpty()) {
            viewModel.updateSearchQuery("")
        } else {
            navHostController.popBackStack()
        }
    }

    BaseContent(
        baseState = baseState,
        containerColor = Color(0xFFF5F5F5)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 16.dp)
            ) {
                // 검색창
                SearchBar(
                    value = uiState.query,
                    onValueChange = { viewModel.updateSearchQuery(it) },
                    onSearch = {
                        viewModel.searchArtLetters()
                    },
                    placeholder = "찰나의 영감을 검색해보세요"
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()), // 가로 스크롤 가능
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                uiState.recentSearches.forEach { history ->
                    SearchChip(
                        text = history,
                        onDelete = { viewModel.removeSearchHistory(history) }, // 검색어 삭제
                        onSearch = {
                            viewModel.searchArtLetters(history)
                        }
                    )
                }
                Spacer(Modifier.weight(1f))

                Text(
                    text = "서치 Tip",
                    color = Color(0xFF1669ED),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .clickable { showPopup = true }
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    if (showPopup) {
                        SearchMessage(
                            Modifier.align(Alignment.TopCenter),
                            onDismiss = { showPopup = false })
                    }
                }

            }


            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 12.dp, top = 40.dp)
            ) {
                when {
                    !uiState.isSearchActivated -> {
                        item { KeywordSection(navHostController, uiState.keywords) }
                        item { Spacer(modifier = Modifier.height(16.dp)) }
                        item { CategorySection(viewModel, uiState.categories) }
                    }

                    uiState.artLetters.isEmpty() -> {
                        item { NoResultSection(uiState.lastQuery) }
                        item { Recommend(viewModel, uiState, navHostController) }
                    }

                    else -> {
                        item {
                            SearchResultBar(viewModel, uiState.lastQuery)
                        }

                        item { Spacer(modifier = Modifier.height(16.dp)) }

                        item {
                            GridLayout(
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
                                    onBookmarkClick = { viewModel.postArtLetterScrap(it.artLetterId) }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (uiState.showLoginModal) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF3A3D40).copy(alpha = 0.5f))
                    .clickable { viewModel.updateShowLoginModal(false) },
                contentAlignment = Alignment.Center
            ) {
                PopUpDecision(
                    question = "로그인이 필요한 기능입니다",
                    positiveButtonText = "로그인",
                    negativeButtonText = "취소",
                    onPositiveClick = {
                        navHostController.navigate(NavRoute.Login.route) {
                            popUpTo(NavRoute.Login.route) {
                                inclusive = true
                            }
                        }
                        viewModel.updateShowLoginModal(false)
                    },
                    onNegativeClick = {
                        viewModel.updateShowLoginModal(false)
                    }
                )
            }
        }
    }


}

// 검색 완료 헤더
@Composable
fun SearchResultBar(
    viewModel: ArtLetterSearchViewModel,
    text: String
) {
    var showPopup by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ResultChip(text = "# $text")
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "에 관련된 아트레터",
            color = Gray800,
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        Box {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "Filter",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { showPopup = true },
                tint = Gray800
            )

            if (showPopup) {
                ArtLetterSearchPopup(
                    onLikesSort = {
                        viewModel.getSortedArtLetters("likes") // 좋아요 순 정렬 요청
                        showPopup = false
                    },
                    onScrapsSort = {
                        viewModel.getSortedArtLetters("scraps") // 스크랩 순 정렬 요청
                        showPopup = false
                    },
                    onRecentSort = {
                        viewModel.getSortedArtLetters("latest") // 최신순 정렬 요청
                        showPopup = false
                    },
                    onDismiss = {
                        showPopup = false
                    }
                )
            }
        }

    }
}

@Composable
private fun ArtLetterSearchPopup(
    onLikesSort: () -> Unit,
    onScrapsSort: () -> Unit,
    onRecentSort: () -> Unit,
    onDismiss: () -> Unit
) {
    Popup(
        alignment = Alignment.TopEnd,
        offset = IntOffset(-20, 80),
        properties = PopupProperties(
            dismissOnClickOutside = true,
            focusable = true
        ),
        onDismissRequest = { onDismiss() }
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Gray300, White000),
                    )
                )
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = Color(0xFF3A3D40).copy(alpha = 0.12f),
                    spotColor = Color(0xFF3A3D40).copy(alpha = 0.12f),
                )
                .padding(1.dp),
        ) {
            Column(
                modifier = Modifier
                    .width(150.dp)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(White000)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "공감 순",
                    modifier = Modifier.clickable { onLikesSort() },
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray800,
                    textAlign = TextAlign.Center
                )

                HorizontalDivider(modifier = Modifier.border(1.dp, Gray300))

                Text(
                    text = "스크랩 순",
                    modifier = Modifier.clickable { onScrapsSort() },
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray800,
                    textAlign = TextAlign.Center
                )

                HorizontalDivider(modifier = Modifier.border(1.dp, Gray300))

                Text(
                    text = "최신 순",
                    modifier = Modifier.clickable { onRecentSort() },
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray800,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// 오늘 당신을 자극할 키워드
@Composable
fun KeywordSection(
    navHostController: NavHostController,
    keywords: List<ArtLetterKeyWordModel>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Gray300, shape = RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_keyword),
                contentDescription = "키워드 이모티콘",
                tint = Gray800,
            )

            Text("오늘 당신을 자극할 키워드", style = MaterialTheme.typography.titleMedium, color = Gray800)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            keywords.forEach { keywordModel ->
                KeyWordChip(
                    onKeywordClick = {
                        navHostController.navigate(
                            NavRoute.ArtLetterDetail.createRoute(
                                keywordModel.artLetterId
                            )
                        )
                    },
                    keyword = keywordModel.keyword,
                )
            }
        }
    }
}

// 태그
@Composable
fun KeyWordChip(
    onKeywordClick: () -> Unit,
    keyword: String,
) {
    Box(
        modifier = Modifier
            .background(Gray400, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp)
            .clickable { onKeywordClick() }
    ) {
        Text(
            text = keyword,
            style = MaterialTheme.typography.bodySmall.copy(color = Gray800)
        )
    }
}


// 아트레터 추천 카테고리
@Composable
fun CategorySection(
    viewModel: ArtLetterSearchViewModel,
    categories: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Gray300, shape = RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_artletter_tag),
                contentDescription = "태그 이모티콘",
                tint = Gray800,
            )

            Text("아트레터 추천 카테고리", style = MaterialTheme.typography.titleMedium, color = Gray800)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                KeyWordChip(
                    onKeywordClick = { viewModel.searchArtLetters(category) },
                    keyword = category,
                )
            }
        }
    }
}


// 검색기록
@Composable
fun SearchChip(text: String, onDelete: () -> Unit, onSearch: () -> Unit) {
    Row(
        modifier = Modifier
            .background(Gray300, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp)
            .clickable(
                onClick = { onSearch() },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = "삭제 아이콘",
            tint = Gray800,
            modifier = Modifier
                .size(16.dp)
                .clickable { onDelete() }
        )

        Text(text = text, style = MaterialTheme.typography.bodySmall.copy(color = Gray800))
    }
}


// 검색 결과 없음
@Composable
fun NoResultSection(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .background(Gray100, shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "# $text",
                    style = MaterialTheme.typography.displayMedium.copy(color = Gray800)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text("와", style = MaterialTheme.typography.headlineSmall, color = Gray800)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("관련된 아트레터를 찾을 수 없어요.", style = MaterialTheme.typography.headlineSmall, color = Gray800)
    }
}


@Composable
fun ResultChip(text: String) {
    Box(
        modifier = Modifier
            .background(Gray300, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displaySmall.copy(color = Gray800)
        )
    }
}

@Composable
fun Recommend(
    viewModel: ArtLetterSearchViewModel,
    uiState: ArtLetterSearchState,
    navHostController: NavHostController,
) {
    Spacer(modifier = Modifier.height(160.dp))
    Text(
        text = "이런 아트레터는 어떤가요?",
        color = Gray800,
        style = MaterialTheme.typography.displayMedium
    )
    Spacer(modifier = Modifier.height(18.dp))

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(uiState.recommendedArtLetters) { artLetter ->
            Box(
                modifier = Modifier
                    .width(160.dp)
            ) {
                ArtLetterCard(
                    artLetter = artLetter,
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

@Composable
fun SearchMessage(modifier: Modifier, onDismiss: () -> Unit) {
    Popup(
        alignment = Alignment.TopEnd,
        offset = IntOffset(-20, 80),
        properties = PopupProperties(
            dismissOnClickOutside = true,
            focusable = false
        ),
        onDismissRequest = { onDismiss() },
    ) {
        Box(
            modifier = modifier
                .width(230.dp)
                .wrapContentSize()
                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(14.dp)
        ) {
            Column {
                Text(
                    text = "아트레터 검색 가이드",
                    style = MaterialTheme.typography.titleMedium,
                    color = Gray800,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "원하는 레터를 빠르고 정확하게 찾는 방법을 안내해 드릴게요!",
                    style = MaterialTheme.typography.labelSmall,
                    color = Gray600,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "• 검색어가 포함된 모든 레터를 보여드립니다.\n" +
                            "• 태그 및 제목에 검색어가 포함된 레터를 가장 먼저 확인할 수 있어요.\n" +
                            "• 정확한 단어로 검색할수록 원하는 레터를 쉽게 찾을 수 있어요.",
                    style = MaterialTheme.typography.labelLarge,
                    color = Gray700,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "필요한 레터를 손쉽게 찾아보세요.",
                    style = MaterialTheme.typography.labelSmall,
                    color = Gray600,
                )
            }
        }

    }

}