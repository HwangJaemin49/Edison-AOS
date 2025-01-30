package com.umc.edison.domain.usecase.label

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Label
import com.umc.edison.domain.repository.LabelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteLabelUseCase @Inject constructor(
    private val labelRepository: LabelRepository
) {
    operator fun invoke(label: Label): Flow<DataResource<Unit>> {
        label.bubbles.forEach { bubble ->
            bubble.labels = bubble.labels.filter { it.id != label.id }
        }

        return labelRepository.deleteLabel(label)
    }
}