package com.teamdui.profiler.ui.util;

import android.widget.TextView;

public class TextUpdater {
    public static void textSetter(TextView view, String text)
    {
        if(view != null)
        {
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.setText(text);
                }
            });
        }
    }
}
