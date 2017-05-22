package cn.loganimage.weibo.friendship.service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * @author wang.xiang on 2017/5/22.
 */

public class FriendshipService extends AccessibilityService {
    private static String TAG = "FriendshipService";
    private String description;
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG,"onAccessibilityEvent");
        //微信UI界面的根节点，开始遍历节点
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        description = "";
        if (rootNodeInfo.getContentDescription() != null) {
            description = rootNodeInfo.getContentDescription().toString();
        }
        Log.d(TAG,"description:"+description);

        List<AccessibilityNodeInfo>  detail = rootNodeInfo.findAccessibilityNodeInfosByText("微博正文");
        if(detail != null && detail.size() == 1){//是微博正文页面
            Log.d(TAG,"detail size:");
        }

    }

    @Override
    public void onInterrupt() {
        Log.d(TAG,"onInterrupt");
    }
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        //连接服务后,一般是在授权成功后会接收到

        Log.d(TAG,"onServiceConnected");
    }
}
