package com.umc.edison.ui.artboard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.umc.edison.presentation.artletter.ArtLetterHomeState
import com.umc.edison.presentation.artletter.ArtLetterHomeViewModel
import com.umc.edison.presentation.model.ArtLetterPreviewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.ArtLetterCard
import com.umc.edison.ui.components.GridLayout
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun ArtLetterHomeScreen(
    navHostController: NavHostController,
    viewModel: ArtLetterHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAllArtLetters()
        viewModel.fetchEditorsPick()
    }

    BackHandler(enabled = true) {
        navHostController.navigate(NavRoute.MyEdison.route)
    }

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
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {
            EditorPickSection(uiState, navHostController)
            ArtBoardSection(viewModel, uiState, navHostController)
        }
    }
}

@Composable
fun TopBar(
    navHostController: NavHostController,
    viewModel: ArtLetterHomeViewModel
) {
    var showPopup by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 8.dp),
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

@Composable
fun EditorPickSection(
    uiState: ArtLetterHomeState,
    navHostController: NavHostController,
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "Editor’s Pick",
            modifier = Modifier.padding(horizontal = 24.dp),
            style = MaterialTheme.typography.displayMedium,
            color = Gray800
        )

        val pagerState = rememberPagerState(pageCount = { uiState.editorsPick.size })

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val artLetter = uiState.editorsPick[page]

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Gray300)
                        .clickable { navHostController.navigate(NavRoute.ArtLetterDetail.createRoute(artLetter.artLetterId)) },
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = artLetter.thumbnail,
                        contentDescription = "Banner Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // 인디케이터
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .wrapContentSize()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                repeat(uiState.editorsPick.size) { index ->
                    Spacer(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (pagerState.currentPage == index) Gray800 else White000)
                    )
                }
            }
        }
    }
}


@Composable
fun ArtBoardSection(
    viewModel: ArtLetterHomeViewModel,
    uiState: ArtLetterHomeState,
    navHostController: NavHostController,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "당신을 자극할 세상의 이모저모",
            style = MaterialTheme.typography.displayMedium,
            color = Gray800
        )

        GridLayout(
            columns = 2,
            items = uiState.artLetters,
        ) {
            ArtLetterCard(
                artLetter = it as ArtLetterPreviewModel,
                onArtLetterClick = { artLetter ->
                    navHostController.navigate(
                        NavRoute.ArtLetterDetail.createRoute(
                            artLetter.artLetterId
                        )
                    )
                },
                onBookmarkClick = { artLetter ->
                    viewModel.postArtLetterScrap(artLetter.artLetterId)
                }
            )
        }
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