package com.umc.edison.domain.usecase.label

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.repository.LabelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteLabelUseCase @Inject constructor(
    private val labelRepository: LabelRepository
) {
    operator fun invoke(id: String): Flow<DataResource<Unit>> = labelRepository.deleteLabel(id)
}