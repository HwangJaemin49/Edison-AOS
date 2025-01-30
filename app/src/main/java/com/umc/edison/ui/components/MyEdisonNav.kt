package com.umc.edison.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umc.edison.R
import com.umc.edison.ui.theme.Gray300
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.Gray500

@Composable
fun MyEdisonNavBar(
    onProfileClicked: () -> Unit,
    onCompassClicked: () -> Unit
) {
    var isSearchActive by remember { mutableStateOf(false) } // 검색창 활성화 여부

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(if (isSearchActive) 382.dp else 136.dp) // Box 너비 조건부 변경
                .height(48.dp)
                .background(color = Gray300, shape = RoundedCornerShape(30.dp)),
            contentAlignment = Alignment.Center
        ) {
            // 검색창 활성화 상태
            if (isSearchActive) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    var text by remember { mutableStateOf("") }
                    BasicTextField(
                        value = text,
                        onValueChange = { text = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = Gray800
                        ),
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .width(282.dp)
                                    .height(34.dp)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(30.dp)
                                    )
                                    .padding(horizontal = 8.dp) // 내부 여백 설정
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_topbar_search),
                                    contentDescription = "Search Icon",
                                )
                                Spacer(modifier = Modifier.width(8.dp)) // 아이콘과 텍스트 간격
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (text.isEmpty()) {
                                        Text(
                                            text = "검색",
                                            color = Gray500
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        }
                    )

                    IconButton(
                        onClick = { isSearchActive = false },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_topbar_bubble),
                            contentDescription = "Profile Icon",
                            tint = Color.Unspecified
                        )
                    }

                    IconButton(
                        onClick = {
                            isSearchActive = false
                            onCompassClicked()
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_topbar_my_edison),
                            contentDescription = "Compass Icon",
                            tint = Color.Unspecified
                        )
                    }
                }
            } else { // 검색창 비활성화 상태
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    IconButton(
                        onClick = { isSearchActive = true }, // 검색창 활성화
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_topbar_search),
                            contentDescription = "Search Icon",
                            tint = Color.Unspecified
                        )
                    }

                    IconButton(
                        onClick = { isSearchActive = false },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_topbar_bubble),
                            contentDescription = "Profile Icon",
                            tint = Color.Unspecified
                        )
                    }

                    IconButton(
                        onClick = {
                            isSearchActive = false
                            onCompassClicked()
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_topbar_my_edison),
                            contentDescription = "Compass Icon",
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BubbleStorageTopBarPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        MyEdisonNavBar(
            onProfileClicked = { /* 클릭 로직 */ },
            onCompassClicked = { /* 클릭 로직 */ }
        )
    }
}
