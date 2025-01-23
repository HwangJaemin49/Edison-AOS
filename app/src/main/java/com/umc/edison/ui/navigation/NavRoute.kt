package com.umc.edison.ui.navigation

sealed class NavRoute(val route: String) {
    data object MyEdison : NavRoute("my-edison")
    data object Space : NavRoute("space")
    data object ArtBoard : NavRoute("art-board")
    data object MyPage : NavRoute("my-page")

    data object SpaceLabel : NavRoute("space/labels")

    data class LabelDetail(val id: Int) : NavRoute("${SpaceLabel.route}/${id}") {
        companion object {
            fun createRoute(labelId: Int): String = "${SpaceLabel.route}/${labelId}"
        }
    }
}