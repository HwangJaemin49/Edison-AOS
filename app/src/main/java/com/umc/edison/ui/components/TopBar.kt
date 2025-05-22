package com.umc.edison.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.umc.edison.R
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun HamburgerMenu(
    onClick: () -> Unit,
    alignment: Alignment.Horizontal,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White000)
            .padding(start = 24.dp, top = 12.dp, end = 24.dp)
    ) {
        androidx.compose.material3.IconButton(
            onClick = onClick,
            modifier = Modifier.align(alignment),
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
fun BackButtonTopBar(
    onBack: () -> Unit,
    backgroundColor: Color = White000,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(start = 24.dp, top = 12.dp, end = 24.dp)
            .height(32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        androidx.compose.material3.IconButton(
            onClick = { onBack() },
            modifier = Modifier.size(18.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_left),
                contentDescription = "뒤로가기",
                tint = Gray800
            )
        }

        content()
    }
}

@Composable
fun BackButtonTopBar(
    title: String,
    onBack: () -> Unit,
    backgroundColor: Color = White000
) {
    BackButtonTopBar(
        onBack = onBack,
        backgroundColor = backgroundColor
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = Gray800,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
fun LabelTopAppBar(
    label: LabelModel,
    onBackClick: () -> Unit
) {
    BackButtonTopBar(
        onBack = onBackClick,
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(color = label.color, shape = CircleShape)
                .border(
                    width = 3.dp,
                    color = if (label.color != White000) label.color else Gray100,
                    shape = RoundedCornerShape(16.dp)
                )
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "${label.name}  ${label.bubbleCnt}",
            style = MaterialTheme.typography.titleLarge,
            color = Gray800
        )
    }
}

