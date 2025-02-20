package com.umc.edison.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.umc.edison.R
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun GrayColumnContainer(
    padding: Dp,
    title: String? = null,
    space: Dp = 12.dp,
    content: @Composable () -> Unit
) {
    GrayColumnContainer(
        paddingStart = padding,
        paddingEnd = padding,
        paddingTop = padding,
        paddingBottom = padding,
        title = title,
        space = space,
        content = content
    )
}

@Composable
fun GrayColumnContainer(
    paddingHorizontal: Dp,
    paddingVertical: Dp,
    title: String? = null,
    space: Dp = 12.dp,
    content: @Composable () -> Unit
) {
    GrayColumnContainer(
        paddingStart = paddingHorizontal,
        paddingEnd = paddingHorizontal,
        paddingTop = paddingVertical,
        paddingBottom = paddingVertical,
        title = title,
        space = space,
        content = content
    )
}

@Composable
fun GrayColumnContainer(
    paddingStart: Dp,
    paddingEnd: Dp,
    paddingTop: Dp,
    paddingBottom: Dp,
    title: String? = null,
    space: Dp = 12.dp,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Gray100)
            .padding(
                start = paddingStart,
                top = paddingTop,
                end = paddingEnd,
                bottom = paddingBottom
            ),
        verticalArrangement = Arrangement.spacedBy(space)
    ) {
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Gray800
            )
        }

        content()
    }
}

@Composable
fun WhiteContainerItem(
    title: String,
    description: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                onClick = { onClick() },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .background(White000)
            .padding(start = 16.dp, top = 12.dp, end = 10.dp, bottom = 12.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title.replace("\n", " "),
                style = MaterialTheme.typography.headlineLarge,
                color = Gray800
            )

            Text(
                text = description,
                style = MaterialTheme.typography.labelSmall,
                color = Gray800
            )
        }

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_chevron_right),
            contentDescription = "more",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(16.dp),
            tint = Gray800
        )
    }
}

@Composable
fun GridLayout(
    columns: Int,
    items: List<Any>,
    modifier: Modifier = Modifier,
    spaceSize: Dp = 12.dp,
    content: @Composable (item: Any) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(spaceSize)
    ) {
        items.chunked(columns).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spaceSize)
            ) {
                rowItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        content(item)
                    }
                }

                if (rowItems.size < columns) {
                    Spacer(modifier = Modifier.weight(columns - rowItems.size.toFloat()))
                }
            }
        }
    }
}
