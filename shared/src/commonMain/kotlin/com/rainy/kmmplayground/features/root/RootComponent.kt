package com.rainy.kmmplayground.features.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.rainy.kmmplayground.features.emojiList.EmojiListComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, KoinComponent {

    private val navigation = StackNavigation<Config>()


    val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.EmojiList,
            childFactory = ::child,
        )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): Child {
        return when (config) {
            Config.EmojiList -> Child.EmojiList(
                EmojiListComponent(componentContext, get())
            )
        }
    }

    sealed class Child {
        class EmojiList(val component: EmojiListComponent) : Child()
    }

    @Parcelize
    private sealed class Config : Parcelable {
        object EmojiList : Config()
    }
}