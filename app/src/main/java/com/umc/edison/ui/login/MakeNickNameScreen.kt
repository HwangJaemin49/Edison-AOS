package com.umc.edison.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.presentation.login.MakeNickNameViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.BasicFullButton
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray800


@Composable
fun MakeNickNameScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: MakeNickNameViewModel = hiltViewModel(),
){

    val uiState by viewModel.uiState.collectAsState()
    var textState by remember { mutableStateOf("") }



    LaunchedEffect(Unit) {
        updateShowBottomNav(false)
    }

    BaseContent(
        uiState = uiState,
        clearToastMessage = { viewModel.clearToastMessage() },
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.Bottom,

        ){

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "에디슨에서 사용할\n닉네임을 설정해주세요.",
                fontSize = 24.sp,
                color = Gray800,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Gray100, shape = RoundedCornerShape(16.dp))
                    .height(48.dp)

            ){
                TextField(
                    value = textState,
                    onValueChange = {
                        if (it.length <= 20) {
                            textState = it
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(fontSize = 16.sp, color = Gray800),
                    placeholder = {
                        Text(
                            text = "닉네임을 입력해주세요. (최대 20자)",
                            fontSize = 16.sp,
                            color = Gray600
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Gray100,
                        unfocusedContainerColor = Gray100,
                        disabledContainerColor = Gray100,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),

                    modifier = Modifier
                        .fillMaxWidth()
                )

            }



            Spacer(modifier = Modifier.weight(4f))

            BasicFullButton(
                text = "다음으로",
                enabled = textState.isNotEmpty(),
                modifier = Modifier,
                onClick = {
                    viewModel.makeNickName(textState,navHostController)
                   // viewModel.buttonClicked(navHostController)
                },
                textStyle = TextStyle(fontSize = 16.sp)
            )

        }


    }


}


