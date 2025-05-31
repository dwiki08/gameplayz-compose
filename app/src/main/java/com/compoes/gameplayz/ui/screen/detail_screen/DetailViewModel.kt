package com.compoes.gameplayz.ui.screen.detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compoes.gameplayz.domain.model.Game
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
class DetailViewModel @Inject constructor(
    private val repository: GamesRepository,
) : ViewModel() {

    private var _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    fun getDetailGame(id: Int) {
        if (_state.value.game != null) return
        viewModelScope.launch {
            _state.update { DetailState(isLoading = true) }
            repository.getGameDetail(id).onRight { result ->
                _state.update {
                    DetailState(game = result)
                }
            }.onLeft { e ->
                _state.update {
                    DetailState(error = e.error.message)
                }
                sendEvent(Event.Toast(e.error.message))
            }
        }
    }

    fun addFavorite(game: Game) {
        viewModelScope.launch {
            repository.addFavorite(game.copy(isFavorite = true))
            _state.update { it.copy(game = it.game?.copy(isFavorite = true)) }
        }
    }

    fun removeFavorite(id: Int) {
        viewModelScope.launch {
            repository.removeFavorite(id)
            _state.update { it.copy(game = it.game?.copy(isFavorite = false)) }
        }
    }
}