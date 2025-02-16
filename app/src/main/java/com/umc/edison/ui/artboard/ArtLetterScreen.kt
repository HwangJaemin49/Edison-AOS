package com.umc.edison.ui.artboard

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.umc.edison.R
import com.umc.edison.presentation.artletter.ArtLetterState
import com.umc.edison.presentation.artletter.ArtLetterViewModel
import com.umc.edison.presentation.model.ArtLetterModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000


@Composable
fun ArtLetterScreen(
    navHostController: NavHostController,
    viewModel: ArtLetterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    BaseContent(
        uiState = uiState,
        clearToastMessage = { viewModel.clearToastMessage() },
        topBar = { TopBar(navHostController, viewModel) },
        modifier = Modifier.fillMaxSize(),
        bottomBar = {}
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            //EditorPickSection(viewModel)
            ArtboardSection(navHostController, uiState, viewModel)
        }
    }
}

@Composable
fun TopBar(
    navHostController: NavHostController,
    viewModel: ArtLetterViewModel) {
    var showPopup by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_topbar_search),
            contentDescription = "Search",
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    navHostController.navigate(NavRoute.ArtLetterSearch.route)
                },
            tint = Gray800
        )

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
                ArtLetterPopup(
                    onLikesSort = {
                        viewModel.fetchSortedArtLetters("likes") // 좋아요 순 정렬 요청
                        showPopup = false
                    },
                    onScrapsSort = {
                        viewModel.fetchSortedArtLetters("scraps") // 스크랩 순 정렬 요청
                        showPopup = false
                    },
                    onRecentSort = {
                        viewModel.fetchSortedArtLetters("latest") // 최신순 정렬 요청
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

//@Composable
//fun EditorPickSection(viewModel: ArtLetterViewModel) {
//    val editorPickState by viewModel.uiEditorPickState.collectAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.postEditorPick(listOf(1)) // ID로 API 호출
//    }
//
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Text(
//            text = "Editor’s Pick",
//            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
//            style = MaterialTheme.typography.displayMedium,
//            color = Gray800
//        )
//
//        when {
//            editorPickState.isLoading -> {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(180.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator(color = Gray800)
//                }
//            }
//
//            editorPickState.artletters.isEmpty() -> {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(180.dp)
//                        .background(Gray300),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(text = "데이터가 없습니다.", color = Gray800)
//                }
//            }
//
//            else -> {
//                val pagerState = rememberPagerState(pageCount = { editorPickState.artletters.size })
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(180.dp)
//                ) {
//                    HorizontalPager(
//                        state = pagerState,
//                        modifier = Modifier.fillMaxSize()
//                    ) { page ->
//                        val artLetter = editorPickState.artletters[page]
//
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .background(Gray300),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            AsyncImage(
//                                model = artLetter.thumbnail,
//                                contentDescription = "Banner Image",
//                                modifier = Modifier.fillMaxSize(),
//                                contentScale = ContentScale.Crop
//                            )
//                        }
//                    }
//
//                    // 인디케이터
//                    Row(
//                        modifier = Modifier
//                            .align(Alignment.BottomCenter)
//                            .padding(bottom = 8.dp),
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        repeat(editorPickState.artletters.size) { index ->
//                            Spacer(
//                                modifier = Modifier
//                                    .padding(2.dp)
//                                    .size(8.dp)
//                                    .clip(CircleShape)
//                                    .background(if (pagerState.currentPage == index) Gray800 else White000)
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}


@Composable
fun ArtboardSection(
    navHostController: NavHostController,
    uiState: ArtLetterState,
    viewModel: ArtLetterViewModel,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "당신을 자극할 세상의 이모저모",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.displayMedium,
            color = Gray800
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            uiState.artletters.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEach { artLetter ->
                        ArtBoardCard(
                            modifier = Modifier.weight(1f),
                            navHostController = navHostController,
                            uiState = artLetter,
                            viewModel = viewModel
                        )
                    }
                    if (rowItems.size < 2) {
                        Spacer(modifier = Modifier.weight(1f)) // 아이템이 하나만 있으면 빈 공간 추가
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@Composable
fun ArtBoardCard(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    uiState: ArtLetterModel,
    viewModel: ArtLetterViewModel
) {
    val scrapStatus by viewModel.scrapStatus.collectAsState()
    val isBookmarked = scrapStatus[uiState.artletterId] ?: false


    Box(
        modifier = modifier
            .aspectRatio(174f / 240f)
            .clickable { navHostController.navigate(NavRoute.ArtLetterDetail) }
            .clip(RoundedCornerShape(10.dp))
    ) {
        if (uiState.thumbnail.isNullOrBlank()) {
            Image(
                painter = painterResource(id = R.drawable.delivery),
                contentDescription = "Default Thumbnail",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = uiState.thumbnail,
                contentDescription = "Thumbnail Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Icon(
            painter = painterResource(id = if (isBookmarked) R.drawable.ic_bookmark else R.drawable.ic_empty_bookmark),
            contentDescription = "Bookmark",
            tint = Color.Unspecified,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(24.dp)
                .clickable {
                    Log.d("ArtBoardCard", "Bookmark clicked: ${uiState.artletterId}") // 북마크 클릭 로그 추가
                    viewModel.toggleScrap(uiState.artletterId) // 북마크 요청
                }
        )
        Text(
            text = uiState.title,
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        )
    }
}


@Composable
private fun ArtLetterPopup(
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