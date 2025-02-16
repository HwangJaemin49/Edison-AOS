package com.umc.edison.ui.space

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.presentation.space.BubbleSpaceViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.Bubble
import com.umc.edison.ui.components.BubbleType
import com.umc.edison.ui.components.LabelTagList
import com.umc.edison.ui.components.calculateBubbleSize
import com.umc.edison.ui.label.LabelTabScreen
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray800
import kotlinx.coroutines.launch

@Composable
fun BubbleSpaceScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: BubbleSpaceViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) { updateShowBottomNav(true) }

    // 탭 & 페이지 관련
    val tabs = listOf("스페이스", "라벨")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(
        pageCount = { tabs.size },
        initialPageOffsetFraction = 0f,
        initialPage = 0,
    )

    val coroutineScope = rememberCoroutineScope()
    val indicatorOffset by animateDpAsState(
        targetValue = (192.dp / tabs.size) * selectedTabIndex,
        label = "Indicator Animation"
    )

    val uiState by viewModel.uiState.collectAsState()
    var showBubble by remember { mutableStateOf(false) }

    BackHandler {
        if (showBubble) {
            showBubble = false
            viewModel.selectBubble(null)
            updateShowBottomNav(true)
        } else if (selectedTabIndex == 1) {
            selectedTabIndex = 0
            coroutineScope.launch {
                pagerState.animateScrollToPage(0)
            }
        } else {
            navHostController.popBackStack()
        }
    }

    BaseContent(
        uiState = uiState,
        clearToastMessage = { viewModel.clearToastMessage() },
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) { page ->
            selectedTabIndex = pagerState.currentPage
            when (page) {
                0 -> SpaceTabScreen(showBubble = { bubble ->
                    showBubble = true
                    viewModel.selectBubble(bubble)

                    val bubbleSize = calculateBubbleSize(bubble)

                    if (bubbleSize == BubbleType.BubbleDoor) {
                        updateShowBottomNav(false)
                    }
                })

                1 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 66.dp)
                    ) {
                        LabelTabScreen(navHostController)
                    }
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(start = 24.dp, top = 30.dp, end = 24.dp)
                    .align(Alignment.CenterHorizontally)
                    .zIndex(1f),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_topbar_search),
                    contentDescription = "Search",
                    tint = Gray800,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            // TODO: 검색 기능 추가
                        }
                )

                Spacer(modifier = Modifier.weight(1f))

                tabs.forEachIndexed { index, text ->
                    Box(
                        modifier = Modifier
                            .width(192.dp / tabs.size)
                            .clip(RoundedCornerShape(100.dp))
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                    selectedTabIndex = index
                                }
                            }
                            .padding(4.dp)
                            .wrapContentSize(Alignment.Center)
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.titleMedium,
                            color = Gray800,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Spacer(modifier = Modifier.size(32.dp))
            }

            Box(
                modifier = Modifier
                    .height(4.dp)
                    .width(192.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(100.dp))
                    .background(Gray300)
                    .zIndex(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 192.dp / tabs.size, height = 4.dp)
                        .align(Alignment.CenterStart)
                        .offset {
                            IntOffset(
                                x = indicatorOffset.roundToPx(),
                                y = 0
                            )
                        }
                        .clip(RoundedCornerShape(100.dp))
                        .background(Gray800)
                )
            }
        }

        if (showBubble && uiState.selectedBubble != null) {
            val bubble = uiState.selectedBubble!!
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Gray800.copy(alpha = 0.5f))
                    .clickable(onClick = {
                        showBubble = false
                        viewModel.selectBubble(null)
                        updateShowBottomNav(true)
                    })
                    .padding(top = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Bubble(
                    bubble = bubble,
                    onBubbleClick = {
                        navHostController.navigate(NavRoute.BubbleEdit.createRoute(bubble.id))
                    },
                    onLinkedBubbleClick = { linkedBubbleId ->
                        navHostController.navigate(NavRoute.BubbleEdit.createRoute(linkedBubbleId))
                    }
                )

                LabelTagList(
                    labels = bubble.labels,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }
        }
    }
}
