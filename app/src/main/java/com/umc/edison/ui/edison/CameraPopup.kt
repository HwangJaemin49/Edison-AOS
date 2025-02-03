package com.umc.edison.ui.edison

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.umc.edison.ui.theme.Gray800

@Composable
fun CameraPopup(
    cameraExpanded: Boolean,
    onGalleryOpen: () -> Unit,
    onCameraOpen: () -> Unit,
    onDismiss: () -> Unit
) {

    Box {
        val popupWidth = 150.dp
        val popupHeight = 136.dp
        val cornerSize = 16.dp

        if (cameraExpanded) {
            Popup(
                alignment = Alignment.BottomCenter,
                offset = IntOffset(30, -100),
                properties = PopupProperties(
                    dismissOnClickOutside = true
                ),
                onDismissRequest = { onDismiss() }
            ) {
                Box(
                    Modifier
                        .shadow(6.dp, shape = RoundedCornerShape(cornerSize))
                        .size(popupWidth, popupHeight)
                        .padding(top = 5.dp)
                        .background(Color.White, RoundedCornerShape(cornerSize))
                        .border(1.dp, color = Gray800, RoundedCornerShape(cornerSize))

                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { }
                                .weight(1f),
                            contentAlignment = Alignment.Center // Box의 중앙에 Text 배치
                        ) {
                            Text(
                                text = "사진 촬영",
                                modifier = Modifier.clickable { onCameraOpen() },
                                fontSize = 16.sp,
                                color = Gray800
                            )

                        }
                        HorizontalDivider(modifier = Modifier.border(1.dp, Gray800))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { }
                                .weight(1f),
                            contentAlignment = Alignment.Center // Box의 중앙에 Text 배치
                        ) {
                            Text(
                                text = "갤러리",
                                modifier = Modifier.clickable { onGalleryOpen() },
                                fontSize = 16.sp,
                                color = Gray800
                            )

                        }
                        HorizontalDivider(modifier = Modifier.border(1.dp, Gray800))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { }
                                .weight(1f),
                            contentAlignment = Alignment.Center // Box의 중앙에 Text 배치
                        ) {
                            Text(
                                text = "배경 설정하기",
                                modifier = Modifier
                                    .clickable { println("clicked!") },
                                fontSize = 16.sp,
                                color = Gray800
                            )

                        }

                    }
                }
            }
        }
    }
}


@Composable
fun LinkPopup(
    linkExpanded: Boolean,
    onDismiss: () -> Unit,
    backLink: () -> Unit,
    linkBubble: () -> Unit
) {

    Box {
        val popupWidth = 150.dp
        val popupHeight = 92.dp
        val cornerSize = 16.dp

        if (linkExpanded) {
            Popup(
                alignment = Alignment.BottomCenter,
                offset = IntOffset(30, -100),
                properties = PopupProperties(
                    dismissOnClickOutside = true,
                ),
                onDismissRequest = { onDismiss() }
            ) {
                Box(
                    Modifier
                        .shadow(6.dp, shape = RoundedCornerShape(cornerSize))
                        .size(popupWidth, popupHeight)
                        .padding(top = 5.dp)
                        .background(Color.White, RoundedCornerShape(cornerSize))
                        .border(1.dp, color = Gray800, RoundedCornerShape(cornerSize))

                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {

                        HorizontalDivider(modifier = Modifier.border(1.dp, Gray800))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { }
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "[ ] 백링크",
                                fontSize = 16.sp,
                                color = Gray800,
                                modifier = Modifier.clickable { backLink() }
                            )
                        }
                        HorizontalDivider(modifier = Modifier.border(1.dp, Gray800))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { }
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "링크버블 만들기",
                                modifier = Modifier
                                    .padding(vertical = 13.dp)
                                    .clickable { linkBubble() },
                                fontSize = 16.sp,
                                color = Gray800
                            )

                        }
                    }
                }
            }
        }
    }
}