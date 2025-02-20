package com.umc.edison.ui.artboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.umc.edison.R
import com.umc.edison.presentation.artletter.ArtLetterDetailState
import com.umc.edison.presentation.artletter.ArtLetterDetailViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.CustomDraggableBottomSheet
import com.umc.edison.ui.components.PopUpDecision
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray800
import kotlin.math.roundToInt

@Composable
fun ArtLetterDetailScreen(
    navHostController: NavHostController,
    viewModel: ArtLetterDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenHeightPx = with(LocalDensity.current) { screenHeight.toPx() }
    val collapsedOffset = screenHeightPx * 0.4f
    val expandedOffset = 0f

    var sheetOffsetPx by remember { mutableFloatStateOf(collapsedOffset) }

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
                    model = uiState.artLetter.thumbnail,
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
                            .clickable { viewModel.scrapArtLetter() }
                    )
                }
            }

            CustomDraggableBottomSheet(
                collapsedOffset = collapsedOffset,
                expandedOffset = expandedOffset,
                onOffsetChanged = { sheetOffsetPx = it },
            ) { animatedTopPadding ->
                ArtLetterDetailContent(
                    viewModel = viewModel,
                    uiState = uiState,
                    navHostController = navHostController,
                    topPadding = animatedTopPadding
                )
            }

            if (sheetOffsetPx > 10f) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.TopCenter)
                        .padding(horizontal = 24.dp)
                        .offset {
                            IntOffset(
                                x = 0,
                                y = (sheetOffsetPx - 24.dp.roundToPx()).roundToInt()
                            )
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // 작성 시간
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(RoundedCornerShape(20.dp))
                            .background(Gray300)
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_time),
                            contentDescription = "Time",
                            modifier = Modifier.size(24.dp),
                            tint = Gray600
                        )

                        Text(
                            text = "${uiState.artLetter.readTime}분",
                            color = Gray600,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(RoundedCornerShape(100.dp))
                            .background(Gray300)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "${uiState.artLetter.likesCnt}",
                            color = Gray600,
                            style = MaterialTheme.typography.labelLarge
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_like),
                            contentDescription = "Likes",
                            modifier = Modifier.size(16.dp),
                            tint = Gray600
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(CircleShape)
                            .background(Gray300)
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = "Share",
                            modifier = Modifier.size(24.dp),
                            tint = Gray600,
                        )
                    }
                }
            }
        }

        if (uiState.showLoginModal) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF3A3D40).copy(alpha = 0.5f))
                    .clickable(
                        onClick = { viewModel.updateShowLoginModal(false) },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
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
        if (topPadding == 0.dp) {
            Text(
                text = uiState.artLetter.title,
                color = Gray800,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_bookmark),
                contentDescription = "BookMark",
                tint = if (uiState.artLetter.scraped) Color(0xFFFFDE66) else Gray500,
                modifier = Modifier
                    .clickable { viewModel.scrapArtLetter() }
                    .weight(0.2f)
            )
        } else {
            Text(
                text = uiState.artLetter.title,
                color = Gray800,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        uiState.artLetter.tags.forEach { tag ->
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
            .padding(top = 24.dp)
    )

    Column(
        modifier = if (topPadding == 0.dp) {
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 24.dp)
        } else {
            Modifier.padding(top = 24.dp)
        }
    ) {
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
            items(uiState.relatedArtLetters.size) { relatedArtLetter ->
                val artLetter = uiState.relatedArtLetters[relatedArtLetter]
                Column(
                    modifier = Modifier
                        .width(220.dp)
                        .wrapContentHeight()
                        .clickable {
                            navHostController.navigate(
                                NavRoute.ArtLetterDetail.createRoute(
                                    artLetter.artLetterId
                                )
                            )
                        },
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val imageUrl = artLetter.thumbnail

                    Box(
                        modifier = Modifier
                            .height(120.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Gray300)
                    ) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "ArtLetterPreview Category Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = artLetter.title,
                            style = MaterialTheme.typography.displaySmall,
                            color = Gray800,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_bookmark),
                            contentDescription = "Bookmark",
                            tint = if (artLetter.scraped) Color(0xFFFFDE66) else Gray500,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { viewModel.scrapArtLetter(artLetter.artLetterId) }
                                .weight(0.2f)
                        )
                    }

                    Text(
                        text = artLetter.tags.joinToString(" "),
                        style = MaterialTheme.typography.titleMedium,
                        color = Gray800,
                    )
                }
            }
        }
    }
}
