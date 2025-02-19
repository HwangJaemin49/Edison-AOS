package com.umc.edison.ui.edison

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.umc.edison.presentation.edison.MyEdisonViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.bubblestorage.BubbleStorageScreen
import com.umc.edison.ui.components.BubbleInput
import com.umc.edison.ui.components.MyEdisonNavBar
import com.umc.edison.ui.login.PrefsHelper
import com.umc.edison.ui.navigation.NavRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MyEdisonScreen(
    navController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: MyEdisonViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var isViewMode by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        pageCount = { 2 },
        initialPageOffsetFraction = 0f,
        initialPage = 0,
    )

    BackHandler {
        (context as? Activity)?.finish()
    }

    LaunchedEffect(Unit) {
        viewModel.fetchBubbles()
        updateShowBottomNav(true)
        PrefsHelper.setMainScreenVisited(context)
    }

    LaunchedEffect(uiState.bubbles) {
        if (uiState.bubbles.isNotEmpty()) {
            pagerState.animateScrollToPage(1)
        }
    }

    BaseContent(
        uiState = uiState,
        clearToastMessage = { viewModel.clearToastMessage() },
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            when (page) {
                0 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.weight(0.6f))

                        BubbleInput(
                            onClick = { navController.navigate(NavRoute.BubbleEdit.createRoute(0)) },
                        )

                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                1 -> {
                    BubbleStorageScreen(
                        navHostController = navController,
                        updateShowBottomNav = updateShowBottomNav,
                        searchResults = uiState.searchResults,
                        searchKeyword = uiState.query,
                        updateViewMode = { flag -> isViewMode = flag },
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(15.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            MyEdisonNavBar(
                onSearchClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                    viewModel.resetSearchResults()
                },
                onBubbleClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                    viewModel.resetSearchResults()
                },
                onStorageClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                    viewModel.resetSearchResults()
                },
                onSearchQuerySubmit = { query ->
                    viewModel.fetchSearchBubbles(query)
                },
                currentPage = pagerState.currentPage,
                query = uiState.query,
                isViewMode = isViewMode,
            )
        }
    }
}