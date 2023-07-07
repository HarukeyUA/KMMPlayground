package com.rainy.kmmplayground.features.emojiList

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.rainy.kmmplayground.domain.model.Emoji
import com.rainy.kmmplayground.network.EmojiRemoteDataSource
import com.rainy.kmmplayground.utils.exstensions.coroutineScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

sealed class EmojisListState {
    object Loading : EmojisListState()
    object Error : EmojisListState()
    data class Success(val list: List<Emoji>) : EmojisListState()
}

class EmojiListComponent(
    componentContext: ComponentContext,
    private val emojiRemoteDataSource: EmojiRemoteDataSource
) : ComponentContext by componentContext {

    private val coroutineScope = coroutineScope()
    private val defaultExceptionHandler = CoroutineExceptionHandler { _, _ ->
        _uiState.value = EmojisListState.Error
    }

    private val _uiState = MutableValue<EmojisListState>(EmojisListState.Loading)
    val uiState: Value<EmojisListState> get() = _uiState

    init {
        coroutineScope.launch(defaultExceptionHandler) {
            _uiState.value = EmojisListState.Success(emojiRemoteDataSource.getEmojis())
        }
    }
}