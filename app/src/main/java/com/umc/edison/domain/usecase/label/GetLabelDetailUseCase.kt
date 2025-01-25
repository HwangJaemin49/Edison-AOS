package com.umc.edison.domain.usecase.label

import com.umc.edison.domain.repository.LabelRepository
import javax.inject.Inject

class GetLabelDetailUseCase @Inject constructor(
    private val labelRepository: LabelRepository
) {
    operator fun invoke(labelId: Int) = labelRepository.getLabelDetail(labelId)
}