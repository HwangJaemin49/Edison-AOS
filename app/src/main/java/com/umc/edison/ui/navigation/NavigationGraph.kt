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
import com.umc.edison.ui.mypage.AccountManagementScreen
import com.umc.edison.ui.mypage.EditProfileScreen
import com.umc.edison.ui.mypage.MenuScreen
import com.umc.edison.ui.mypage.ScrapBoardScreen
import com.umc.edison.ui.mypage.TrashScreen

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

        composable(NavRoute.BubbleStorage.route) {
            BubbleStorageScreen(navHostController, updateShowBottomNav)
        }

        composable(NavRoute.Space.route) {
            BubbleSpaceScreen(navHostController)
        }

        composable(
            route = "${NavRoute.SpaceLabel.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            LabelDetailScreen(navHostController, updateShowBottomNav)
        }

        composable(NavRoute.ArtBoard.route) {
            ArtBoardScreen(navHostController)
        }

        composable(NavRoute.MyPage.route) {
            MyPageScreen(navHostController, updateShowBottomNav)
        }

        composable(NavRoute.ProfileEdit.route) {
            EditProfileScreen(navHostController)
        }

        composable(NavRoute.Menu.route) {
            MenuScreen(navHostController, updateShowBottomNav)
        }

        composable(NavRoute.Trash.route) {
            TrashScreen(navHostController, updateShowBottomNav)
        }

        composable(NavRoute.AccountManagement.route) {
            AccountManagementScreen(navHostController, updateShowBottomNav)
        }

        composable(NavRoute.ScrapBoard.route) {
            ScrapBoardScreen(navHostController)
        }
    }
}