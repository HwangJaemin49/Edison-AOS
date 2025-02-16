package com.umc.edison.ui.artboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.umc.edison.R
import com.umc.edison.presentation.artletter.ArtLetterViewModel
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun ArtLetterDetailScreen(
    navHostController: NavHostController,
    artletterId: Int,
    viewModel: ArtLetterViewModel = hiltViewModel()) {
    LaunchedEffect(artletterId) {
        viewModel.fetchArtLetterDetail(artletterId)
    }
    val artLetterDetail by viewModel.artLetterDetail.collectAsState()

    val isBookmarked = remember { mutableStateOf(false) }
    val isLiked = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(White000)
        ) {
            item {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(472.dp)

                    ) {
                        if (artLetterDetail.artletter.thumbnail.isNullOrBlank()) {
                            Image(
                                painter = painterResource(id = R.drawable.delivery),
                                contentDescription = "Thumbnail Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop // 이미지가 박스 크기에 맞게 조정됨
                            )
                        } else {
                            AsyncImage(
                                model = artLetterDetail.artletter.thumbnail,
                                contentDescription = "Thumbnail Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Icon(
                            painter = painterResource(id = if (isBookmarked.value) R.drawable.ic_artletter_detail_bookmark else R.drawable.ic_artletter_detail_empty_bookmark),
                            contentDescription = "Bookmark",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(16.dp)
                                .size(48.dp)
                                .clickable { isBookmarked.value = !isBookmarked.value }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 작성 시간
                        Box(
                            modifier = Modifier
                                .size(width = 73.dp, height = 32.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Gray300),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_artletter_detail_time),
                                    contentDescription = "Time",
                                    modifier = Modifier.size(20.dp),
                                    tint = Gray600
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = artLetterDetail.artletter.createdAt, color = Gray600, style = MaterialTheme.typography.labelLarge)
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // 좋아요 수
                            Box(
                                modifier = Modifier
                                    .size(width = 72.dp, height = 32.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Gray300),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_artletter_detail_likes),
                                        contentDescription = "Likes",
                                        modifier = Modifier.size(20.dp),
                                        tint = Gray600
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "31", color = Gray600, style = MaterialTheme.typography.labelLarge)
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Gray300),
                                contentAlignment = Alignment.Center
                            ){
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_artletter_detail_share),
                                    contentDescription = "Share",
                                    tint = Gray600,
                                )
                            }
                        }

                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "배달을 바라보는 시선, '딜리버리 탑서의 구' (2022)", color = Gray800, style = MaterialTheme.typography.displayLarge)
                    Spacer(modifier = Modifier.height(18.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(width = 72.dp, height = 32.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Gray100),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "# 영상아트", color = Gray600, style = MaterialTheme.typography.labelLarge)
                        }
                        Box(
                            modifier = Modifier
                                .size(width = 72.dp, height = 32.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Gray100),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "# 영상아트", color = Gray600, style = MaterialTheme.typography.labelLarge)
                        }
                    }
                    Divider(
                        color = Gray300,
                        thickness = 1.dp,
                        modifier = Modifier
                            .width(450.dp)
                            .padding(top = 20.dp, bottom = 20.dp)
                    )

                    Text(text = "<딜리버리 댄서의 구>(단채널 영상, 약 25분)에는, \n" +
                            "가상의 서울을 배경으로 끊임없이 갱신되는 배달 앱의 네비게이션 미로에 갇힌 채 질주하는 여성 배달 라이더가 있다. \n" +
                            "\n" +
                            "본 프로젝트는 긱 이코노미, 플랫폼 노동뿐 아니라 현실 위에 모바일 스크린의 형태로 포개어진 위상학적 미로–앱을 통해 경험하는 세계와 현실 양쪽에 동시에 거주하는 존재의 양태, 가능세계 이론, (현실의 문제이기도 한) 배달 라이더들의 극단적 각성상태, 신체와 시간에 대한 끊임없는 최적화를 요구하는 가속주의적 촉구 등을 담고 있다.",
                        color = Gray800, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(modifier = Modifier.fillMaxWidth(), // Row를 가로로 꽉 채움
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End) {
                        Icon(
                            painter = painterResource(id = if (isLiked.value) R.drawable.ic_artletter_detail_like else R.drawable.ic_artletter_detail_empty_like),
                            contentDescription = "Like",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { isLiked.value = !isLiked.value }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Divider(
                        color = Gray300,
                        thickness = 1.dp,
                        modifier = Modifier
                            .width(450.dp)
                            .padding(top = 20.dp, bottom = 20.dp)
                    )

                    Text(
                        text = "아트레터 더보기",
                        style = MaterialTheme.typography.displayMedium,
                        color = Gray800
                    )
                    Spacer(modifier = Modifier.height(12.dp)) // 텍스트와 리스트 사이 여백

                }
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(48.dp),
                    contentPadding = PaddingValues(start = 16.dp, end = 8.dp)
                ) {
                    items(3) { index ->
                        val isBookmarked = remember { mutableStateOf(false) }
                        Column(
                            modifier = Modifier
                                .width(220.dp)
                                .height(187.dp)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            Box( // 이미지 자리
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Gray300)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.thum_1302041), // 이미지 추가
                                    contentDescription = "Thumbnail",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop // 이미지 크기를 Box에 맞춤
                                ) }

                            Spacer(modifier = Modifier.height(18.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "펠릭스 곤잘레스의 연인",
                                    style = MaterialTheme.typography.displaySmall,
                                    color = Gray800
                                )

                                Icon(
                                    painter = painterResource(id = if (isBookmarked.value) R.drawable.ic_artletter_detail_bookmark else R.drawable.ic_artletter_detail_empty_bookmark),
                                    contentDescription = "Bookmark",
                                    tint = Color.Unspecified,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clickable { isBookmarked.value = !isBookmarked.value }
                                )
                            }

                            Text(
                                text = "#개념미술",
                                style = MaterialTheme.typography.titleMedium,
                                color = Gray800
                            )

                        }
                    }
                }
            }

        }

    }
}
