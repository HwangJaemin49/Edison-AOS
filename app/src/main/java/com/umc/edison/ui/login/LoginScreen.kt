package com.umc.edison.ui.login

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.presentation.login.LoginViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.BasicFullButton
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray800
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.umc.edison.ui.theme.Gray700
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()
    var backPressedOnce by remember { mutableStateOf(false) }
    var showPopup by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        updateShowBottomNav(false)
    }

    BackHandler {
        if (backPressedOnce) {
            (context as? Activity)?.finish()
        } else {
            backPressedOnce = true
            coroutineScope.launch {
                delay(2000)
                backPressedOnce = false
            }
        }
    }

    BaseContent(
        uiState = uiState,
        clearToastMessage = { viewModel.clearToastMessage() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (showPopup) {
                    BubbleMessage(
                        Modifier.align(Alignment.TopCenter),
                        onDismiss = { showPopup = false })
                }

                Text(
                    text = "로그인하면 사용할 수 있는 기능",
                    fontSize = 16.sp,
                    color = Gray800,
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .clickable { showPopup = true }
                )
            }


                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {

                    BasicFullButton(
                        text = "구글 로그인",
                        modifier = Modifier,
                        enabled = true,
                        onClick = {
                            viewModel.signInWithGoogle(
                                context = context,
                                navController = navHostController
                            )

                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Gray300),
                        textStyle = TextStyle(color = Gray800, fontSize = 16.sp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "google image",
                        modifier = Modifier.padding(start = 24.dp)
                    )

                }

                Spacer(modifier = Modifier.height(24.dp))

                BasicFullButton(
                    text = "로그인 없이 긴급 시작",
                    enabled = true,
                    modifier = Modifier,
                    onClick = { navHostController.navigate(NavRoute.TermsOfUse.route) },
                    textStyle = TextStyle(fontSize = 16.sp)
                )

            }


        }


    }



@Composable
fun BubbleMessage(modifier: Modifier, onDismiss: () -> Unit) {
    Popup(
        alignment = Alignment.BottomCenter,
        offset = IntOffset(0, -150),
        properties = PopupProperties(
            dismissOnClickOutside = true,
            focusable = false
        ),
        onDismissRequest = { onDismiss() },
    ){
        Box(
            modifier = Modifier
                .size(width = 243.dp, height = 124.dp) // ✅ 고정 크기 설정
                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp)) // ✅ 말풍선을 둥글게 유지
                .background(Color.White) // ✅ 배경색 추가
                .padding(14.dp)
        ) {
            Column() {
                Text(
                    text = "로그인하면 뭐가 좋을까요?",
                    style = MaterialTheme.typography.titleMedium,
                    color = Gray800,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "나에게 맞는 버블 정리\n" +
                            "스페이스 기능 완벽 사용\n" +
                            "맞춤형 레터 추천 및 북마크 지원",
                    style = MaterialTheme.typography.labelLarge,
                    color = Gray700,
                )
            }
        }

    }

}

