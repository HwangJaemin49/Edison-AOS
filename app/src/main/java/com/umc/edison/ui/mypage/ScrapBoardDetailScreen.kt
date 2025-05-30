package com.umc.edison.ui.mypage

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.presentation.model.ArtLetterPreviewModel
import com.umc.edison.presentation.mypage.ScrapBoardDetailViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.ArtLetterCard
import com.umc.edison.ui.components.BackButtonTopBar
import com.umc.edison.ui.components.GridLayout
import com.umc.edison.ui.navigation.NavRoute

@Composable
fun ScrapBoardDetailScreen(
    navHostController: NavHostController,
    viewModel: ScrapBoardDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val baseState by viewModel.baseState.collectAsState()

    BaseContent(
        baseState = baseState,
        topBar = {
            BackButtonTopBar(
                title = uiState.categoryName,
                onBack = { navHostController.popBackStack() }
            )
        }
    ) {
        val scrollState = rememberScrollState()
        GridLayout(
            columns = 2,
            items = uiState.artLetters,
            modifier = Modifier
                .padding(24.dp)
                .verticalScroll(scrollState)
        ) {
            ArtLetterCard(
                artLetter = it as ArtLetterPreviewModel,
                onArtLetterClick = { artLetter ->
                    navHostController.navigate(
                        NavRoute.ArtLetterDetail.createRoute(artLetter.artLetterId)
                    )
                },
                onBookmarkClick = { artLetter ->
                    viewModel.toggleScrap(artLetter.artLetterId)
                }
            )
        }
    }
}