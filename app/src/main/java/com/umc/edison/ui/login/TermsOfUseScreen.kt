package com.umc.edison.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.presentation.login.TermsOfUseViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.BasicFullButton
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray800

@Composable
fun TermsOfUseScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: TermsOfUseViewModel = hiltViewModel(),
) {
    val baseState by viewModel.baseState.collectAsState()

    LaunchedEffect(Unit) {
        updateShowBottomNav(false)
    }
    BaseContent(
        baseState = baseState,
        clearToastMessage = { viewModel.clearToastMessage() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Spacer(modifier = Modifier.height(52.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_bubble_logo),
                contentDescription = "bubble",
                modifier = Modifier.size(56.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "에디슨 이용 약관",
                color = Gray800,
                style = MaterialTheme.typography.displayMedium,
            )

            Spacer(modifier = Modifier.height(47.55.dp))

            Text(
                text = "1.서비스 개요",
                color = Gray800,
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "본 약관은 사용자가 에디슨(이하 ‘서비스’)을 이용함에 있어 서비스 제공자와 사용자 간의 권리, 의무 및 책임 사항을 규정합니다.",
                color = Gray800,
                style = MaterialTheme.typography.titleSmall,
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "•서비스는 사용자가 메모를 작성, 저장, 분류할 수 있도록 지원하는 도구입니다.\n" +
                        "•사용자는 로컬 환경에서 메모를 저장 및 관리할 수 있으며, 구글 소셜 로그인을 통해 서버에 데이터를 저장할 수 있습니다.\n" +
                        "•구글 소셜 로그인 사용자는 아이덴티티 테스트를 진행하며, 이를 기반으로 레터 추천 및 메모 자동 분류 (스페이스) 기능을 제공받을 수 있습니다.",
                color = Gray600,
                style = MaterialTheme.typography.labelLarge,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "2.회원가입 및 계정 관리",
                color = Gray800,
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "•사용자는 구글 소셜 로그인을 통해 계정을 생성하고 서비스를 이용할 수 있습니다.\n" +
                        "•사용자는 언제든지 계정을 삭제하거나, 서비스 내에서 개인정보를 관리할 수 있습니다.\n" +
                        "•계정을 삭제하면 서버에 저장된 모든 메모 데이터가 삭제되며, 복구할 수 없습니다.",
                color = Gray600,
                style = MaterialTheme.typography.labelLarge,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "3.개인정보 보호 및 데이터 보안",
                color = Gray800,
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "서비스는 사용자의 개인정보 보호를 최우선으로 하며, 다음과 같은 원칙을 따릅니다.",
                color = Gray800,
                style = MaterialTheme.typography.titleSmall,
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("•수집 정보:\n")
                    }
                    append("•구글 소셜 로그인 시, 사용자 이름, 이메일 주소 등의 기본 정보를 수집합니다.\n•선호 키워드 테스트 결과 및 사용자 활동 데이터를 분석하여 개인화된 서비스를 제공합니다.\n")

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("•데이터 저장:\n")
                    }
                    append("•로컬 저장: 사용자가 기기에 메모를 저장할 경우, 서비스는 해당 데이터를 서버에 업로드하지 않습니다.\n")
                    append("•서버 저장: 사용자가 구글 소셜 로그인하면, 해당 데이터를 암호화하여 저장합니다.\n")

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("•보안 조치:\n")
                    }
                    append("•서비스는 사용자 데이터를 안전하게 보호하기 위해 암호화 기술을 적용합니다.\n•사용자의 동의 없이 데이터를 제 3자에게 제공하지 않습니다.")
                },
                color = Gray600,
                style = MaterialTheme.typography.labelLarge,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "4.서비스 이용 제한",
                color = Gray800,
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "다음과 같은 행위를 금지하며, 위반 시 서비스 이용이 제한될 수 있습니다.",
                color = Gray800,
                style = MaterialTheme.typography.titleSmall,
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "•불법적인 콘텐츠 저장 (저작권 침해, 명예훼손, 불법 정보 공유 등)\n" +
                        "•타인의 개인정보를 무단 수집 또는 공유하는 행위\n" +
                        "•서비스의 보안 시스템을 무단으로 해킹하거나 변조하는 행위\n" +
                        "•자동화된 봇 또는 스크립트를 사용하여 부정한 방식으로 데이터를 수집하는 행위",
                color = Gray600,
                style = MaterialTheme.typography.labelLarge,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "5.서비스 변경 및 종료",
                color = Gray800,
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "•서비스는 필요에 따라 기능을 추가, 변경 또는 중단할 수 있으며, 주요 변경 사항은 사전 공지됩니다.\n" +
                        "•무료 제공되는 서비스는 운영 정책에 따라 변경될 수 있습니다.",
                color = Gray600,
                style = MaterialTheme.typography.labelLarge,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "6.책임 제한",
                color = Gray800,
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "•서비스 제공자는 서버 장애, 해킹, 천재지변 등의 불가항력적인 사유로 인해 발생한 데이터 손실에 대해 책임을 지지 않습니다.\n" +
                        "•사용자가 작성한 메모 및 콘텐츠에 대한 법적 책임은 전적으로 사용자에게 있습니다.\n" +
                        "•서비스가 제공하는 레터 추천 및 메모 자동 분류(스페이스) 기능은 참고용이며, 완전한 정확성을 보장하지 않습니다.",
                color = Gray600,
                style = MaterialTheme.typography.labelLarge,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "7.제한",
                color = Gray800,
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "•본 약관은 법률 및 정책 변경에 따라 수정될 수 있으며, 변경된 내용은 서비스 내 공지사항을 통해 안내됩니다.\n" +
                        "•본 약관과 관련하여 궁금한 사항이 있을 경우, 서비스 내 고객센터를 통해 문의할 수 있습니다.",
                color = Gray600,
                style = MaterialTheme.typography.labelLarge,
            )

            Spacer(modifier = Modifier.height(48.dp))

            BasicFullButton(
                text = "동의하고 넘어가기",
                enabled = true,
                modifier = Modifier.padding(bottom = 24.dp),
                onClick = {
                    viewModel.buttonClicked(navHostController)
                },
                textStyle = TextStyle(fontSize = 16.sp)
            )
        }
    }
}
