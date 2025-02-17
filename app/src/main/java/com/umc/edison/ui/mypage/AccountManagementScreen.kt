package com.umc.edison.ui.mypage

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.presentation.mypage.AccountManagementMode
import com.umc.edison.presentation.mypage.AccountManagementState
import com.umc.edison.presentation.mypage.AccountManagementViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.BackButtonTopBar
import com.umc.edison.ui.components.PopUpDecision
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun AccountManagementScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: AccountManagementViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        updateShowBottomNav(false)
    }

    BaseContent(
        uiState = uiState,
        clearToastMessage = { viewModel.clearToastMessage() },
        topBar = {
            BackButtonTopBar(
                title = stringResource(R.string.account_management),
                onBack = {
                    navHostController.popBackStack()
                },
                backgroundColor = if (uiState.mode != AccountManagementMode.NONE) {
                    Color(0xFF3E3E3E).copy(alpha = 0.5f)
                } else White000
            )
        }
    ) {
        AccountManagementContent(viewModel, uiState, navHostController)
    }
}

@Composable
private fun AccountManagementContent(
    viewModel: AccountManagementViewModel,
    uiState: AccountManagementState,
    navHostController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 28.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = if (uiState.isLoggedIn) stringResource(R.string.social_account_success)
                else stringResource(R.string.socail_account_failure),
                style = MaterialTheme.typography.labelLarge,
                color = Gray600,
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.google),
                style = MaterialTheme.typography.bodySmall,
                color = Gray800,
                modifier = Modifier.weight(1f)
            )

            if (uiState.isLoggedIn && uiState.user != null) {
                Text(
                    text = uiState.user.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray600
                )
            } else {
                Text(
                    text = stringResource(R.string.connection_failure),
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray600
                )

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Gray100)
                        .clickable {
                            navHostController.navigate(NavRoute.Login.route) {
                                popUpTo(NavRoute.Login.route) { inclusive = true }
                            }
                        }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.login_btn),
                        style = MaterialTheme.typography.titleSmall,
                        color = Gray800
                    )
                }
            }
        }

        if (uiState.isLoggedIn) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .background(Gray100)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.logout),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.updateMode(AccountManagementMode.LOGOUT) }
                        .padding(vertical = 6.dp),
                )

                Text(
                    text = stringResource(R.string.delete_account),
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray800,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.updateMode(AccountManagementMode.DELETE_ACCOUNT) }
                        .padding(vertical = 6.dp),
                )
            }
        }
    }

    if (uiState.mode != AccountManagementMode.NONE) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF3E3E3E).copy(alpha = 0.5f))
                .clickable { viewModel.updateMode(AccountManagementMode.NONE) },
            contentAlignment = Alignment.Center
        ) {
            if (uiState.mode == AccountManagementMode.DELETE_ACCOUNT) {
                navHostController.navigate(NavRoute.DeleteAccount.route)
            } else if (uiState.mode == AccountManagementMode.LOGOUT) {
                PopUpDecision(
                    question = stringResource(R.string.logout_question),
                    positiveButtonText = stringResource(R.string.logout),
                    negativeButtonText = stringResource(R.string.cancel),
                    onPositiveClick = { viewModel.logOut() },
                    onNegativeClick = { viewModel.updateMode(AccountManagementMode.NONE) }
                )
            }
        }
    }
}
