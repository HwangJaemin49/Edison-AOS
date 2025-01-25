package com.umc.edison.ui.my_edison

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun CameraPopup(CameraExpanded : Boolean, onGalleryOpen: () -> Unit, onCameraOpen:()->Unit, onDismiss: () -> Unit){

    Box {
        val popupWidth = 150.dp
        val popupHeight = 136.dp
        val cornerSize = 16.dp

        if (CameraExpanded) {
            Popup(
                alignment = Alignment.BottomCenter,
                offset = IntOffset(30, -100),
                properties = PopupProperties(
                    dismissOnClickOutside = true, // 팝업 외부 클릭 시 닫히도록 설정
                    //focusable = true
                ),
                onDismissRequest = { onDismiss() }
            ) {
                Box(
                    Modifier
                        .shadow(12.dp,shape = RoundedCornerShape(cornerSize))
                        .size(popupWidth, popupHeight)
                        .padding(top = 5.dp)
                        .background(Color.White, RoundedCornerShape(cornerSize))
                        .border(1.dp, color = Color(0xFF8E9398), RoundedCornerShape(cornerSize))

                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Text(
                            text = "사진 촬영",
                            modifier = Modifier.padding(vertical = 13.dp, horizontal = 35.dp)
                                                .weight(1f)
                                                .clickable{onCameraOpen()},
                            fontSize = 16.sp,
                            color = Gray800
                        )
                        HorizontalDivider(modifier = Modifier.border(1.dp, Color.Black))
                        Text(
                            text = "갤러리",
                            modifier = Modifier.padding(vertical = 13.dp, horizontal = 42.dp)
                                               .weight(1f)
                                               .clickable{onGalleryOpen()},
                            fontSize = 16.sp,
                            color = Gray800
                        )
                        HorizontalDivider(modifier = Modifier.border(1.dp, Color.Black))
                        Text(
                            text = "배경 설정하기",
                            modifier = Modifier.padding(vertical = 13.dp, horizontal =  22.dp)
                                .clickable{ println("clicked!") }
                                .weight(1f),
                            fontSize = 16.sp,
                            color = Gray800
                        )
                    }
                }
            }
        }
    }
}