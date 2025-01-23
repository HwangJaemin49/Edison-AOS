package com.umc.edison.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.umc.edison.R
import com.umc.edison.ui.theme.EdisonTheme
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
        title = R.string.artletter,
        icon = R.drawable.ic_art_letter,
        selectedIcon = R.drawable.ic_art_letter_selected,
        route = NavRoute.ArtBoard.route
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
    onBubbleClick: () -> Unit = {}
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

        items.forEach { navItem ->
            if (navItem == BottomNavItem.Bubble) {
                NavigationBarItem(
                    icon = {
                        Image(
                            painter = painterResource(id = if (currentRoute == navItem.route) navItem.selectedIcon else navItem.icon),
                            contentDescription = navItem.route
                        )
                    },
                    selected = false,
                    onClick = {
                        onBubbleClick()
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Gray800,
                        unselectedIconColor = Gray400,
                        indicatorColor = White000
                    )
                )
            } else {
                NavigationBarItem(
                    icon = {
                        Image(
                            painter = painterResource(id = if (currentRoute == navItem.route) navItem.selectedIcon else navItem.icon),
                            contentDescription = navItem.route
                        )
                    },
                    label = {
                        Text(
                            text = LocalContext.current.getString(navItem.title),
                            color = if (currentRoute == navItem.route) Gray800 else Gray400,
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route!!)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Gray800,
                        unselectedIconColor = Gray400,
                        indicatorColor = White000
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    EdisonTheme {
        BottomNavigation(navController = NavHostController(LocalContext.current))
    }
}
