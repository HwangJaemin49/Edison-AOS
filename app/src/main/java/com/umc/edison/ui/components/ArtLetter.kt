package com.umc.edison.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.umc.edison.R
import com.umc.edison.presentation.model.ArtLetterCategoryModel
import com.umc.edison.presentation.model.ArtLetterPreviewModel
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray400
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun ArtLetterCategoryContent(
    category: ArtLetterCategoryModel,
    onCategoryClick: (ArtLetterCategoryModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                onClick = { onCategoryClick(category) },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        val imageUrl = category.mainImage ?: ""

        Box(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Gray300)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "ArtLetterPreview Category Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = category.title,
            style = MaterialTheme.typography.titleSmall,
            color = Gray800,
            modifier = Modifier.padding(start = 6.dp)
        )
    }
}

@Composable
fun ArtLetterCard(
    artLetter: ArtLetterPreviewModel,
    onArtLetterClick: (ArtLetterPreviewModel) -> Unit,
    onBookmarkClick: (ArtLetterPreviewModel) -> Unit
) {
    val imageUrl = artLetter.thumbnail

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(228.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Gray400)
            .clickable { onArtLetterClick(artLetter) },
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "ArtLetterPreview Category Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.padding(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_bookmark),
                    contentDescription = "Scrap Icon",
                    tint = if (artLetter.scraped) Color(0xFFFFDE66) else Gray500,
                    modifier = Modifier
                        .size(22.dp)
                        .clickable { onBookmarkClick(artLetter) }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = artLetter.title,
                style = MaterialTheme.typography.headlineLarge,
                color = White000,
            )
        }
    }
}