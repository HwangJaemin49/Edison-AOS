package com.umc.edison.domain.usecase.label

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.label.Label
import com.umc.edison.domain.repository.LabelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateLabelUseCase @Inject constructor(
    private val labelRepository: LabelRepository
) {
    operator fun invoke(label: Label): Flow<DataResource<Unit>> = labelRepository.updateLabel(label)
}