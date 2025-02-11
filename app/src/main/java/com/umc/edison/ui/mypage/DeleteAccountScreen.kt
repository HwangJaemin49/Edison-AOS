package com.umc.edison.ui.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.presentation.mypage.DeleteAccountViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.BackButtonTopBar
import com.umc.edison.ui.components.BasicFullButton
import com.umc.edison.ui.components.CheckBoxButton
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray800

@Composable
fun DeleteAccountScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: DeleteAccountViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        updateShowBottomNav(false)
    }

    LaunchedEffect(uiState.isDeleted) {
        if (uiState.isDeleted) {
            // TODO: Navigate to login screen
        }
    }

    BaseContent(
        uiState = uiState,
        clearToastMessage = { viewModel.clearToastMessage() },
        topBar = {
            BackButtonTopBar(
                title = stringResource(id = R.string.delete_account),
                onBack = {
                    navHostController.popBackStack()
                }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 36.dp),
            verticalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            Text(
                text = stringResource(R.string.delete_account_title),
                style = MaterialTheme.typography.displayMedium,
                color = Gray800,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Text(
                    text = stringResource(R.string.delete_account_agree_1),
                    style = MaterialTheme.typography.headlineLarge,
                    color = Gray800,
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    Text(
                        text = stringResource(R.string.delete_account_agree_2),
                        style = MaterialTheme.typography.headlineLarge,
                        color = Gray800,
                    )

                    Text(
                        text = stringResource(R.string.delete_account_agree_2_detail),
                        style = MaterialTheme.typography.labelLarge,
                        color = Gray600,
                    )
                }

                Text(
                    text = stringResource(R.string.delete_account_agree_3),
                    style = MaterialTheme.typography.headlineLarge,
                    color = Gray800,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CheckBoxButton(
                    selected = uiState.isAgree,
                    onClick = { viewModel.toggleAgree() }
                )

                Text(
                    text = stringResource(R.string.delete_account_agree),
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray800,
                )
            }

            BasicFullButton(
                text = stringResource(id = R.string.delete_account),
                enabled = uiState.isAgree,
                onClick = {
                    viewModel.deleteAccount()
                }
            )
        }
    }
}