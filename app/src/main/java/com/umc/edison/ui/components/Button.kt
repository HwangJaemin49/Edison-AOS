package com.umc.edison.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.umc.edison.R
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray400
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray700
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.Gray900
import com.umc.edison.ui.theme.White000

@Composable
fun BasicFullButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Gray800,
        contentColor = White000,
        disabledContainerColor = Gray300,
        disabledContentColor = Gray600,
    ),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = colors,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun MiddleCancelButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BasicFullButton(
        text = text,
        modifier = modifier,
        enabled = true,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Gray100,
            contentColor = Gray600,
        ),
        textStyle = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun MiddleConfirmButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    onClick: () -> Unit
) {
    BasicFullButton(
        text = text,
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Gray800,
            contentColor = Gray100,
            disabledContainerColor = Gray400,
            disabledContentColor = Gray700,
        ),
        textStyle = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun MiddleDeleteButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    onClick: () -> Unit
) {
    BasicFullButton(
        text = "삭제하기",
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFE2E5),
            contentColor = Color(0xFFFF0000),
            disabledContainerColor = Gray400,
            disabledContentColor = Color(0xFFFF0000),
        ),
        textStyle = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun EditDeleteIcons(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .width(120.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(Gray800)
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pencil),
                contentDescription = "Edit Label",
                modifier = Modifier
                    .size(28.dp)
                    .clickable(onClick = onEditClick),
                tint = Gray100
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color(0xFFFF0000))
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_trash),
                contentDescription = "Delete Label",
                modifier = Modifier
                    .size(28.dp)
                    .clickable(onClick = onDeleteClick),
                tint = Gray100
            )
        }
    }
}

@Composable
fun RadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_radio_on)
    val unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_radio_off)

    IconButton(
        selected = selected,
        onClick = onClick,
        selectedIcon = selectedIcon,
        unselectedIcon = unselectedIcon,
        modifier = modifier
    )
}

@Composable
fun CheckBoxButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_check_box_on)
    val unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_check_box_off)

    IconButton(
        selected = selected,
        onClick = onClick,
        selectedIcon = selectedIcon,
        unselectedIcon = unselectedIcon,
        modifier = modifier
    )
}

@Composable
private fun IconButton(
    selected: Boolean,
    onClick: () -> Unit,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = if (selected) selectedIcon else unselectedIcon,
        contentDescription = null,
        modifier = modifier.clickable { onClick() },
        tint = Color.Unspecified
    )
}

@Composable
fun KeywordChip(
    keyword: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(if (isSelected) Gray700 else Gray100)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = keyword,
            color = if (isSelected) Gray100 else Gray900,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
