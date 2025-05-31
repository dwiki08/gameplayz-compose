package com.compoes.gameplayz.ui.screen.home_screen

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
class HomeViewModel @Inject constructor(
    private val repository: GamesRepository,
) : ViewModel() {

    private var _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
    private var currentPage = 1

    init {
        getGames()
    }

    fun getGames() {
        currentPage = 1
        loadGames(currentPage)
    }

    fun nextPage() {
        loadGames(++currentPage)
    }

    private fun loadGames(page: Int) {
        if (_state.value.isLoading) return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.getGames(page = page, pageSize = 10).onRight { result ->
                _state.update {
                    val games = it.games.toMutableList()
                    games.addAll(result)
                    it.copy(games = games, isLoading = false)
                }
                currentPage = page
            }.onLeft { e ->
                --currentPage
                _state.update {
                    it.copy(error = e.error.message, isLoading = false)
                }
                sendEvent(Event.Toast("${e.code} : ${e.error.message}"))
            }
        }
    }

    fun setStateQuery(text: String) {
        _state.update { it.copy(query = text) }
    }

    fun searchGames(query: String) {
        viewModelScope.launch {
            setStateQuery(query)
            if (query.isEmpty()) {
                getGames()
                return@launch
            }
            _state.update { HomeState(query = query, isLoading = true) }
            repository.searchGames(query).onRight { result ->
                _state.update {
                    HomeState(query = query, games = result)
                }
            }.onLeft { e ->
                _state.update {
                    HomeState(query = query, error = e.error.message)
                }
                sendEvent(Event.Toast("${e.code} : ${e.error.message}"))
            }
        }
    }
}