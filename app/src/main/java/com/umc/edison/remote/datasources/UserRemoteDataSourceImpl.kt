package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.UserRemoteDataSource
import com.umc.edison.data.model.IdentityKeywordEntity
import com.umc.edison.data.model.InterestKeywordEntity
import com.umc.edison.data.model.KeywordEntity
import com.umc.edison.remote.api.MyPageApiService
import com.umc.edison.remote.model.mypage.getCategoryQuestion
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val myPageApiService: MyPageApiService,
) : UserRemoteDataSource {
    override suspend fun getMyIdentityKeywords(): List<IdentityKeywordEntity> {
        val categories = myPageApiService.getMemberIdentityKeyword().data.categories

        val result: MutableList<IdentityKeywordEntity> = mutableListOf()

        result.add(
            IdentityKeywordEntity(
                question = getCategoryQuestion("CATEGORY1"),
                keywords = categories.category1.map {
                    KeywordEntity(
                        id = it.id,
                        name = it.name
                    )
                }
            )
        )

        result.add(
            IdentityKeywordEntity(
                question = getCategoryQuestion("CATEGORY2"),
                keywords = categories.category2.map {
                    KeywordEntity(
                        id = it.id,
                        name = it.name
                    )
                }
            )
        )

        result.add(
            IdentityKeywordEntity(
                question = getCategoryQuestion("CATEGORY3"),
                keywords = categories.category3.map {
                    KeywordEntity(
                        id = it.id,
                        name = it.name
                    )
                }
            )
        )

        return result
    }

    override suspend fun getMyInterestKeyword(): InterestKeywordEntity {
        val categories = myPageApiService.getMemberIdentityKeyword().data.categories

        return InterestKeywordEntity(
            question = getCategoryQuestion("CATEGORY4"),
            keywords = categories.category4.map {
                KeywordEntity(
                    id = it.id,
                    name = it.name
                )
            }
        )
    }
}