package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.UserRemoteDataSource
import com.umc.edison.data.model.ArtLetterCategoryEntity
import com.umc.edison.data.model.IdentityCategoryMapper
import com.umc.edison.data.model.IdentityEntity
import com.umc.edison.data.model.InterestCategoryMapper
import com.umc.edison.data.model.InterestEntity
import com.umc.edison.data.model.KeywordEntity
import com.umc.edison.data.model.UserEntity
import com.umc.edison.remote.api.MyPageApiService
import com.umc.edison.remote.model.mypage.toUpdateTestRequest
import com.umc.edison.remote.model.mypage.toUpdateProfileRequest
import com.umc.edison.remote.token.TokenManager
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val myPageApiService: MyPageApiService,
    private val tokenManager: TokenManager,
) : UserRemoteDataSource {
    override suspend fun getAllMyIdentityResults(): List<IdentityEntity> {
        val categories = myPageApiService.getAllMyTestResults().data.categories

        val result: MutableList<IdentityEntity> = mutableListOf()

        result.add(
            IdentityEntity(
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
            IdentityEntity(
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
            IdentityEntity(
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

    override suspend fun getMyInterestResult(categoryNumber: String): InterestEntity {
        val categories = myPageApiService.getAllMyTestResults().data.categories

        val category = when (categoryNumber) {
            InterestCategoryMapper.INSPIRATION.categoryNumber -> categories.category4
            else -> throw IllegalArgumentException("Invalid categoryNumber")
        }

        val options = myPageApiService.getTestKeyword(categoryNumber).data

        return InterestEntity(
            categoryNumber = categoryNumber,
            keywords = category.map {
                KeywordEntity(
                    id = it.id,
                    name = it.name
                )
            },
            options = options.map { it.toData() }
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
            nickname = "닉네임",
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

    override suspend fun updateIdentity(identity: IdentityEntity) {
        myPageApiService.updateTest(identity.toUpdateTestRequest())
    }

    override suspend fun updateInterest(interest: InterestEntity) {
        myPageApiService.updateTest(interest.toUpdateTestRequest())
    }

    override suspend fun getMyIdentityResult(categoryNumber: String): IdentityEntity {
        val categories = myPageApiService.getAllMyTestResults().data.categories

        val category = when (categoryNumber) {
            IdentityCategoryMapper.EXPLAIN.categoryNumber -> categories.category1
            IdentityCategoryMapper.FIELD.categoryNumber -> categories.category2
            IdentityCategoryMapper.ENVIRONMENT.categoryNumber -> categories.category3
            else -> throw IllegalArgumentException("Invalid categoryNumber")
        }

        val options = myPageApiService.getTestKeyword(categoryNumber).data

        return IdentityEntity(
            categoryNumber = categoryNumber,
            keywords = category.map {
                KeywordEntity(
                    id = it.id,
                    name = it.name
                )
            },
            options = options.map { it.toData() }
        )
    }
}