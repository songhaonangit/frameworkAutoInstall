package com.blackuio.barrierfreeservice;

/**
 * <pre>
 *     author : songhaonan
 *     e-mail : anoop_hn@163.com
 *     time   : 2019/11/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;

public class WindowAccessibilityService extends AccessibilityService {

    public static final String TAG = "WindowAccessibilityService";

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getSource() != null) {
            findAndPerformAction("安装");
//            findAndPerformAction("下一步");
            findAndPerformAction("打开");
            findAndPerformAction("总是允许");
            findAndPerformAction("DeviceOS");
            findAndPerformAction("确认");
            findAndPerformAction("始终");

        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "WindowAccessibilityService");
    }

    private void findAndPerformAction(String text) {
        // 查找当前窗口中包含“安装”文字的按钮
        AccessibilityNodeInfo info = getRootInActiveWindow();
        if (info != null) {
            //通过文字找到当前的节点
            List<AccessibilityNodeInfo> nodes = info.findAccessibilityNodeInfosByText(text);
            for (int i = 0; i < nodes.size(); i++) {
                AccessibilityNodeInfo node = nodes.get(i);
//                // 执行按钮点击行为
//                if (node.getClassName().equals("android.widget.Button") && node.isEnabled()) {
//                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                }

                if (node.isEnabled()) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }
    }

    private static final String CHANNEL_ID_SERVICE_RUNNING = "CHANNEL_ID_SERVICE_RUNNING";
    private Notification createNotification() {
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_SERVICE_RUNNING, "app service", NotificationManager.IMPORTANCE_LOW);
            nm.createNotificationChannel(channel);
            builder = new Notification.Builder(getApplicationContext(), CHANNEL_ID_SERVICE_RUNNING);
        } else {
            builder = new Notification.Builder(getApplicationContext());
        }
        Intent nfIntent = new Intent();
        nfIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        builder.setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, nfIntent, 0))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("无障碍服务运行中")
                .setContentText("无障碍服务运行中");
        Notification notification = builder.build();
        return notification;
    }

}

