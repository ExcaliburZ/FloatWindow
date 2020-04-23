package com.wings.floatwindow.app;

import android.app.Activity;
import android.app.Application;
import android.view.View;
import android.widget.Toast;

import com.wings.floatwindow.FloatWindow;

import org.jetbrains.annotations.NotNull;

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //在全局or部分页面添加一个固定的view or 一个浮动的view
        FloatWindow floatWindow = FloatWindow.INSTANCE.init(this);
        floatWindow.addDefault(R.drawable.ic_icon);
        floatWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
            }
        });
        floatWindow.addFilter(new FloatWindow.FilterListener() {
            @Override
            public boolean needAdd(@NotNull Activity activity) {
                return activity.getClass().getSimpleName().contains("Main");
            }
        });

//        TextView textView = new TextView(getApplicationContext());
//        textView.setText("ABCABC");
//
//        FloatWindow.INSTANCE.addCustom(R.layout.layout_custom_test);
//        FloatWindow.INSTANCE.addCustom(textView);

    }
}
