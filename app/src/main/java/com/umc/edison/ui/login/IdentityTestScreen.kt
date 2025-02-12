package com.umc.edison.ui.login

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.presentation.login.IdentityTestViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.BasicFullButton
import com.umc.edison.ui.theme.Gray800
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.IntOffset
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray300
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun IdentityTestScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: IdentityTestViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    val tabs = listOf("아이덴티티 테스트1", "아이덴티티 테스트2", "아이덴티티 테스트3", "아이덴티티 테스트4")
    var selectedTabIndex by remember { mutableStateOf(0) }
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

    LaunchedEffect(Unit) {
        updateShowBottomNav(false)
    }

    BaseContent(
        uiState = uiState,
        clearToastMessage = { viewModel.clearToastMessage() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(33.dp))

            Box(
                modifier = Modifier
                    .height(4.dp)
                    .size(width = 192.dp, height = 4.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(100.dp))
                    .background(Gray300)
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 192.dp / tabs.size, height = 4.dp)
                        .align(Alignment.CenterStart)
                        .offset { IntOffset(x = indicatorOffset.roundToPx(), y = 0) }
                        .clip(RoundedCornerShape(100.dp))
                        .background(Gray800)
                )
            }


            Box(
                modifier = Modifier.weight(1f)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    userScrollEnabled = false
                ) { page ->
                    selectedTabIndex = pagerState.currentPage
                    when (page) {
                        0 -> Identitytest1(navHostController, pagerState, coroutineScope)
                        1 -> Identitytest2(navHostController, pagerState, coroutineScope)
                        2 -> Identitytest3(navHostController, pagerState, coroutineScope)
                        3 -> Identitytest4(navHostController, pagerState, coroutineScope)
                    }
                }
            }
        }
    }
}

@Composable
fun Identitytest1(
    navHostController: NavHostController,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    viewModel: IdentityTestViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Bottom,

        ) {

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "나를 설명하는\n단어를 골라주세요!",
            fontSize = 24.sp,
            color = Gray800,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .offset(y = (-37).dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Spacer(modifier = Modifier.weight(4f))

        BasicFullButton(
            text = "다음으로",
            enabled = true,
            modifier = Modifier,
            onClick = {

                coroutineScope.launch {
                    if (pagerState.currentPage < 3) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
            textStyle = TextStyle(fontSize = 16.sp)
        )

    }
}


@Composable
fun Identitytest2(
    navHostController: NavHostController,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    viewModel: IdentityTestViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Bottom,

        ) {

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "지금, 혹은 미래의 나는\n어떤 필드 위에 서 있나요?",
            fontSize = 24.sp,
            color = Gray800,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .offset(y = (-37).dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Spacer(modifier = Modifier.weight(4f))

        BasicFullButton(
            text = "다음으로",
            enabled = true,
            modifier = Modifier,
            onClick = {

                coroutineScope.launch {
                    if (pagerState.currentPage < 3) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
            textStyle = TextStyle(fontSize = 16.sp)
        )

    }
}


@Composable
fun Identitytest3(
    navHostController: NavHostController,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    viewModel: IdentityTestViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Bottom,

        ) {

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "나에게 가장\n영감을 주는 환경은?",
            fontSize = 24.sp,
            color = Gray800,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .offset(y = (-37).dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Spacer(modifier = Modifier.weight(4f))

        BasicFullButton(
            text = "다음으로",
            enabled = true,
            modifier = Modifier,
            onClick = {

                coroutineScope.launch {
                    if (pagerState.currentPage < 3) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
            textStyle = TextStyle(fontSize = 16.sp)
        )

    }
}


@Composable
fun Identitytest4(
    navHostController: NavHostController, pagerState: PagerState,
    coroutineScope: CoroutineScope,
    viewModel: IdentityTestViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Bottom,

        ) {

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "당신의 상상력을\n자극하는 분야를 골라주세요!",
            fontSize = 24.sp,
            color = Gray800,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .offset(y = (-37).dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Spacer(modifier = Modifier.weight(4f))

        BasicFullButton(
            text = "다음으로",
            enabled = true,
            modifier = Modifier,
            onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage < 3) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }

                    navHostController.navigate(NavRoute.TermsOfUse.route)
                }
            },
            textStyle = TextStyle(fontSize = 16.sp)
        )

    }
}