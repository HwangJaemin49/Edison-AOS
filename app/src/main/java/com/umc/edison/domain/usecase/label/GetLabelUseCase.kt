package com.umc.edison.domain.usecase.label

import com.umc.edison.data.DataResource
import com.umc.edison.domain.model.label.Label
import com.umc.edison.domain.repository.LabelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLabelUseCase @Inject constructor(
    private val labelRepository: LabelRepository
) {
    operator fun invoke(id: String): Flow<DataResource<Label>> =
        labelRepository.getLabel(id)
}
