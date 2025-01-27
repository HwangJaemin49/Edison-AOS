package com.umc.edison.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.umc.edison.presentation.sync.SyncTrigger
import com.umc.edison.ui.components.BubbleInput
import com.umc.edison.ui.navigation.BottomNavigation
import com.umc.edison.ui.navigation.NavigationGraph
import com.umc.edison.ui.theme.EdisonTheme
import com.umc.edison.ui.theme.Gray800
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var syncTrigger: SyncTrigger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        syncTrigger = SyncTrigger(this)
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

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                BottomNavigation(
                    navController = navController,
                    onBubbleClick = { showInputBubble = !showInputBubble }
                )
            }
        }
    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(navController, updateShowBottomNav = { flag -> showBottomNav = flag })

            if (showInputBubble) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Gray800.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    BubbleInput(
                        onClick = { },
                        onSwipeUp = { }
                    )
                }
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