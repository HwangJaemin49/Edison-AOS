package com.umc.edison.data.repository

import com.umc.edison.data.bound.FlowBoundResourceFactory
import com.umc.edison.data.datasources.LabelLocalDataSource
import com.umc.edison.data.datasources.LabelRemoteDataSource
import com.umc.edison.data.model.label.toData
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.label.Label
import com.umc.edison.domain.repository.LabelRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class LabelRepositoryImpl @Inject constructor(
    private val labelLocalDataSource: LabelLocalDataSource,
    private val labelRemoteDataSource: LabelRemoteDataSource,
    private val resourceFactory: FlowBoundResourceFactory
) : LabelRepository {
    // CREATE
    override fun addLabel(label: Label): Flow<DataResource<Unit>> =
        resourceFactory.sync(
            localAction = { labelLocalDataSource.addLabel(label.toData()) },
            remoteSync = { labelRemoteDataSource.addLabel(label.toData()) },
            onRemoteSuccess = { remoteData -> labelLocalDataSource.markAsSynced(remoteData) }
        )

    // READ
    override fun getAllLabels(): Flow<DataResource<List<Label>>> =
        resourceFactory.local(
            dataAction = { labelLocalDataSource.getAllLabels() }
        )

    override fun getLabel(id: String): Flow<DataResource<Label>> =
        resourceFactory.local(
            dataAction = { labelLocalDataSource.getLabel(id) }
        )

    // UPDATE
    override fun updateLabel(label: Label): Flow<DataResource<Unit>> =
        resourceFactory.sync(
            localAction = { labelLocalDataSource.updateLabel(label.toData()) },
            remoteSync = { labelRemoteDataSource.updateLabel(label.toData()) },
            onRemoteSuccess = { remoteData -> labelLocalDataSource.markAsSynced(remoteData) }
        )

    // DELETE
    override fun deleteLabel(id: String): Flow<DataResource<Unit>> =
        resourceFactory.sync(
            localAction = {
                val label = labelLocalDataSource.getLabel(id)

                if (!label.isDeleted) {
                    label.copy(
                        isDeleted = true,
                        deletedAt = Date()
                    )
                    labelLocalDataSource.updateLabel(label)
                }
            },
            remoteSync = { labelRemoteDataSource.deleteLabel(id) },
            onRemoteSuccess = { remoteData -> labelLocalDataSource.deleteLabel(remoteData) }
        )
}