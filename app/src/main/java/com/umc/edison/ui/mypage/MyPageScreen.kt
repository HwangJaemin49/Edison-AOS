package com.umc.edison.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.umc.edison.R
import com.umc.edison.presentation.model.ArtLetterCategoryModel
import com.umc.edison.presentation.model.IdentityModel
import com.umc.edison.presentation.model.InterestModel
import com.umc.edison.presentation.mypage.MyPageViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.GrayColumnContainer
import com.umc.edison.ui.components.GridLayout
import com.umc.edison.ui.components.HamburgerMenu
import com.umc.edison.ui.components.PopUpMulti
import com.umc.edison.ui.components.WhiteContainerItem
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray200
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun MyPageScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        updateShowBottomNav(true)
        viewModel.fetchLoginState()
    }

    Scaffold(
        topBar = {
            HamburgerMenu(
                onClick = { navHostController.navigate(NavRoute.Menu.route) },
                alignment = Alignment.End
            )
        }
    ) { innerPadding ->
        BaseContent(
            uiState = uiState,
            onDismiss = { viewModel.clearError() },
            modifier = Modifier.padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = if (uiState.isLoggedIn) White000 else White000.copy(alpha = 0.5f)
                    )
                    .blur(if (uiState.isLoggedIn) 0.dp else 10.dp),
            ) {
                MyPageContent(navHostController, viewModel)
            }

            if (!uiState.isLoggedIn) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    PopUpMulti(
                        title = "로그인이 필요한 페이지입니다",
                        detail = "로그인으로 더 안전하게 아이디어를 보관하세요!",
                        hintText = "스페이스 자동 시각화 기능 지원\n" +
                                "맞춤형 레터 추천과 북마크 기능 지원",
                        buttonText = "구글 로그인",
                        onButtonClick = { /* TODO: 로그인 화면으로 이동 */ }
                    )
                }
            }
        }
    }
}

@Composable
private fun MyPageContent(
    navHostController: NavHostController,
    viewModel: MyPageViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

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
            name = uiState.user.nickname,
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
                    NavRoute.InterestEdit.createRoute(id)
                )
            }
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(8.dp)
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
            .clickable {
                navHostController.navigate(NavRoute.ProfileEdit.route)
            }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        AsyncImage(
            model = imageUrl ?: R.drawable.ic_profile_default_small,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Gray100)
        )

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
        title = "Identity",
    ) {
        identities.forEach {
            WhiteContainerItem(
                title = it.question,
                description = it.selectedKeywords.joinToString { keyword -> keyword.name },
                onClick = { onItemClick(it.id) }
            )
        }
    }
}

@Composable
private fun InterestResultContainer(
    interest: InterestModel,
    onItemClick: (Int) -> Unit,
) {
    GrayColumnContainer(
        padding = 12.dp,
        title = "나의 관심사",
    ) {
        WhiteContainerItem(
            title = interest.question,
            description = interest.selectedKeywords.joinToString { keyword -> keyword.name },
            onClick = { onItemClick(interest.id) }
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
            .clickable(onClick = onClick)
            .border(1.dp, Gray200, RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "ARTLETTER",
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

        GridLayout(
            columns = 2,
            items = scrapItems,
        ) {
            ArtLetterCategoryContent(it as ArtLetterCategoryModel)
        }
    }
}
