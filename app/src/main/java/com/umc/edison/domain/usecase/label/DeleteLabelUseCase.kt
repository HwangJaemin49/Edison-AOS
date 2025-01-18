package com.umc.edison.domain.usecase.label

import com.umc.edison.domain.model.Label
import com.umc.edison.domain.repository.LabelRepository
import javax.inject.Inject

class DeleteLabelUseCase @Inject constructor(
    private val labelRepository: LabelRepository
) {
    operator fun invoke(label: Label) = labelRepository.deleteLabel(label)
}