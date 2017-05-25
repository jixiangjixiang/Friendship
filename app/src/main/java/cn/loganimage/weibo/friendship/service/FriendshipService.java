package cn.loganimage.weibo.friendship.service;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wang.xiang on 2017/5/22.
 */

public class FriendshipService extends AccessibilityService {
    private static String TAG = "FriendshipService";
    private String lastClickedName;
    private ArrayList<String> userList;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        printEventLog(event);

        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && "com.sina.weibo.page.ProfileInfoActivity".equals(event.getClassName())) {//如果在个人主页
            //点赞操作
            checkAndClickLikeBtn(rootNodeInfo);
        } else if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && "com.sina.weibo.feed.DetailWeiboActivity".equals(event.getClassName())) {//如果在微博详情页面
            saveUserListAndOpenIt(rootNodeInfo);

        }
    }

    private void saveUserListAndOpenIt(final AccessibilityNodeInfo rootNodeInfo) {
        if (userList == null) {
            userList = new ArrayList<>();
            List<AccessibilityNodeInfo> likedRootList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.sina.weibo:id/tvItemNickname");
            for (AccessibilityNodeInfo node : likedRootList) {
                userList.add(node.getText().toString());
            }
        }


        if (userList.size() == 0) {//如果用户全被执行完成 滑动到下一屏幕
            List<AccessibilityNodeInfo> listViews = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.sina.weibo:id/tweet_list");
            if (listViews != null && listViews.size() != 0) {
                listViews.get(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ArrayList<String> tmpUserName = new ArrayList<String>();
                        int samePoint = 0;
                        List<AccessibilityNodeInfo> likedRootList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.sina.weibo:id/tvItemNickname");
                        for (int i = 0; i < likedRootList.size(); i++) {
                            String text = likedRootList.get(i).getText().toString();
                            tmpUserName.add(text);
                            if (tmpUserName.equals(lastClickedName)) {
                                samePoint = i;
                            }
                        }


                        for (int i = samePoint; i < tmpUserName.size(); i++) {
                            userList.add(tmpUserName.get(i));
                            Log.d(TAG, "添加进去的Name:" + tmpUserName.get(i));
                        }
                        if (userList.size() != 0) {
                            String userName = userList.remove(0);
                            openProfilePage(userName, rootNodeInfo);
                        }

                    }
                }).start();
                return;
            }
        }

        if (userList.size() != 0) {
            String userName = userList.remove(0);
            openProfilePage(userName, rootNodeInfo);
        }

    }

    private void openProfilePage(String userName, AccessibilityNodeInfo rootNodeInfo) {
        List<AccessibilityNodeInfo> nodeInfoList = rootNodeInfo.findAccessibilityNodeInfosByText(userName);
        if (nodeInfoList != null && nodeInfoList.size() != 0) {
            nodeInfoList.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            Log.d(TAG, "执行的user:" + userName);
        }
    }

    private void printEventLog(AccessibilityEvent event) {
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
        eventText = eventText + ":" + eventType + "   className:" + event.getClassName();
        Log.i(TAG, eventText);
    }

    private void checkAndClickLikeBtn(final AccessibilityNodeInfo rootNodeInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AccessibilityNodeInfo> weiboLikedCount = null;
//                List<AccessibilityNodeInfo> listViews = null;

                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    weiboLikedCount = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.sina.weibo:id/tv_feed_like_count");//点赞数量
                    if (weiboLikedCount != null && weiboLikedCount.size() != 0) {
                        Log.d(TAG,"在个人主页中找到了点赞数量，耗时"+i+"秒");
                        break;
                    }
                }
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//
//                listViews =  rootNodeInfo.findAccessibilityNodeInfosByViewId("com.sina.weibo:id/content");
//                if(listViews != null  && listViews.size() != 0) {
//                    listViews.get(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
//                }
//
//
                //查看赞数量
//                weiboLikedCount = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.sina.weibo:id/tv_feed_like_count");//点赞数量

                if (weiboLikedCount != null && weiboLikedCount.size() != 0) {
                    String likedCountString = weiboLikedCount.get(0).getText().toString();
                    int count = 0;
                    if (!TextUtils.isEmpty(likedCountString) && !likedCountString.equals("Likes")) {//如果 count
                        try {
                            count = Integer.valueOf(likedCountString);
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }

                    if (count < 20) {//如果小于5个
                        doClickLikeBtn(rootNodeInfo);
                    } else {
                        Log.d(TAG, "点赞数量超出预设  " + weiboLikedCount.get(0).getText());
                        clickBackBtn(rootNodeInfo);
                        return;
                    }

                } else {
                    Log.d(TAG, "没有找到点赞数量的id");
                    clickBackBtn(rootNodeInfo);
                    return;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clickBackBtn(rootNodeInfo);
            }
        }).start();
    }

    private void doClickLikeBtn(AccessibilityNodeInfo rootNodeInfo) {
        List<AccessibilityNodeInfo> weiboTextViews = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.sina.weibo:id/ly_feed_like_icon");//点赞按钮
        if (weiboTextViews != null && weiboTextViews.size() != 0) {
            Log.d(TAG, "找到了 点赞按钮 触发点击");
            weiboTextViews.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }

    }

    private void clickBackBtn(AccessibilityNodeInfo rootNodeInfo) {
        List<AccessibilityNodeInfo> backBtnList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.sina.weibo:id/img_back");
        if (backBtnList != null && backBtnList.size() != 0) {
            Log.d(TAG, "找到了 返回按钮");
            backBtnList.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
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
