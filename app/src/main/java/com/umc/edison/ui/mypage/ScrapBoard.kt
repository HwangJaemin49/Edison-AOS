package com.umc.edison.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray800

@Composable
fun ScrapBoardGrid(
    spaceSize: Int = 12
) {
    val items = List(6) { it }
    val columns = 2

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(spaceSize.dp)
    ) {
        items.chunked(columns).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { _ ->
                    Box(modifier = Modifier.weight(1f)) {
                        ScrapBoardContent()
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
fun ScrapBoardContent(
    thumbnail: String? = null,
    categoryName: String = "현대미술"
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
                    contentDescription = "Scrap Category Image",
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }

        Text(
            text = categoryName,
            style = MaterialTheme.typography.titleSmall,
            color = Gray800
        )
    }
}
