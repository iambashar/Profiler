package com.teamdui.profiler.ui.home;

import com.teamdui.profiler.ui.History.progress;
import com.teamdui.profiler.ui.History.set;

public class Data {
    public com.teamdui.profiler.ui.History.set set;
    public com.teamdui.profiler.ui.History.progress progress;

    public Data() {
        set = new set();
        progress = new progress();
    }
}
