package com.umc.edison.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.umc.edison.presentation.sync.SyncTrigger
import com.umc.edison.ui.components.BubbleInput
import com.umc.edison.ui.components.MyEdisonNavBar
import com.umc.edison.ui.navigation.BottomNavigation
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.navigation.NavigationGraph
import com.umc.edison.ui.theme.EdisonTheme
import com.umc.edison.ui.theme.White000
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var syncTrigger: SyncTrigger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        syncTrigger = SyncTrigger(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            EdisonTheme {
                MainScreen()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        syncTrigger.triggerSync()
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var showInputBubble by remember { mutableStateOf(false) }
    var showBottomNav by remember { mutableStateOf(true) }
    val currentBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(initial = null)
    val currentRoute = currentBackStackEntry?.destination?.route

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
            Column(
                modifier = Modifier
                    .background(White000)
            ) {
                if (currentRoute?.startsWith(NavRoute.MyEdison.route) == true && showBottomNav) {
                    Box(
                        modifier = Modifier.padding(vertical = 12.dp)
                    ){
                        MyEdisonNavBar(
                            onProfileClicked = { /* TODO: 프로필 클릭 이벤트 */ },
                            onCompassClicked = { /* TODO: 컴퍼스 클릭 이벤트 */ },
                        )
                    }
                }

                NavigationGraph(navController, updateShowBottomNav = { flag -> showBottomNav = flag })
            }

            if (showInputBubble) {
                BubbleInput(
                    onClick = {
                        showInputBubble = false
                        navController.navigate(NavRoute.BubbleEdit.createRoute(0))
                    },
                    isBlur = true,
                    onBackScreenClick = { showInputBubble = false }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    EdisonTheme {
        MainScreen()
    }
}