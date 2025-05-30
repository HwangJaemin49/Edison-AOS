package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.UserRemoteDataSource
import com.umc.edison.data.model.identity.IdentityCategoryEntity
import com.umc.edison.data.model.identity.IdentityEntity
import com.umc.edison.data.model.user.UserEntity
import com.umc.edison.remote.api.ArtLetterApiService
import com.umc.edison.remote.api.LoginApiService
import com.umc.edison.remote.api.MyPageApiService
import com.umc.edison.remote.model.login.IdTokenRequest
import com.umc.edison.remote.model.login.toSetIdentityKeywordRequest
import com.umc.edison.remote.model.mypage.toUpdateTestRequest
import com.umc.edison.remote.model.mypage.toUpdateProfileRequest
import com.umc.edison.remote.token.TokenManager
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val loginApiService: LoginApiService,
    private val myPageApiService: MyPageApiService,
    private val artLetterApiService: ArtLetterApiService,
    private val tokenManager: TokenManager,
) : UserRemoteDataSource {
    // CREATE
    override suspend fun addIdentity(identity: IdentityEntity) {
        loginApiService.setUserIdentityAndInterest(identity.toSetIdentityKeywordRequest())
    }

    override suspend fun googleLogin(idToken: String): UserEntity {
        val request = IdTokenRequest(idToken)
        val response = loginApiService.googleLogin(request)

        if (!response.isSuccess) {
            throw Exception("Google 로그인 실패: ${response.message}")
        }

        tokenManager.setToken(response.data.accessToken, response.data.refreshToken)

        return response.data.toData()
    }

    // READ
    override suspend fun getAllMyIdentityResults(): List<IdentityEntity> {
        return myPageApiService.getAllMyTestResults().data.categories.toData()
    }

    override suspend fun getAllRecentSearches(): List<String> {
        return artLetterApiService.getRecentSearches().data.keywords
    }

    override suspend fun getIdentityByCategory(category: IdentityCategoryEntity): IdentityEntity {
        val keywords = myPageApiService.getTestKeyword(category.categoryNumber).data
        return IdentityEntity(
            category = category,
            keywords = keywords.map { it.toData() },
            selectedKeywords = emptyList()
        )
    }

    override suspend fun getLogInState(): Boolean {
        return !tokenManager.loadAccessToken().isNullOrEmpty()
    }

    override suspend fun getMyProfileInfo(): UserEntity {
        return myPageApiService.getProfileInfo().data.toData()
    }

    // UPDATE
    override suspend fun logOut() {
        myPageApiService.logout()
        tokenManager.deleteToken()
    }

    override suspend fun updateIdentity(identity: IdentityEntity) {
        myPageApiService.updateTest(identity.toUpdateTestRequest())
    }

    override suspend fun updateProfileInfo(user: UserEntity) {
        myPageApiService.updateProfile(user.toUpdateProfileRequest())
    }

    // DELETE
    override suspend fun deleteRecentSearch(search: String) {
        artLetterApiService.removeRecentSearch(search)
    }

    override suspend fun deleteUser() {
        myPageApiService.deleteAccount()
        tokenManager.deleteToken()
    }
}