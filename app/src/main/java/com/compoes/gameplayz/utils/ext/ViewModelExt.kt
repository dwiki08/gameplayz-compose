package com.compoes.gameplayz.utils.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compoes.gameplayz.utils.EventBus
import kotlinx.coroutines.launch

fun ViewModel.sendEvent(event: Any) {
    viewModelScope.launch {
        EventBus.sendEvent(event)
    }
}