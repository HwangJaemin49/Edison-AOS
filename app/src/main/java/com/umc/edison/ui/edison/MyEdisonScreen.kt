package com.umc.edison.ui.edison


import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.umc.edison.presentation.edison.MyEdisonViewModel
import com.umc.edison.presentation.storage.BubbleStorageViewModel
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

    val context = LocalContext.current
    var backPressedOnce by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val sharedViewModel: BubbleStorageViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsState()

    BackHandler {
        if (backPressedOnce) {
            (context as? Activity)?.finish()
        } else {
            backPressedOnce = true
            coroutineScope.launch {
                delay(2000)
                backPressedOnce = false
            }
        }
    }
    LaunchedEffect(uiState.bubbles) {

        if(uiState.bubbles.isNotEmpty()){
            navController.navigate(NavRoute.BubbleStorage.route) {
                popUpTo(navController.graph.startDestinationId) {saveState=false}
                launchSingleTop=true
            }
        }

        updateShowBottomNav(true)
        PrefsHelper.setMainScreenVisited(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyEdisonNavBar(
            onBubbleClicked = { navController.navigate(NavRoute.BubbleStorage.route) },
            onMyEdisonClicked = { navController.navigate(NavRoute.MyEdison.route) },
            onSearchQuerySubmit = { query -> sharedViewModel.fetchSearchBubbles(query) }
        )

        Spacer(modifier = Modifier.weight(0.6f))


        BubbleInput(
            onClick = { navController.navigate(NavRoute.BubbleEdit.createRoute(0)) },)

        Spacer(modifier = Modifier.weight(1f))


    }
}
