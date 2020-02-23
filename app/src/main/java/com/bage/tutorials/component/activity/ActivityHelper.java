package com.bage.tutorials.component.activity;

import android.content.Context;
import android.content.Intent;

public class ActivityHelper {

    public static void startActivity(Context context,Class cls){
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

}
