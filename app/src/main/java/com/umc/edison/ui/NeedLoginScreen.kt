package com.umc.edison.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.ui.components.PopUpMulti
import com.umc.edison.ui.navigation.NavRoute

@Composable
fun NeedLoginScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        PopUpMulti(
            title = stringResource(R.string.login_require_title),
            detail = stringResource(R.string.login_require_detail),
            hintText = stringResource(R.string.login_require_title_hint),
            buttonText = stringResource(R.string.google_login),
            onButtonClick = {
                navHostController.navigate(NavRoute.Login.route) {
                    popUpTo(NavRoute.Login.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}