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

    private boolean isNeededClickWeibo = true;
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        Log.d(TAG,"onAccessibilityEvent");
//        //微信UI界面的根节点，开始遍历节点
//        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
//        description = "";
//        if (rootNodeInfo.getContentDescription() != null) {
//            description = rootNodeInfo.getContentDescription().toString();
//        }
//        Log.d(TAG,"description:"+description);
//
//        List<AccessibilityNodeInfo>  detail = rootNodeInfo.findAccessibilityNodeInfosByText("微博正文");
//        if(detail != null && detail.size() == 1){//是微博正文页面
//            Log.d(TAG,"detail size:");
//        }



        //触发 click 进入个人主页的代码
//        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
//        //         if(!"com.sina.weibo.feed.DetailWeiboActivity".equals(event.getClassName())){//             Log.d(TAG,"onAccessibilityEvent 没在微博详情页面");//         }        //微信UI界面的根节点，开始遍历节点        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();        description = "";        if (rootNodeInfo.getContentDescription() != null) {
////        description = rootNodeInfo.getContentDescription().toString();        }
//        Log.d(TAG, "description:" + description);
//        List<AccessibilityNodeInfo> detail = rootNodeInfo.findAccessibilityNodeInfosByText("微博正文");
//        if (detail != null && detail.size() == 1) {//是微博正文页面            Log.d(TAG,"detail size:");        }
//
//            List<AccessibilityNodeInfo> likedRootList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.sina.weibo:id/tvItemNickname");
//            if (likedRootList != null && likedRootList.size() == 0) {//是微博正文页面            return;        }
//                Log.d(TAG, "准备触发click:");
//                likedRootList.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
//
//
//            }
//        }


        int eventType = event.getEventType();
        String eventText = "";
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eventText = "TYPE_VIEW_CLICKED";
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                eventText = "TYPE_VIEW_FOCUSED";
                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                eventText = "TYPE_VIEW_LONG_CLICKED";
                break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                eventText = "TYPE_VIEW_SELECTED";
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                eventText = "TYPE_VIEW_TEXT_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                eventText = "TYPE_WINDOW_STATE_CHANGED";
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                eventText = "TYPE_NOTIFICATION_STATE_CHANGED";
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_END";
                break;
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                eventText = "TYPE_ANNOUNCEMENT";
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_START";
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                eventText = "TYPE_VIEW_HOVER_ENTER";
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                eventText = "TYPE_VIEW_HOVER_EXIT";
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                eventText = "TYPE_VIEW_SCROLLED";
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                eventText = "TYPE_VIEW_TEXT_SELECTION_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                eventText = "TYPE_WINDOW_CONTENT_CHANGED";
                break;
        }
        eventText = eventText + ":" + eventType;
        Log.i(TAG, eventText);

        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();

        if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && "com.sina.weibo.page.ProfileInfoActivity".equals(event.getClassName())){//如果在个人主页
            Log.d(TAG,"当前类在个人主页");

            //测试返回按钮
            List<AccessibilityNodeInfo> backBtnList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.sina.weibo:id/img_back");
            if (backBtnList != null && backBtnList.size() != 0) {
                Log.d(TAG, "找到了 返回按钮");
                backBtnList.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                isNeededClickWeibo = false;
            }
        }

        if(isNeededClickWeibo) {
//            List<AccessibilityNodeInfo> weiboTextViews = rootNodeInfo.findAccessibilityNodeInfosByText("Weibo");
//            if (weiboTextViews != null && weiboTextViews.size() != 0) {
//                Log.d(TAG, "找到了 weiboTextView 触发点击");
//                weiboTextViews.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                isNeededClickWeibo = false;
//            }


            //点赞操作
//            List<AccessibilityNodeInfo> weiboTextViews = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.sina.weibo:id/ly_feed_like_icon");
//            if (weiboTextViews != null && weiboTextViews.size() != 0) {
//                Log.d(TAG, "找到了 点赞按钮 触发点击");
//                weiboTextViews.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                isNeededClickWeibo = false;
//            }

//            //查看赞数量
//            List<AccessibilityNodeInfo> weiboLikedCount = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.sina.weibo:id/tv_feed_like_count");
//            if (weiboLikedCount != null && weiboLikedCount.size() != 0) {
//                Log.d(TAG, "找到了 点赞数量Text 查看第一个的个数:"+weiboLikedCount.get(0).getText());
//                isNeededClickWeibo = false;
//            }
        }

    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        //连接服务后,一般是在授权成功后会接收到

        Log.d(TAG, "onServiceConnected");
    }
}
