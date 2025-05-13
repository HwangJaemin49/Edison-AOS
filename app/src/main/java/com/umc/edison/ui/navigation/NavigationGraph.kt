package com.umc.edison.ui.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.umc.edison.ui.artboard.ArtLetterHomeScreen
import com.umc.edison.ui.artboard.ArtLetterDetailScreen
import com.umc.edison.ui.artboard.ArtLetterSearchScreen
import com.umc.edison.ui.edison.MyEdisonScreen
import com.umc.edison.ui.mypage.MyPageScreen
import com.umc.edison.ui.space.BubbleSpaceScreen
import com.umc.edison.ui.edison.BubbleInputScreen
import com.umc.edison.ui.label.LabelDetailScreen
import com.umc.edison.ui.login.IdentityTestScreen
import com.umc.edison.ui.login.LoginScreen
import com.umc.edison.ui.login.MakeNickNameScreen
import com.umc.edison.ui.login.SplashScreen
import com.umc.edison.ui.login.TermsOfUseScreen
import com.umc.edison.ui.mypage.AccountManagementScreen
import com.umc.edison.ui.mypage.DeleteAccountScreen
import com.umc.edison.ui.mypage.EditProfileScreen
import com.umc.edison.ui.mypage.IdentityEditScreen
import com.umc.edison.ui.mypage.InterestEditScreen
import com.umc.edison.ui.mypage.MenuScreen
import com.umc.edison.ui.mypage.ScrapBoardDetailScreen
import com.umc.edison.ui.mypage.ScrapBoardScreen
import com.umc.edison.ui.mypage.TrashScreen

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit
) {
    NavHost(navHostController, startDestination = NavRoute.Splash.route) {
        composable(NavRoute.MyEdison.route) {
            MyEdisonScreen(navHostController, updateShowBottomNav)
        }

        composable(NavRoute.Space.route) {
            BubbleSpaceScreen(navHostController, updateShowBottomNav)
        }

        composable(NavRoute.ArtLetter.route) {
            ArtLetterHomeScreen(navHostController)
        }

        composable(NavRoute.ArtLetterSearch.route) {
            ArtLetterSearchScreen(navHostController)
        }

        // ✅ 수정된 아트레터 상세 경로
        composable(
            route = "${NavRoute.ArtLetter.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val idStr = backStackEntry.arguments?.getString("id")
            if (idStr != null) {
                ArtLetterDetailScreen(
                    artLetterId = idStr,
                    navHostController = navHostController
                )
            } else {
                Log.e("NavGraph", "Invalid artLetterId in deeplink: $idStr")
            }
        }

        composable(NavRoute.MyPage.route) {
            MyPageScreen(navHostController, updateShowBottomNav)
        }

        composable(NavRoute.ProfileEdit.route) {
            EditProfileScreen(navHostController, updateShowBottomNav)
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

        composable(NavRoute.DeleteAccount.route) {
            DeleteAccountScreen(navHostController, updateShowBottomNav)
        }

        composable(NavRoute.ScrapBoard.route) {
            ScrapBoardScreen(navHostController)
        }

        composable(
            route = "${NavRoute.ScrapBoard.route}/categories/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) {
            ScrapBoardDetailScreen(navHostController)
        }

        composable(NavRoute.Login.route) {
            LoginScreen(navHostController, updateShowBottomNav)
        }

        composable(NavRoute.Splash.route) {
            SplashScreen(navHostController, updateShowBottomNav)
        }

        composable(NavRoute.MakeNickName.route) {
            MakeNickNameScreen(navHostController, updateShowBottomNav)
        }

        composable(NavRoute.IdentityTest.route) {
            IdentityTestScreen(navHostController, updateShowBottomNav)
        }

        composable(NavRoute.TermsOfUse.route) {
            TermsOfUseScreen(navHostController, updateShowBottomNav)
        }

        composable(
            route = "${NavRoute.MyPage.route}/identity/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            IdentityEditScreen(navHostController, updateShowBottomNav)
        }

        composable(
            route = "${NavRoute.MyPage.route}/interest/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            InterestEditScreen(navHostController, updateShowBottomNav)
        }

        composable(
            route = "${NavRoute.SpaceLabel.route}/labels/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            LabelDetailScreen(navHostController, updateShowBottomNav)
        }

        composable(
            route = "${NavRoute.MyEdison.route}/bubbles/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            BubbleInputScreen(navHostController, updateShowBottomNav)
        }
    }
}
