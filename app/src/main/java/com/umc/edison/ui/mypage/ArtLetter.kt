package com.umc.edison.ui.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.umc.edison.presentation.model.ArtLetterCategoryModel
import com.umc.edison.ui.theme.Gray800

@Composable
fun ArtLetterCategoryContent(
    category: ArtLetterCategoryModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = category.mainImage,
            contentDescription = "ArtLetter Category Image",
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        )

        Text(
            text = category.title,
            style = MaterialTheme.typography.titleSmall,
            color = Gray800
        )
    }
}
