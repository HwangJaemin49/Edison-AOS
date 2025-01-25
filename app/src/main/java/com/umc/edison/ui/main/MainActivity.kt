package com.umc.edison.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.umc.edison.ui.navigation.BottomNavigation
import com.umc.edison.ui.navigation.NavigationGraph
import com.umc.edison.ui.theme.EdisonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            EdisonTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry).value?.destination?.route

    val noBottomNavRoutes = listOf("bubble_input_screen")

    Scaffold(
        bottomBar = {
            if (currentRoute !in noBottomNavRoutes) {
                BottomNavigation(navController = navController)
            }
        },
        contentWindowInsets = WindowInsets(
            left = 0.dp,
            top = 25.dp,
            right = 0.dp,
            bottom = 0.dp
        )

    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(navController)
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