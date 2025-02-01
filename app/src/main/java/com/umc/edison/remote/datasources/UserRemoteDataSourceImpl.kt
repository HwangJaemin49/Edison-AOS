package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.UserRemoteDataSource
import com.umc.edison.data.model.ArtLetterCategoryEntity
import com.umc.edison.data.model.IdentityCategoryMapper
import com.umc.edison.data.model.IdentityKeywordEntity
import com.umc.edison.data.model.InterestCategoryMapper
import com.umc.edison.data.model.InterestKeywordEntity
import com.umc.edison.data.model.KeywordEntity
import com.umc.edison.data.model.UserEntity
import com.umc.edison.remote.api.MyPageApiService
import com.umc.edison.remote.model.mypage.toUpdateProfileRequest
import com.umc.edison.remote.token.TokenManager
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val myPageApiService: MyPageApiService,
    private val tokenManager: TokenManager,
) : UserRemoteDataSource {
    override suspend fun getMyIdentityKeywords(): List<IdentityKeywordEntity> {
        val categories = myPageApiService.getMemberIdentityKeyword().data.categories

        val result: MutableList<IdentityKeywordEntity> = mutableListOf()

        result.add(
            IdentityKeywordEntity(
                categoryNumber = IdentityCategoryMapper.EXPLAIN.categoryNumber,
                keywords = categories.category1.map {
                    KeywordEntity(
                        id = it.id,
                        name = it.name
                    )
                },
                options = emptyList()
            )
        )

        result.add(
            IdentityKeywordEntity(
                categoryNumber = IdentityCategoryMapper.FIELD.categoryNumber,
                keywords = categories.category2.map {
                    KeywordEntity(
                        id = it.id,
                        name = it.name
                    )
                },
                options = emptyList()
            )
        )

        result.add(
            IdentityKeywordEntity(
                categoryNumber = IdentityCategoryMapper.ENVIRONMENT.categoryNumber,
                keywords = categories.category3.map {
                    KeywordEntity(
                        id = it.id,
                        name = it.name
                    )
                },
                options = emptyList()
            )
        )

        return result
    }

    override suspend fun getMyInterestKeyword(): InterestKeywordEntity {
        val categories = myPageApiService.getMemberIdentityKeyword().data.categories

        return InterestKeywordEntity(
            categoryNumber = InterestCategoryMapper.INSPIRATION.categoryNumber,
            keywords = categories.category4.map {
                KeywordEntity(
                    id = it.id,
                    name = it.name
                )
            },
            options = emptyList()
        )
    }

    override suspend fun getLogInState(): Boolean {
        return tokenManager.loadAccessToken() != null
    }

    override suspend fun getMyScrapArtLetters(): List<ArtLetterCategoryEntity> {
        // TODO: api 명세 확인 후 구현
        return emptyList()
    }

    override suspend fun getProfileInfo(): UserEntity {
        // TODO: api 명세 확인 후 구현
        return UserEntity(
            email = "",
            nickname = "",
            profileImage = ""
        )
    }

    override suspend fun getScrapArtLettersByCategory(categoryId: Int): List<ArtLetterCategoryEntity> {
        // TODO: api 명세 확인 후 구현
        return emptyList()
    }

    override suspend fun updateProfileInfo(user: UserEntity) {
        myPageApiService.updateProfile(user.toUpdateProfileRequest())
    }
}