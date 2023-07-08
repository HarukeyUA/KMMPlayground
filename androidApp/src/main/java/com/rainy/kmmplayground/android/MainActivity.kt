package com.rainy.kmmplayground.android

import android.os.Bundle
import android.text.Html
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.rainy.kmmplayground.domain.model.Emoji
import com.rainy.kmmplayground.features.emojiList.EmojiListComponent
import com.rainy.kmmplayground.features.emojiList.EmojisListState
import com.rainy.kmmplayground.features.root.RootComponent
import com.rainy.kmmplayground.features.root.RootComponentImpl

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val appComponent =
            RootComponentImpl(
                defaultComponentContext()
            )
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Children(stack = appComponent.stack) {
                        when (val child = it.instance) {
                            is RootComponent.Child.EmojiList -> EmojiListRoute(child.component)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun EmojiListRoute(component: EmojiListComponent) {
        val uiState by component.uiState.subscribeAsState()

        when (uiState) {
            EmojisListState.Error -> {
                Error()
            }

            EmojisListState.Loading -> {
                Loading()
            }

            is EmojisListState.Success -> {
                EmojiList((uiState as EmojisListState.Success).list)
            }
        }

    }

    @Composable
    private fun Error() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = stringResource(R.string.an_error_occurred),
                style = MaterialTheme.typography.h4
            )
        }
    }

    @Composable
    private fun Loading() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator()
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun EmojiList(items: List<Emoji>) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items) {
                val emojiCode = remember(it) {
                    Html.fromHtml(it.htmlCode, Html.FROM_HTML_MODE_COMPACT).toString()
                }
                ListItem(
                    icon = {
                        Text(
                            text = emojiCode
                        )
                    }
                ) {
                    Text(text = it.name)
                }

            }
        }
    }
}
