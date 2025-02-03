package com.umc.edison.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.presentation.mypage.InterestEditViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.BackButtonTopBar
import com.umc.edison.ui.components.GrayColumnContainer
import com.umc.edison.ui.components.KeywordChip
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun InterestEditScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: InterestEditViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        updateShowBottomNav(false)
    }

    Scaffold(
        topBar = {
            BackButtonTopBar(
                title = "Identity 고르기",
                onBack = {
                    viewModel.updateIdentity()
                    navHostController.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White000)
                .padding(innerPadding)
        ) {
            InterestContent(viewModel)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun InterestContent(
    viewModel: InterestEditViewModel
) {

    val uiState by viewModel.uiState.collectAsState()

    BaseContent(
        uiState = uiState,
        onDismiss = { viewModel.clearToastMessage() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(
                    state = rememberScrollState()
                )
                .padding(start = 24.dp, top = 36.dp, end = 24.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = uiState.interest.question,
                style = MaterialTheme.typography.displayLarge,
                color = Gray800,
                softWrap = true,
            )

            Text(
                text = uiState.interest.questionTip,
                style = MaterialTheme.typography.titleSmall,
                color = Gray500,
            )

            GrayColumnContainer(
                paddingHorizontal = 24.dp,
                paddingVertical = 18.dp,
                space = 20.dp
            ) {
                Text(
                    text = uiState.interest.descriptionFirst,
                    style = MaterialTheme.typography.titleMedium,
                    color = Gray800
                )

                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.interest.selectedKeywords.forEach { keyword ->
                        KeywordChip(
                            keyword = keyword.name,
                            isSelected = true,
                            onClick = {}
                        )
                    }
                }

                uiState.interest.descriptionSecond?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium,
                        color = Gray800,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "선택하기",
                    style = MaterialTheme.typography.titleMedium,
                    color = Gray800,
                )

                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.interest.options.forEach { keyword ->
                        KeywordChip(
                            keyword = keyword.name,
                            isSelected = uiState.interest.selectedKeywords.contains(keyword),
                            onClick = { viewModel.toggleKeyword(keyword) }
                        )
                    }
                }
            }
        }
    }
}
