package com.umc.edison.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.umc.edison.R
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray400
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

sealed class BottomNavItem(
    val title: Int,
    val icon: Int,
    val selectedIcon: Int,
    val route: String? = null
) {
    data object MyEdison : BottomNavItem(
        title = R.string.my_edison,
        icon = R.drawable.ic_my_edison,
        selectedIcon = R.drawable.ic_my_edison_selected,
        route = NavRoute.MyEdison.route
    )

    data object Space : BottomNavItem(
        title = R.string.space,
        icon = R.drawable.ic_space,
        selectedIcon = R.drawable.ic_space_selected,
        route = NavRoute.Space.route
    )

    data object Bubble : BottomNavItem(
        title = R.string.bubble,
        icon = R.drawable.ic_bubble,
        selectedIcon = R.drawable.ic_bubble,
    )

    data object ArtLetter : BottomNavItem(
        title = R.string.artboard,
        icon = R.drawable.ic_art_letter,
        selectedIcon = R.drawable.ic_art_letter_selected,
        route = NavRoute.ArtLetter.route
    )

    data object MyPage : BottomNavItem(
        title = R.string.my,
        icon = R.drawable.ic_my,
        selectedIcon = R.drawable.ic_my_selected,
        route = NavRoute.MyPage.route
    )
}

@Composable
fun BottomNavigation(
    navController: NavHostController,
    setBottomNavPosition: (Int, Offset, IntSize) -> Unit,
    onBubbleClick: () -> Unit = {},
) {
    val items = listOf(
        BottomNavItem.MyEdison,
        BottomNavItem.Space,
        BottomNavItem.Bubble,
        BottomNavItem.ArtLetter,
        BottomNavItem.MyPage
    )

    NavigationBar(
        modifier = Modifier
            .background(color = White000)
            .border(width = 1.dp, color = Gray100)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        containerColor = White000,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEachIndexed { idx, navItem ->
            // 현재 라우트가 세부 화면일 경우 상위 라우트로 매핑
            val isSelected = when (navItem) {
                BottomNavItem.MyEdison -> currentRoute?.startsWith(NavRoute.MyEdison.route) == true
                BottomNavItem.Space -> currentRoute?.startsWith(NavRoute.Space.route) == true
                BottomNavItem.ArtLetter -> currentRoute?.startsWith(NavRoute.ArtLetter.route) == true
                BottomNavItem.MyPage -> currentRoute?.startsWith(NavRoute.MyPage.route) == true
                BottomNavItem.Bubble -> false
            }

            val interactionSource = remember { MutableInteractionSource() }

            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = if (isSelected) navItem.selectedIcon else navItem.icon),
                        contentDescription = navItem.route
                    )
                },
                label = {
                    if (navItem != BottomNavItem.Bubble) {
                        Text(
                            text = LocalContext.current.getString(navItem.title),
                            color = if (isSelected) Gray800 else Gray400,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                selected = isSelected,
                onClick = {
                    if (navItem == BottomNavItem.Bubble) {
                        onBubbleClick()
                    } else {
                        // navController에 쌓여있는 모든 라우트를 제거하고 해당 라우트로 이동
                        navController.navigate(navItem.route!!) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = false
                            }
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = White000
                ),
                modifier = Modifier
                    .indication(interactionSource, null)
                    .onGloballyPositioned { coordinates ->
                        if (idx == 2) {
                            setBottomNavPosition(
                                idx,
                                coordinates.positionOnScreen() + Offset(
                                    x = 0f,
                                    y = - 10f
                                ),
                                coordinates.size
                            )
                        }
                        else {
                            setBottomNavPosition(
                                idx,
                                coordinates.positionOnScreen(),
                                coordinates.size
                            )
                        }
                    },
            )
        }
    }
}
