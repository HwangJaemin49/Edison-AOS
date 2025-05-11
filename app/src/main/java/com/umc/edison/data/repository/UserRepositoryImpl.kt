package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.UserRemoteDataSource
import com.umc.edison.data.model.user.toData
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.user.User
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {
    // CREATE
    override fun googleLogin(idToken: String): Flow<DataResource<User>> = flowDataResource(
        dataAction = {
            userRemoteDataSource.googleLogin(idToken)
        }
    )

    // READ
    override fun getLogInState(): Flow<DataResource<Boolean>> = flowDataResource(
        dataAction = { userRemoteDataSource.getLogInState() }
    )

    override fun getMyProfileInfo(): Flow<DataResource<User>> = flowDataResource(
        dataAction = { userRemoteDataSource.getMyProfileInfo() }
    )

    // UPDATE
    override fun logOut(): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { userRemoteDataSource.logOut() }
    )

    override fun updateProfileInfo(user: User): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { userRemoteDataSource.updateProfileInfo(user.toData()) }
    )

    // DELETE
    override fun deleteUser(): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { userRemoteDataSource.deleteUser() }
    )
}