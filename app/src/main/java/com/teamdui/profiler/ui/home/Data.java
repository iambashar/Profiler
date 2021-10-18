package com.teamdui.profiler.ui.home;

import com.teamdui.profiler.ui.history.progress;
import com.teamdui.profiler.ui.history.set;

public class Data {
    public com.teamdui.profiler.ui.history.set set;
    public com.teamdui.profiler.ui.history.progress progress;

    public Data() {
        set = new set();
        progress = new progress();
    }
}
