package com.umc.edison.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.umc.edison.presentation.ToastManager
import com.umc.edison.presentation.sync.SyncTrigger
import com.umc.edison.ui.ToastScreen
import com.umc.edison.ui.components.BubbleInput
import com.umc.edison.ui.navigation.BottomNavigation
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.navigation.NavigationGraph
import com.umc.edison.ui.theme.EdisonTheme
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import io.branch.referral.Branch
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var syncTrigger: SyncTrigger
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("AppStart", "EdisonApplication started")
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
                        navController.navigate(NavRoute.ArtLetterDetail.createRoute(artLetterId.toInt()))
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

@Composable
fun MainScreen(navController: NavHostController) {
    val toastManager = rememberToastManager()
    var toastMessage by remember { mutableStateOf<String?>(null) }

    var showInputBubble by remember { mutableStateOf(false) }
    var showBottomNav by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        toastManager.toastMessage.collect { message ->
            toastMessage = message
            delay(2000)
            toastMessage = null
        }
    }

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

            toastMessage?.let {
                ToastScreen(
                    message = it,
                    onDismiss = { toastMessage = null }
                )
            }
        }
    }
}

@Composable
fun rememberToastManager(): ToastManager {
    val context = LocalContext.current
    val entry = remember(context) {
        EntryPointAccessors.fromApplication(context, ToastManagerEntryPoint::class.java)
    }
    return entry.toastManager()
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ToastManagerEntryPoint {
    fun toastManager(): ToastManager
}
