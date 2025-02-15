package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.UserRemoteDataSource
import com.umc.edison.data.model.IdentityCategoryMapper
import com.umc.edison.data.model.InterestCategoryMapper
import com.umc.edison.data.model.toData
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetter
import com.umc.edison.domain.model.ArtLetterCategory
import com.umc.edison.domain.model.Identity
import com.umc.edison.domain.model.IdentityCategory
import com.umc.edison.domain.model.Interest
import com.umc.edison.domain.model.InterestCategory
import com.umc.edison.domain.model.User
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun getLogInState(): Flow<DataResource<Boolean>> = flowDataResource(
        dataAction = { userRemoteDataSource.getLogInState() }
    )

    override fun googleLogin(idToken: String): Flow<DataResource<User>> = flowDataResource(
        dataAction = {
            userRemoteDataSource.googleLogin(idToken)
        }
    )

    override fun makeNickName(nickname: String): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = {
            userRemoteDataSource.makeNickName(nickname)
        }
    )

    override fun getInterestKeywordsByCategory(interestCategory: InterestCategory): Flow<DataResource<Interest>> =
        flowDataResource(
            dataAction = {
                val categoryNumber = InterestCategoryMapper.entries.first {
                    it.category == interestCategory
                }.categoryNumber

                userRemoteDataSource.getMyInterestResult(categoryNumber)
            }
        )

    override fun getIdentityKeywordsByCategory(identityCategory: IdentityCategory): Flow<DataResource<Identity>> =
        flowDataResource(
            dataAction = {
                val categoryNumber = IdentityCategoryMapper.entries.first {
                    it.category == identityCategory
                }.categoryNumber

                userRemoteDataSource.getMyIdentityResult(categoryNumber)
            }
        )


    override fun setUserIdentity(identity: Identity): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { userRemoteDataSource.setUserIdentity(identity.toData()) }
    )


    override fun setUserInterest(interest: Interest): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { userRemoteDataSource.setUserInterest(interest.toData()) }
    )


    override fun getProfileInfo(): Flow<DataResource<User>> = flowDataResource(
        dataAction = { userRemoteDataSource.getProfileInfo() }
    )

    override fun getAllMyIdentityResults(): Flow<DataResource<List<Identity>>> = flowDataResource(
        dataAction = { userRemoteDataSource.getAllMyIdentityResults() }
    )

    override fun getMyInterestResult(interestCategory: InterestCategory): Flow<DataResource<Interest>> =
        flowDataResource(
            dataAction = {
                val categoryNumber = InterestCategoryMapper.entries.first {
                    it.category == interestCategory
                }.categoryNumber

                userRemoteDataSource.getMyInterestResult(categoryNumber)
            }
        )

    override fun getMyScrapArtLetters(): Flow<DataResource<List<ArtLetterCategory>>> =
        flowDataResource(
            dataAction = { userRemoteDataSource.getMyScrapArtLetters() }
        )

    override fun getScrapArtLettersByCategory(category: ArtLetterCategory): Flow<DataResource<List<ArtLetter>>> =
        flowDataResource(
            dataAction = { userRemoteDataSource.getScrapArtLettersByCategory(category.toData()) }
        )

    override fun updateProfileInfo(user: User): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { userRemoteDataSource.updateProfileInfo(user.toData()) }
    )

    override fun updateIdentity(identity: Identity): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { userRemoteDataSource.updateIdentity(identity.toData()) }
    )

    override fun updateInterest(interest: Interest): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { userRemoteDataSource.updateInterest(interest.toData()) }
    )

    override fun logOut(): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { userRemoteDataSource.logOut() }
    )

    override fun deleteAccount(): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { userRemoteDataSource.deleteAccount() }
    )

    override fun getMyIdentityResult(identityCategory: IdentityCategory): Flow<DataResource<Identity>> =
        flowDataResource(
            dataAction = {
                val categoryNumber = IdentityCategoryMapper.entries.first {
                    it.category == identityCategory
                }.categoryNumber

                userRemoteDataSource.getMyIdentityResult(categoryNumber)
            }
        )
}