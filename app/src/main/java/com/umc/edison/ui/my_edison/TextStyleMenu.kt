package com.umc.edison.ui.my_edison

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.umc.edison.R

@Composable
fun textStyleMenu(textExpanded: Boolean) {


    if (textExpanded)
        Box(
            modifier = Modifier
                .background(Color.White)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .imePadding()
                    .padding(vertical = 13.dp, horizontal = 16.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_text_tool_off),
                    contentDescription = "Text Tool",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp)
                        .offset(x = 23.dp)
                        .clickable { }
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_list),
                    contentDescription = "List",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { }
                )


                Box() {
                    Image(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = "Camera",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { }

                    )

                }


                Image(
                    painter = painterResource(id = R.drawable.ic_link),
                    contentDescription = "Link",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {

                        }
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_tag),
                    contentDescription = "List",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp)
                        .offset(x = -23.dp)
                        .clickable { }
                )

            }
        }
}
