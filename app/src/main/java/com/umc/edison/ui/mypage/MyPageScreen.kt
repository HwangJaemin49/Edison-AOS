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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.umc.edison.R
import com.umc.edison.ui.theme.EdisonTheme
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
        topBar = { HamburgerMenu() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            MyPageDefaultContent()
        }
    }

}

@Composable
private fun HamburgerMenu() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White000)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_hamburger),
            contentDescription = "Hamburger Menu",
            modifier = Modifier
                .size(24.dp)
                .padding(16.dp),
            tint = Gray800
        )
    }
}

@Composable
private fun MyPageDefaultContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White000)
            .padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ProfileInfo()

        GrayContainerSection(
            title = "Identity",
            items = listOf("나를 설명하는 단어를 골라주세요!", "지금 혹은 미래의 나는 어떤 필드에 있나요?", "나에게 가장 영감을 주는 환경은?")
        )

        GrayContainerSection(
            title = "나의 관심사",
            items = listOf("나의 상상력을 자극하는 분야는?")
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(16.dp)
        )

        ArtLetterScrap()
    }
}

@Composable
private fun ProfileInfo() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        AsyncImage(
            model = R.drawable.ic_profile_default_small,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Gray300), // TODO: 임시 보여지는 용
        )

        Text(
            text = "Name",
            style = MaterialTheme.typography.titleMedium,
            color = Gray800
        )
    }
}

@Composable
private fun GrayContainerSection(
    title: String,
    items: List<String>
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
                title = it,
                keyword = listOf("Android", "iOS", "Web", "UI/UX")
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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(6) {
                ArtLetterContent()
            }
        }
    }
}

@Composable
private fun ArtLetterContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = R.drawable.ic_profile_default_small,
            contentDescription = "ArtLetter Image",
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Gray300), // TODO: 임시 보여지는 용
        )

        Text(
            text = "현대미술",
            style = MaterialTheme.typography.titleSmall,
            color = Gray800
        )
    }


}

@Preview(showBackground = true)
@Composable
fun MyPageScreenPreview() {
    EdisonTheme {
        MyPageDefaultContent()
    }
}
