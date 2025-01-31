package com.umc.edison.ui.navigation

sealed class NavRoute(val route: String) {
    data object MyEdison : NavRoute("my-edison")
    data object Space : NavRoute("space")
    data object ArtBoard : NavRoute("art-board")
    data object MyPage : NavRoute("my-page")

    data object BubbleStorage: NavRoute("my-edison/bubble-storage")
    data object SpaceLabel : NavRoute("space/labels")

    data object ProfileEdit: NavRoute("my-page/profile-edit")
    data object Menu : NavRoute("my-page/menu")
    data object Trash : NavRoute("my-page/trash")
    data object AccountManagement : NavRoute("my-page/account-management")

    data class LabelDetail(val id: Int) : NavRoute("${SpaceLabel.route}/${id}") {
        companion object {
            fun createRoute(labelId: Int): String = "${SpaceLabel.route}/${labelId}"
        }
    }

    data class BubbleEdit(val id: Int = 0) : NavRoute("${MyEdison.route}/bubbles/${id}") {
        companion object {
            fun createRoute(bubbleId: Int): String = "${MyEdison.route}/bubbles/${bubbleId}"
        }
    }
}