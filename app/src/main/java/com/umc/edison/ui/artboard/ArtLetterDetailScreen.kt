package com.umc.edison.ui.artboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.umc.edison.R
import com.umc.edison.presentation.artletter.ArtLetterDetailState
import com.umc.edison.presentation.artletter.ArtLetterDetailViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.CustomDraggableBottomSheet
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray800

@Composable
fun ArtLetterDetailScreen(
    navHostController: NavHostController,
    viewModel: ArtLetterDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenHeightPx = with(LocalDensity.current) { screenHeight.toPx() }
    val collapsedOffset = screenHeightPx * 0.6f
    val expandedOffset = 0f

    BaseContent(
        uiState = uiState,
        clearToastMessage = { viewModel.clearToastMessage() }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
            ) {
                AsyncImage(
                    model = "https://cdn.pixabay.com/photo/2025/02/03/21/01/forest-9380294_1280.jpg",
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bookmark),
                        contentDescription = "Bookmark",
                        tint = if (uiState.artLetter.scraped) Color(0xFFFFDE66) else Gray500,
                        modifier = Modifier
                            .clip(CircleShape)
                            .padding(4.dp)
                    )
                }
            }

            CustomDraggableBottomSheet(
                collapsedOffset = collapsedOffset,
                expandedOffset = expandedOffset
            ) { animatedTopPadding ->
                ArtLetterDetailContent(
                    viewModel = viewModel,
                    uiState = uiState,
                    navHostController = navHostController,
                    topPadding = animatedTopPadding
                )
            }
        }
    }
}

@Composable
fun ArtLetterDetailContent(
    viewModel: ArtLetterDetailViewModel,
    uiState: ArtLetterDetailState,
    navHostController: NavHostController,
    topPadding: Dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = uiState.artLetter.title,
            color = Gray800,
            style = MaterialTheme.typography.displayLarge,
        )

        if (topPadding == 0.dp) {
            Icon(
                painter = painterResource(id = R.drawable.ic_bookmark),
                contentDescription = "BookMark",
                tint = if (uiState.artLetter.scraped) Color(0xFFFFDE66) else Gray500,
                modifier = Modifier.clickable { viewModel.scrapArtLetter() }
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val tags = uiState.artLetter.tags.split(" ")
        tags.forEach { tag ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(Gray100)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tag,
                    color = Gray800,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }

    HorizontalDivider(
        color = Gray300,
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    )

    Text(
        text = uiState.artLetter.content,
        color = Gray800,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(vertical = 8.dp)
    )

    Spacer(modifier = Modifier.height(24.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_like),
            contentDescription = "Like",
            tint = if (uiState.artLetter.liked) Gray800 else Gray500,
            modifier = Modifier
                .size(26.dp)
                .clickable { viewModel.likeArtLetter() }
        )
    }

    HorizontalDivider(
        color = Gray300,
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    )

    Text(
        text = "아트레터 더보기",
        style = MaterialTheme.typography.displayMedium,
        color = Gray800
    )

    Spacer(modifier = Modifier.height(12.dp))

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        // 추가 콘텐츠...
    }
}
