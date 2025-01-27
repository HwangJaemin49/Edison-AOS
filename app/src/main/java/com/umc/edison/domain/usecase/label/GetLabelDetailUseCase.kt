package com.umc.edison.domain.usecase.label

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Label
import com.umc.edison.domain.repository.LabelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLabelDetailUseCase @Inject constructor(
    private val labelRepository: LabelRepository
) {
    operator fun invoke(labelId: Int): Flow<DataResource<Label>> = labelRepository.getLabelDetail(labelId)
}