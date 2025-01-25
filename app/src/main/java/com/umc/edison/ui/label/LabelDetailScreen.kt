package com.umc.edison.ui.label

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.presentation.label.LabelDetailModel
import com.umc.edison.presentation.label.LabelDetailViewModel
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.ui.theme.White000

@Composable
fun LabelDetailScreen(
    navHostController: NavHostController,
    viewModel: LabelDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            LabelTopAppBar(
                label = uiState.label,
                navHostController = navHostController
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(White000),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (uiState.error != null) {
                Text(
                    text = "Error loading data",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                BubblesLayout(bubbles = uiState.label.bubbles)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelTopAppBar(
    label: LabelDetailModel,
    navHostController: NavHostController
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(color = label.labelColor, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${label.labelName} ${label.bubbles.size}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { navHostController.popBackStack() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_down),
                    contentDescription = "Back"
                )
            }
        }
    )
}

@Composable
fun BubblesLayout(bubbles: List<BubbleModel>) {
    Box(modifier = Modifier.fillMaxSize()) {
        bubbles.forEachIndexed { index, bubble ->
            val size = (100 + (index * 20)).dp // 각 버블의 크기
            val offset = Offset(
                x = (100 + (index * 60)).toFloat(),
                y = (200 + (index * 80)).toFloat()
            ) // 각 버블의 위치 (샘플)
            Bubble(
                text = bubble.title ?: bubble.contentBlocks.firstOrNull()?.content ?: "",
                size = size,
                offset = offset
            )
        }
    }
}

@Composable
fun Bubble(text: String, size: androidx.compose.ui.unit.Dp, offset: Offset) {
    Box(
        modifier = Modifier
            .size(size)
            .offset(x = offset.x.dp, y = offset.y.dp)
            .background(color = Color(0xFFE0F7FA), shape = CircleShape)
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
