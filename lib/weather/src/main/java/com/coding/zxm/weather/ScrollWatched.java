package com.coding.zxm.weather;

/**
 * Created by dongua on 17-9-11.
 */

public interface ScrollWatched {
    void addWatcher(ScrollWatcher watcher);
    void removeWatcher(ScrollWatcher watcher);
    void notifyWatcher(int x);
}
