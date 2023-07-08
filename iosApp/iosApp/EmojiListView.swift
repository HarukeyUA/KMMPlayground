//
//  EmojiList.swift
//  iosApp
//
//  Created by Rainy on 08.07.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct EmojiListView: View {
    let component: EmojiListComponent
    
    @StateValue
    private var uiState: EmojisListState
    
    init(component: EmojiListComponent) {
        self.component = component
        _uiState = StateValue(component.uiState)
    }
    
    var body: some View {
        switch uiState {
        case uiState as EmojisListState.Loading: ProgressView()
        case uiState as EmojisListState.Error: Text("An error occured")
        case let uiState as EmojisListState.Success: List(uiState.list, id: \.self) { emoji in
            HStack {
                Text(String(UnicodeScalar(Int(emoji.unicode.dropFirst(2), radix: 16)!)!))
                Text(emoji.name)
            }
        }.listStyle(PlainListStyle())
        default: EmptyView()
        }
    }
}


struct EmojiList_Previews: PreviewProvider {
    static var previews: some View {
        EmojiListView(component: PreviewEmojiListComponent())
    }
}

class PreviewEmojiListComponent: EmojiListComponent {
    let uiState: Value<EmojisListState> = mutableValue(
        EmojisListState.Success(list: [
            Emoji(category: "smileys and people", group: "creature face", htmlCode: "&#128123;", name: "ghost", unicode: "U+1F47B")
            
        ])
    )
}
