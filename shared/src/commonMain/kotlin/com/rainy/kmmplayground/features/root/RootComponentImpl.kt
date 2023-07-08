package com.rainy.kmmplayground.features.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.rainy.kmmplayground.features.emojiList.EmojiListComponentImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        class EmojiList(val component: EmojiListComponentImpl) : Child()
    }
}

class RootComponentImpl(
    componentContext: ComponentContext
) : ComponentContext by componentContext, KoinComponent, RootComponent {

    private val navigation = StackNavigation<Config>()


    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.EmojiList,
            childFactory = ::child,
        )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            Config.EmojiList -> RootComponent.Child.EmojiList(
                EmojiListComponentImpl(componentContext, get())
            )
        }
    }

    @Parcelize
    private sealed class Config : Parcelable {
        object EmojiList : Config()
    }
}