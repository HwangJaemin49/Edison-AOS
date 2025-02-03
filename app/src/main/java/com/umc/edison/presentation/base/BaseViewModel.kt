package com.umc.edison.presentation.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.edison.domain.DataResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    /**
     * 공통적으로 DataResource를 처리하는 함수
     */
    protected fun <T> collectDataResource(
        flow: Flow<DataResource<T>>,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onLoading: (() -> Unit)? = null,
        onComplete: (() -> Unit)? = null
    ) {
        val TAG = flow::class.simpleName
        viewModelScope.launch {
            flow.onCompletion {
                onComplete?.invoke()
            }.collect { dataResource ->
                when (dataResource) {
                    is DataResource.Success -> {
                        Log.d(TAG, "onSuccess: ${dataResource.data}")
                        onSuccess(dataResource.data)
                    }
                    is DataResource.Error -> {
                        Log.e(TAG, "onError: ${dataResource.throwable}")
                        onError(dataResource.throwable)
                    }
                    is DataResource.Loading -> {
                        Log.d(TAG, "onLoading")
                        onLoading?.invoke()
                    }
                }
            }
        }
    }

    abstract fun clearToastMessage()
}
