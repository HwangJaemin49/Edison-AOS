package com.umc.edison.ui.navigation

sealed class NavRoute(val route: String) {
    data object MyEdison : NavRoute("my-edison")
    data object Space : NavRoute("space")
    data object ArtBoard : NavRoute("art-board")
    data object ArtBoardDetail : NavRoute("art-board/art-letter") // TODO: 경로 art-board/detail/${id} 형식으로 수정
    data object MyPage : NavRoute("my-page")
    data object BubbleStorage: NavRoute("my-edison/bubble-storage")
    data object SpaceLabel : NavRoute("space/labels")


    data object Splash : NavRoute("login/splash")
    data object Login : NavRoute("login")
    data object MakeNickName : NavRoute("login/make-nick-name")
    data object IdentityTest : NavRoute("login/identity-test")
    data object TermsOfUse : NavRoute("login/terms-of-use")

    data class LabelDetail(val id: Int) : NavRoute("${SpaceLabel.route}/labels/${id}") {
        companion object {
            fun createRoute(labelId: Int): String = "${SpaceLabel.route}/labels/${labelId}"
        }
    }

    data object ProfileEdit: NavRoute("my-page/profile-edit")
    data object Menu : NavRoute("my-page/menu")
    data object Trash : NavRoute("my-page/trash")
    data object AccountManagement : NavRoute("my-page/account-management")

    data object ScrapBoard : NavRoute("my-page/scrap-board")
    data object ScrapBoardDetail : NavRoute("my-page/scrap-board-detail")

    data class BubbleEdit(val id: Int = 0) : NavRoute("${MyEdison.route}/bubbles/${id}") {
        companion object {
            fun createRoute(bubbleId: Int): String = "${MyEdison.route}/bubbles/${bubbleId}"
        }
    }

    data class IdentityEdit(val id: Int) : NavRoute("${MyPage.route}/identity/${id}") {
        companion object {
            fun createRoute(identityId: Int): String = "${MyPage.route}/identity/${identityId}"
        }
    }

    data class InterestEdit(val id: Int) : NavRoute("${MyPage.route}/interest/${id}") {
        companion object {
            fun createRoute(interestId: Int): String = "${MyPage.route}/interest/${interestId}"
        }
    }
}