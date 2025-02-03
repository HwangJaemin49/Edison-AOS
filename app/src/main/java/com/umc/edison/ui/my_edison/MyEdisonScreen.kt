package com.umc.edison.ui.my_edison


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.umc.edison.R
import com.umc.edison.ui.components.BubbleInput
import com.umc.edison.ui.components.BubbleStorageTopBar
import com.umc.edison.ui.navigation.NavRoute

@Composable
fun MyEdisonScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        BubbleStorageTopBar(
            onProfileClicked = { /* */ },
            onCompassClicked = { /* */ }
        )



        Spacer(modifier = Modifier.weight(0.6f))


        Image(
            painter = painterResource(id = R.drawable.ic_up_slide),
            contentDescription = "up slide",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(24.dp)
                .height(44.dp)
                .clickable { }
        )

        Spacer(modifier = Modifier.weight(0.4f))



        BubbleInput(
            onClick = { navController.navigate(NavRoute.BubbleEdit.createRoute(0)) },
            onSwipeUp = { }

            )

        Spacer(modifier = Modifier.weight(1f))
    }
}

//@Preview(showBackground = true)
//@Composable
//fun BubbleSpaceScreenPreview() {
//    EdisonTheme {
//        MyEdisonScreen()
//    }
//}
