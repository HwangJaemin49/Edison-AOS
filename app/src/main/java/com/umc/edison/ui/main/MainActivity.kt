package com.umc.edison.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.umc.edison.presentation.sync.SyncTrigger
import com.umc.edison.ui.components.BubbleInput
import com.umc.edison.ui.navigation.BottomNavigation
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.navigation.NavigationGraph
import com.umc.edison.ui.theme.EdisonTheme
import dagger.hilt.android.AndroidEntryPoint
import io.branch.referral.Branch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var syncTrigger: SyncTrigger
    private lateinit var navController: NavHostController

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        syncTrigger = SyncTrigger(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            navController = rememberNavController()
            EdisonTheme {
                MainScreen(navController = navController)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        Branch.sessionBuilder(this)
            .withCallback { referringParams, error ->
                if (error == null) {
                    val artLetterId = referringParams?.optString("artLetterId")
                    if (!artLetterId.isNullOrBlank()) {
                        Log.d("BranchDeeplink", "Received artLetterId: $artLetterId")
                        // 딥링크로 이동
                        navController.navigate(NavRoute.ArtLetterDetail.createRoute(artLetterId))
                    }
                } else {
                    Log.e("BranchDeeplink", "Branch error: ${error.message}")
                }
            }
            .withData(this.intent?.data)
            .init()
    }

    override fun onDestroy() {
        super.onDestroy()
        syncTrigger.triggerSync()
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MainScreen(navController: NavHostController) {
    var showInputBubble by remember { mutableStateOf(false) }
    var showBottomNav by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                BottomNavigation(
                    navController = navController,
                    onBubbleClick = { showInputBubble = !showInputBubble }
                )
            }
        },
        contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.systemBarsPadding()
    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(
                navHostController = navController,
                updateShowBottomNav = { flag -> showBottomNav = flag }
            )

            if (showInputBubble) {
                BubbleInput(
                    onClick = {
                        showInputBubble = false
                        navController.navigate(NavRoute.BubbleEdit.createRoute(null))
                    },
                    isBlur = true,
                    onBackScreenClick = { showInputBubble = false }
                )
                BackHandler {
                    showInputBubble = false
                }
            }
        }
    }
}
