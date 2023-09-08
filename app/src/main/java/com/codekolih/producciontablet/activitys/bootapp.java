package com.codekolih.producciontablet.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class bootapp extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent i = new Intent(context, Login_Activity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            //test
        }
    }
}