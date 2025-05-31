package com.compoes.gameplayz.ui.screen.favorite_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compoes.gameplayz.domain.repository.GamesRepository
import com.compoes.gameplayz.utils.Event
import com.compoes.gameplayz.utils.ext.sendEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: GamesRepository,
) : ViewModel() {

    private var _state = MutableStateFlow(FavoriteState())
    val state = _state.asStateFlow()

    init {
        getFavorites()

        viewModelScope.launch {
            repository.observeFavorites().collect {
                getFavorites()
            }
        }
    }

    fun getFavorites() {
        viewModelScope.launch {
            repository.getFavorites().onRight { games ->
                _state.update { it.copy(games = games) }
            }.onLeft { e ->
                _state.update { it.copy(error = e.error.message) }
                sendEvent(Event.Toast("${e.code} : ${e.error.message}"))
            }
        }
    }

}