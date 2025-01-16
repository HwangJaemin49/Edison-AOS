package com.umc.edison.ui.space

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.umc.edison.R
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.White000

@Composable
fun LabelTabScreen() {
    // 플로팅 버튼 관련
    var viewMode: ViewMode by remember { mutableStateOf(ViewMode.LIST) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (viewMode == ViewMode.GRAPH) {
            // TODO: 그래프 뷰
        } else {
            LabelListScreen()
        }

        // 플로팅 버튼
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .offset(x = (-12).dp, y = (-12).dp)
                .size(64.dp)
                .clip(RoundedCornerShape(100.dp))
                .border(1.dp, Gray300, RoundedCornerShape(100.dp))
                .background(White000)
                .shadow(15.dp, spotColor = Color(142f, 139f, 139f, 0.15f))
                .clickable(onClick = {
                    viewMode = if (viewMode == ViewMode.LIST) ViewMode.GRAPH else ViewMode.LIST
                })
        ) {
            Image(
                painter = painterResource(
                    id =
                    if (viewMode == ViewMode.LIST) R.drawable.ic_graph
                    else R.drawable.ic_list
                ),
                contentDescription = "change label view mode",
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

enum class ViewMode {
    LIST,
    GRAPH
}
