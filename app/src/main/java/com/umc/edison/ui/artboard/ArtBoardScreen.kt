package com.umc.edison.ui.artboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.umc.edison.R
import com.umc.edison.domain.model.ArtLetter
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000


@Composable
fun ArtBoardScreen(
    navHostController: NavHostController,
    artLetters: List<ArtLetter>,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        TopBar()
        EditorPickSection()
        ArtboardSection(navHostController, artLetters)
    }
}




@Composable
fun TopBar() {
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
            modifier = Modifier.size(32.dp),
            tint = Gray800
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = "Filter",
            modifier = Modifier.size(32.dp),
            tint = Gray800
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditorPickSection() {
    val pagerState = rememberPagerState(pageCount = { 3 })

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Editor’s Pick",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.displayMedium,
            color = Gray800
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Gray300),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.artletter_banner2),
                        contentDescription = "Banner Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop // 이미지가 박스 크기에 맞게 조정됨
                    )
                }
            }
            // 인디케이터
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(3) { index ->
                    Spacer(
                        modifier = Modifier
                            .padding(2.dp)
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
fun ArtboardSection(
    navHostController: NavHostController,
    artLetters: List<ArtLetter> // API에서 받아온 데이터 리스트 추가
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
            artLetters.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEach { artLetter ->
                        ArtBoardCard(
                            modifier = Modifier.weight(1f),
                            navHostController = navHostController,
                            artLetter = artLetter // 데이터 전달
                        )
                    }
                    if (rowItems.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
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
    artLetter: ArtLetter, // API에서 받은 데이터 추가
) {
    var isBookmarked by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .aspectRatio(174f / 240f)
            .clickable { navHostController.navigate(NavRoute.ArtBoardDetail.route) }
            .clip(RoundedCornerShape(10.dp))
    ) {
        AsyncImage( // API에서 받은 썸네일 이미지 적용
            model = artLetter.thumbnail,
            contentDescription = "Thumbnail Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Icon(
            painter = painterResource(id = if (isBookmarked) R.drawable.ic_bookmark else R.drawable.ic_empty_bookmark),
            contentDescription = "Bookmark",
            tint = Color.Unspecified,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(24.dp)
                .clickable { isBookmarked = !isBookmarked }
        )
        Text(
            text = artLetter.title, // API에서 받은 제목 적용
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        )
    }
}
