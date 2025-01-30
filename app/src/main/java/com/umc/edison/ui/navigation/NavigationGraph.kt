package com.umc.edison.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.umc.edison.ui.artboard.ArtBoardScreen
import com.umc.edison.ui.bubblestorage.BubbleStorageScreen
import com.umc.edison.ui.my_edison.MyEdisonScreen
import com.umc.edison.ui.mypage.MyPageScreen
import com.umc.edison.ui.space.BubbleSpaceScreen
import com.umc.edison.ui.label.LabelDetailScreen

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit
) {
    NavHost(navHostController, startDestination = NavRoute.MyEdison.route) {
        // bottom navigation
        composable(NavRoute.MyEdison.route) {
            MyEdisonScreen(navHostController)
        }
        composable(NavRoute.Space.route) {
            BubbleSpaceScreen(navHostController)
        }
        composable(NavRoute.ArtBoard.route) {
            ArtBoardScreen(navHostController)
        }
        composable(NavRoute.MyPage.route) {
            MyPageScreen(navHostController)
        }

        composable(NavRoute.BubbleStorage.route) {
            BubbleStorageScreen(navHostController, updateShowBottomNav)
        }

        // space screen
        composable(
            route = "${NavRoute.SpaceLabel.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            LabelDetailScreen(navHostController, updateShowBottomNav)
        }
    }
}