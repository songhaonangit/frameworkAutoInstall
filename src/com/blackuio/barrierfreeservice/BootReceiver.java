package com.blackuio.barrierfreeservice;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.Build;

public class BootReceiver extends BroadcastReceiver {
public static final String TAG = "WindowAccessService";
    //songhaonan add 20180731
    public static final String C3707_KEY = "com.blackuio.barrierfreeservice/.WindowAccessibilityService";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
                
          if (Build.VERSION.SDK_INT >= 26) {
			context.startForegroundService(new Intent(context, WindowAccessibilityService.class));

		  } else {
			// Pre-O behavior.
			context.startService(new Intent(context, WindowAccessibilityService.class));
           }

                Log.d(TAG, "barrierfreeservice--intent.getAction()-ACTION_BOOT_COMPLETED");

       
        }
        AccessibilityUtil.checkSetting(context, AccessibilityService.class);
    }
}
