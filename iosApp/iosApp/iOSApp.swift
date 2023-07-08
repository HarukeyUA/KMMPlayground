import SwiftUI
import shared

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate

    private var rootHolder: RootHolder { appDelegate.getRootHolder() }
    
    init() {
        KoinKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
            RootView(rootHolder.root)
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.didBecomeActiveNotification)) { _ in
                    LifecycleRegistryExtKt.resume(rootHolder.lifecycle)
                }
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.willResignActiveNotification)) { _ in
                    LifecycleRegistryExtKt.pause(rootHolder.lifecycle)
                }
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.didEnterBackgroundNotification)) { _ in
                    LifecycleRegistryExtKt.stop(rootHolder.lifecycle)
                }
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.willTerminateNotification)) { _ in
                    LifecycleRegistryExtKt.destroy(rootHolder.lifecycle)
                }
		}
	}
}

struct RootView: View {
    private let root: RootComponent
    
    @StateValue
    private var childStack: ChildStack<AnyObject, RootComponentChild>
    
    private var activeChild: RootComponentChild { childStack.active.instance }
    
    init(_ root: RootComponent) {
        self.root = root
        _childStack = StateValue(root.stack)
    }
    
    var body: some View {
        switch activeChild {
        case let activeChild as RootComponentChild.EmojiList: EmojiListView(component: activeChild.component)
        default: EmptyView()
        }
    }
    
    
}

class AppDelegate: NSObject, UIApplicationDelegate {
    private var rootHolder: RootHolder?
    
    fileprivate func getRootHolder() -> RootHolder {
        if (rootHolder == nil) {
            rootHolder = RootHolder(savedState: nil)
        }
        
        return rootHolder!
    }
}

private class RootHolder {
    let lifecycle: LifecycleRegistry
    let root: RootComponent
    
    init(savedState: ParcelableParcelableContainer?) {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        
        root = RootComponentImpl(
            componentContext: DefaultComponentContext(
                lifecycle: lifecycle,
                stateKeeper: nil,
                instanceKeeper: nil,
                backHandler: nil
            )
        )
        
        LifecycleRegistryExtKt.create(lifecycle)
    }
}
