package com.umc.edison.ui.edison


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.umc.edison.ui.components.BubbleInput
import com.umc.edison.ui.components.MyEdisonNavBar
import com.umc.edison.ui.navigation.NavRoute

@Composable
fun MyEdisonScreen(
    navController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit
) {
    LaunchedEffect(Unit) { updateShowBottomNav(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MyEdisonNavBar(
            onProfileClicked = { /* */ },
            onCompassClicked = { /* */ }
        )

        Spacer(modifier = Modifier.weight(0.6f))

        BubbleInput(
            onClick = { navController.navigate(NavRoute.BubbleEdit.createRoute(0)) },
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}
