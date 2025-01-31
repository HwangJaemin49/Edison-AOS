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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.umc.edison.R
import com.umc.edison.presentation.model.KeywordModel
import com.umc.edison.presentation.mypage.MyPageViewModel
import com.umc.edison.ui.components.PopUpMulti
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray200
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun MyPageScreen(
    navHostController: NavHostController,
) {
    Scaffold(
        topBar = { HamburgerMenu(
            onClick = { navHostController.navigate(NavRoute.Menu.route) }
        ) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            MyPageContent(navHostController)
        }
    }
}

@Composable
private fun HamburgerMenu(
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White000)
            .padding(start = 24.dp, top = 12.dp, end = 24.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.align(Alignment.End),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_hamburger),
                contentDescription = "Hamburger Menu",
                tint = Gray800
            )
        }
    }
}

@Composable
private fun MyPageContent(
    navHostController: NavHostController,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(White000)
            .padding(start = 24.dp, end = 24.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ProfileInfo(
            imageUrl = uiState.profileImage,
            name = uiState.nickname,
            navHostController = navHostController,
        )

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .let {
                        if (!uiState.isLoggedIn) it.blur(5.dp) else it
                    }
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                GrayContainerSection(
                    title = "Identity",
                    items = uiState.identity.map { Pair(it.category.question, it.keywords) }
                )

                GrayContainerSection(
                    title = "나의 관심사",
                    items = listOf(Pair(uiState.interest.category.question, uiState.interest.keywords))
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(16.dp)
                )

                ArtLetterScrap()
            }

            if (!uiState.isLoggedIn) {
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
        )

        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            color = Gray800
        )
    }
}

@Composable
private fun GrayContainerSection(
    title: String,
    items: List<Pair<String, List<KeywordModel>>>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Gray100)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Gray800
        )

        items.forEach {
            WhiteContainerItem(
                title = it.first,
                keyword = it.second.map { keyword -> keyword.name }
            )
        }
    }
}

@Composable
private fun WhiteContainerItem(
    title: String,
    keyword: List<String>,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(White000)
            .padding(start = 16.dp, top = 12.dp, end = 10.dp, bottom = 12.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                color = Gray800
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                keyword.forEach {
                    Text(
                        text = if (it == keyword.last()) it else "$it, ",
                        style = MaterialTheme.typography.labelSmall,
                        color = Gray800
                    )
                }
            }
        }

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_chevron_right),
            contentDescription = "more",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickable { onClick() }
                .size(16.dp),
            tint = Gray800
        )
    }
}

@Composable
private fun ArtLetterScrap() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(White000)
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

        ArtLetterGrid()
    }
}

@Composable
private fun ArtLetterGrid() {
    val items = List(6) { it }
    val columns = 2

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.chunked(columns).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { _ ->
                    Box(modifier = Modifier.weight(1f)) {
                        ArtLetterContent()
                    }
                }

                if (rowItems.size < columns) {
                    Spacer(modifier = Modifier.weight(columns - rowItems.size.toFloat()))
                }
            }
        }
    }
}


@Composable
private fun ArtLetterContent(
    thumbnail: String? = null,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Gray300),
            contentAlignment = Alignment.Center
        ) {
            if (thumbnail != null) {
                AsyncImage(
                    model = thumbnail,
                    contentDescription = "ArtLetter Image",
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }

        Text(
            text = "현대미술",
            style = MaterialTheme.typography.titleSmall,
            color = Gray800
        )
    }
}
