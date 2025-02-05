package com.umc.edison.presentation.edison

import android.net.Uri
import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.label.LabelEditMode
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.components.IconType
import com.umc.edison.ui.components.ListStyle
import com.umc.edison.ui.components.TextStyle

data class BubbleInputState(
    override val isLoading: Boolean = false,
    val bubble: BubbleModel,
    val bubbles: List<BubbleModel>,
    val labelEditMode: LabelEditMode = LabelEditMode.NONE,
    val labels: List<LabelModel>,
    val selectedIcon: IconType = IconType.NONE,
    val selectedTextStyles: List<TextStyle> = emptyList(),
    val selectedListStyle: ListStyle = ListStyle.NONE,
    val isGalleryOpen: Boolean = false,
    val isCameraOpen: Boolean = false,
    val cameraImagePath: Uri? = null,
    val canSave: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
) : BaseState {
    companion object {
        val DEFAULT = BubbleInputState(
            isLoading = false,
            bubble = BubbleModel(),
            bubbles = emptyList(),
            labels = emptyList(),
            labelEditMode = LabelEditMode.NONE,
        )
    }
}
