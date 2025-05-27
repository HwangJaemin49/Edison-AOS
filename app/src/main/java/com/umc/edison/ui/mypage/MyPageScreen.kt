package com.umc.edison.ui.mypage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.umc.edison.R
import com.umc.edison.presentation.model.ArtLetterCategoryModel
import com.umc.edison.presentation.model.IdentityModel
import com.umc.edison.presentation.model.toIndex
import com.umc.edison.presentation.mypage.MyPageState
import com.umc.edison.presentation.mypage.MyPageViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.NeedLoginScreen
import com.umc.edison.ui.components.ArtLetterCategoryContent
import com.umc.edison.ui.components.GrayColumnContainer
import com.umc.edison.ui.components.GridLayout
import com.umc.edison.ui.components.HamburgerMenu
import com.umc.edison.ui.components.WhiteContainerItem
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray200
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun MyPageScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val baseState by viewModel.baseState.collectAsState()

    LaunchedEffect(Unit) {
        updateShowBottomNav(true)
        viewModel.fetchLoginState()
    }

    BackHandler {
        navHostController.navigate(NavRoute.MyEdison.route)
    }

    BaseContent(
        baseState = baseState,
        topBar = {
            HamburgerMenu(
                onClick = { navHostController.navigate(NavRoute.Menu.route) },
                alignment = Alignment.End
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = if (uiState.isLoggedIn) White000 else White000.copy(alpha = 0.5f)
                )
                .blur(if (uiState.isLoggedIn) 0.dp else 10.dp),
        ) {
            MyPageContent(navHostController, uiState)
        }

        if (!uiState.isLoggedIn) {
            NeedLoginScreen(
                navHostController = navHostController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp),
            )
        }
    }
}

@Composable
private fun MyPageContent(
    navHostController: NavHostController,
    uiState: MyPageState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(
                state = rememberScrollState(),
                enabled = uiState.isLoggedIn
            )
            .padding(start = 24.dp, end = 24.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ProfileInfo(
            imageUrl = uiState.user.profileImage,
            name = uiState.user.nickname ?: "",
            navHostController = navHostController,
        )

        IdentityTestResultContainer(
            identities = uiState.identities,
            onItemClick = { id ->
                navHostController.navigate(
                    NavRoute.IdentityEdit.createRoute(id)
                )
            }
        )

        InterestResultContainer(
            interest = uiState.interest,
            onItemClick = { id ->
                navHostController.navigate(
                    NavRoute.IdentityEdit.createRoute(id)
                )
            }
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(4.dp)
        )

        ArtLetterScrap(
            scrapItems = uiState.myArtLetterCategories,
            onClick = { navHostController.navigate(NavRoute.ScrapBoard.route) }
        )
    }
}

@Composable
private fun ProfileInfo(
    imageUrl: String?,
    name: String,
    navHostController: NavHostController,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { navHostController.navigate(NavRoute.ProfileEdit.route) },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Gray100)
        ) {
            AsyncImage(
                model = imageUrl ?: R.drawable.ic_profile_default_small,
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            color = Gray800
        )
    }
}

@Composable
private fun IdentityTestResultContainer(
    identities: List<IdentityModel>,
    onItemClick: (Int) -> Unit,
) {
    GrayColumnContainer(
        padding = 12.dp,
        title = stringResource(R.string.identity),
    ) {
        identities.forEach {
            WhiteContainerItem(
                title = it.question,
                description = it.selectedKeywords.joinToString { keyword -> keyword.name },
                onClick = { onItemClick(it.category.toIndex()) }
            )
        }
    }
}

@Composable
private fun InterestResultContainer(
    interest: IdentityModel,
    onItemClick: (Int) -> Unit,
) {
    GrayColumnContainer(
        padding = 12.dp,
        title = stringResource(R.string.my_interest),
    ) {
        WhiteContainerItem(
            title = interest.question,
            description = interest.selectedKeywords.joinToString { keyword -> keyword.name },
            onClick = { onItemClick(interest.category.toIndex()) }
        )
    }
}

@Composable
private fun ArtLetterScrap(
    scrapItems: List<ArtLetterCategoryModel>,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(White000)
            .clickable(
                enabled = scrapItems.isNotEmpty(),
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .border(1.dp, Gray200, RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.artletter_capital),
                style = MaterialTheme.typography.titleMedium,
                color = Gray800,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_chevron_right),
                contentDescription = "more",
                modifier = Modifier.size(16.dp),
                tint = Gray800
            )
        }

        if (scrapItems.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_bin),
                    contentDescription = "bin",
                    modifier = Modifier.size(48.dp),
                    tint = Gray500
                )

                Text(
                    text = stringResource(R.string.empty_scrap),
                    style = MaterialTheme.typography.titleSmall,
                    color = Gray600
                )
            }
        } else {
            GridLayout(
                columns = 2,
                items = scrapItems,
            ) {
                ArtLetterCategoryContent(
                    category = it as ArtLetterCategoryModel,
                    onCategoryClick = {}
                )
            }
        }
    }
}
